package com.builpr.search.solr;

import com.builpr.search.Order;
import com.builpr.search.SearchException;
import com.builpr.search.SearchManager;
import com.builpr.search.filter.Filter;
import com.builpr.search.model.IndexablePrintModel;
import com.builpr.search.model.PrintModelReference;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.SolrPing;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.List;

public class SolrSearchManager implements SearchManager {

    private SolrClient solrClient;
    private SolrQueryFactory solrQueryFactory;
    private PrintModelReferenceFactory printModelReferenceFactory;

    private SolrSearchManager(@NonNull SolrClient solrClient) {
        this.solrClient = solrClient;
        this.solrQueryFactory = new SolrQueryFactory();
        this.printModelReferenceFactory = new PrintModelReferenceFactory();
    }
    
    @Override
    public List<PrintModelReference> search(@NonNull String term) throws SearchException {
        return search(term, Lists.newArrayList(), Order.RELEVANCE);
    }

    @Override
    public List<PrintModelReference> search(@NonNull String term, @NonNull List<Filter> filter) throws SearchException {
        return search(term, filter, Order.RELEVANCE);
    }

    @Override
    public List<PrintModelReference> search(@NonNull String term, @NonNull Order order) throws SearchException {
        return search(term, Lists.newArrayList(), order);
    }

    @Override
    public List<PrintModelReference> search(@NonNull String term, @NonNull List<Filter> filter, @NonNull Order order) throws SearchException {
        try {
            SolrQuery solrQuery = solrQueryFactory.getQueryWith(term, filter, order);

            QueryResponse queryResponse = solrClient.query(solrQuery);

            List<PrintModelReference> results = printModelReferenceFactory.get(queryResponse.getResults());

            return results;
        } catch(Exception exception) {
            throw new SearchException(exception);
        }
    }

    @Override
    public void index(@NonNull List<IndexablePrintModel> indexables) {
        for(IndexablePrintModel indexable : indexables)
            index(indexable);
    }

    @Override
    public void index(@NonNull IndexablePrintModel indexable) {
        /* TODO: Mappe ein SolrInputDocument und schiebe dieses in den Solr. */
        String tags = buildStringForTagField(indexable);
        
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", ""+indexable.getId());
        document.addField("title", indexable.getTitle());
        document.addField("description", indexable.getDescription());
        document.addField("ageRestriction", "" + indexable.getAgeRestriction());
        document.addField("uploaderId", "" + indexable.getUploaderId());
        document.addField("uploadDate", indexable.getUploadDate().toString());
        document.addField("rating", "" + indexable.getRating());
        document.addField("tags", tags);
        
        try {
            UpdateResponse response = solrClient.add(document);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try {
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String buildStringForTagField(IndexablePrintModel indexablePrintModel) {
        StringBuffer buffer = new StringBuffer();
        for (String tag : indexablePrintModel.getTags()) {
            buffer.append(tag);
        }
        return buffer.toString();
    }
    
    @Override
    public int isReachable() {
        /* TODO: check if reacheable */
        SolrPing sp = new SolrPing();
        SolrPingResponse rsp = null;
        try {
            rsp = sp.process(solrClient, "testcore");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsp.getStatus();
    }
    
    public static SolrSearchManager createWithBaseURL(@NonNull String baseURL) {
        SolrClient solrClient = new HttpSolrClient.Builder().withBaseSolrUrl(baseURL).build();

        return new SolrSearchManager(solrClient);
    }

    public static SolrSearchManager createWithSolrClient(@NonNull SolrClient solrClient) {
        return new SolrSearchManager(solrClient);
    }
    
    public void removeFromIndex(List<PrintModelReference> removables) {
    
    }
    public void removeFromIndex(PrintModelReference removable) {
    
    }
}
