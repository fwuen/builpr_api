package com.builpr.search.solr;

import com.builpr.search.*;
import com.builpr.search.filter.Filter;
import com.builpr.search.model.IndexablePrintModel;
import com.builpr.search.model.PrintModelReference;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.SolrPing;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.List;

public class SolrSearchManager implements SearchManager {

    private SolrClient solrClient;
    private SolrQueryFactory solrQueryFactory;
    private PrintModelReferenceFactory printModelReferenceFactory;
    private static final String COLLECTION = "testcore";

    /**
     *
     * @param solrClient
     */
    private SolrSearchManager(@NonNull SolrClient solrClient) {
        this.solrClient = solrClient;
        this.solrQueryFactory = new SolrQueryFactory();
        this.printModelReferenceFactory = new PrintModelReferenceFactory();
    }

    /**
     *
     * @param term
     * @return
     * @throws SearchManagerException
     */
    @Override
    public List<PrintModelReference> search(@NonNull String term) throws SearchManagerException {
        return search(term, Lists.newArrayList(), Order.RELEVANCE);
    }

    /**
     *
     * @param term
     * @param filter
     * @return
     * @throws SearchManagerException
     */
    @Override
    public List<PrintModelReference> search(@NonNull String term, @NonNull List<Filter> filter) throws SearchManagerException {
        return search(term, filter, Order.RELEVANCE);
    }

    /**
     *
     * @param term
     * @param order
     * @return
     * @throws SearchManagerException
     */
    @Override
    public List<PrintModelReference> search(@NonNull String term, @NonNull Order order) throws SearchManagerException {
        return search(term, Lists.newArrayList(), order);
    }

    /**
     *
     * @param term
     * @param filter
     * @param order
     * @return
     * @throws SearchManagerException
     */
    @Override
    public List<PrintModelReference> search(@NonNull String term, @NonNull List<Filter> filter, @NonNull Order order) throws SearchManagerException {
        try {
            SolrQuery solrQuery = solrQueryFactory.getQueryWith(term, filter, order);

            QueryResponse queryResponse = solrClient.query(COLLECTION, solrQuery);

            List<PrintModelReference> results = printModelReferenceFactory.get(queryResponse.getResults());

            return results;

        } catch (Exception exception) {
            throw new SearchManagerException(exception);
        }
    }

    /**
     *
     * @param indexables
     * @throws SearchManagerException
     */
    @Override
    public void addToIndex(@NonNull List<IndexablePrintModel> indexables) throws SearchManagerException {
        for (IndexablePrintModel indexable : indexables)
            addToIndex(indexable, false);

        commit();
    }

    /**
     *
     * @param indexable
     * @throws SearchManagerException
     */
    @Override
    public void addToIndex(@NonNull IndexablePrintModel indexable) throws SearchManagerException {
        this.addToIndex(indexable, true);
    }

    /**
     *
     * @param indexable
     * @param commit
     * @throws SearchManagerException
     */
    private void addToIndex(@NonNull IndexablePrintModel indexable, boolean commit) throws SearchManagerException {
        SolrInputDocument inputDocument = new SolrInputDocumentFactory().get(indexable);

        try {
            solrClient.add(COLLECTION, inputDocument);
        } catch (Exception exception) {
            throw new SearchManagerException(exception);
        }

        if (commit)
            commit();
    }

    private void commit() throws SearchManagerException {
        try {
            solrClient.commit();
        } catch (Exception exception) {
            throw new SearchManagerException(exception);
        }
    }

    /**
     *
     * @return
     * @throws SearchManagerException
     */
    @Override
    public boolean isReachable() throws SearchManagerException {
        try {
            if (new SolrPing().process(solrClient, COLLECTION).getStatus() == 0) {
                return true;
            }
            return false;
        } catch (Exception exception) {
            throw new SearchManagerException(exception);
        }
    }

    /**
     *
     * @param baseURL
     * @return
     */
    public static SolrSearchManager createWithBaseURL(@NonNull String baseURL) {
        SolrClient solrClient = new HttpSolrClient.Builder().withBaseSolrUrl(baseURL).build();

        return new SolrSearchManager(solrClient);
    }

    /**
     *
     * @param solrClient
     * @return
     */
    public static SolrSearchManager createWithSolrClient(@NonNull SolrClient solrClient) {
        return new SolrSearchManager(solrClient);
    }

    /**
     *
     * @param removables
     * @throws SearchManagerException
     */
    @Override
    public void deleteFromIndex(@NonNull List<PrintModelReference> removables) throws SearchManagerException {
        for (PrintModelReference removable : removables)
            deleteFromIndex(removable, false);

        commit();
    }

    /**
     *
     * @param removable
     * @throws SearchManagerException
     */
    @Override
    public void deleteFromIndex(@NonNull PrintModelReference removable) throws SearchManagerException {
        deleteFromIndex(removable, true);
    }

    /**
     *
     * @param removable
     * @param commit
     * @throws SearchManagerException
     */
    private void deleteFromIndex(@NonNull PrintModelReference removable, boolean commit) throws SearchManagerException {

        try {
            solrClient.deleteById(COLLECTION, "" + removable.getId());
        } catch (Exception e) {
            throw new SearchManagerException(e);
        }

        if (commit)
            commit();
    }
}
