package com.builpr.search.solr;

import com.builpr.search.*;
import com.builpr.search.filter.Filter;
import com.builpr.search.model.IndexablePrintModel;
import com.builpr.search.model.PrintModelReference;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.SolrPing;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

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
    public List<PrintModelReference> search(@NonNull String term) throws SearchManagerException {
        return search(term, Lists.newArrayList(), Order.RELEVANCE);
    }

    @Override
    public List<PrintModelReference> search(@NonNull String term, @NonNull List<Filter> filter) throws SearchManagerException {
        return search(term, filter, Order.RELEVANCE);
    }

    @Override
    public List<PrintModelReference> search(@NonNull String term, @NonNull Order order) throws SearchManagerException {
        return search(term, Lists.newArrayList(), order);
    }

    @Override
    public List<PrintModelReference> search(@NonNull String term, @NonNull List<Filter> filter, @NonNull Order order) throws SearchManagerException {
        try {
            SolrQuery solrQuery = solrQueryFactory.getQueryWith(term, filter, order);

            QueryResponse queryResponse = solrClient.query(solrQuery);

            List<PrintModelReference> results = printModelReferenceFactory.get(queryResponse.getResults());

            return results;

        } catch(Exception exception) {
            throw new SearchManagerException(exception);
        }
    }

    @Override
    public void addToIndex(@NonNull List<IndexablePrintModel> indexables) throws SearchManagerException {
        for(IndexablePrintModel indexable : indexables)
            addToIndex(indexable);
    }

    @Override
    public void addToIndex(@NonNull IndexablePrintModel indexable) throws SearchManagerException {
        SolrInputDocument inputDocument = new SolrInputDocumentFactory().get(indexable);

        try {
            UpdateResponse response = solrClient.add(inputDocument);
        } catch (Exception exception) {
            throw new SearchManagerException(exception);
        }

        try {
            UpdateResponse response = solrClient.commit();
        } catch (Exception exception) {
            throw new SearchManagerException(exception);
        }
    }
    
    @Override
    public int isReachable() throws SearchManagerException {

        /* TODO: Passt das so? */
        try {
            return new SolrPing().process(solrClient).getStatus(); /* TODO: Collection(s) hinzufufügen? */
        } catch (Exception exception) {
            throw new SearchManagerException(exception);
        }
    }
    
    public static SolrSearchManager createWithBaseURL(@NonNull String baseURL) {
        SolrClient solrClient = new HttpSolrClient.Builder().withBaseSolrUrl(baseURL).build();

        return new SolrSearchManager(solrClient);
    }

    public static SolrSearchManager createWithSolrClient(@NonNull SolrClient solrClient) {
        return new SolrSearchManager(solrClient);
    }
    
    public void deleteFromIndex(@NonNull List<PrintModelReference> removables) throws SearchManagerException {
        for(PrintModelReference removable : removables)
        {
                deleteFromIndex(removable);
        }
    }
    public void deleteFromIndex(@NonNull PrintModelReference removable) throws SearchManagerException {
         /*TODO: Passt das so?*/

         try {
             UpdateResponse response = solrClient.deleteById(""+removable.getId());
         }
         catch (Exception exception) {
             throw new SearchManagerException(exception);
         }

        try {
            UpdateResponse response = solrClient.commit();
        } catch (Exception exception) {
            throw new SearchManagerException(exception);
        }
    }
}
