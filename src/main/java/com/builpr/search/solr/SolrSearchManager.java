package com.builpr.search.solr;

import com.builpr.search.*;
import com.builpr.search.filter.Filter;
import com.builpr.search.model.Indexable;
import com.builpr.search.model.Printable;
import com.builpr.search.model.PrintableReference;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.SolrPing;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import java.util.List;
// TODO: Doku für @Override-Methode erben

/**
 * Provides access to Solr search engine
 * Implements SearchManager-interface
 */
public class SolrSearchManager implements SearchManager {

    private SolrClient solrClient;
    private SolrQueryFactory solrQueryFactory;
    private PrintableReferenceFactory printableReferenceFactory;
    private static final String COLLECTION = "main";

    /**
     * Creates and returns a SolrSearchManager-object
     *
     * @param solrClient The solr-client that should be assigned to the SolrSearchManager-object to execute all further instructions on
     */
    private SolrSearchManager(@NonNull SolrClient solrClient) {
        this.solrClient = solrClient;
        this.solrQueryFactory = new SolrQueryFactory();
        this.printableReferenceFactory = new PrintableReferenceFactory();
    }

    @Override
    public List<PrintableReference> search(@NonNull String term) throws SearchManagerException {
        return search(term, Lists.newArrayList(), SORT.RELEVANCE, ORDER.ASC);
    }

    @Override
    public List<PrintableReference> search(@NonNull String term, @NonNull List<Filter> filter) throws SearchManagerException {
        return search(term, filter, SORT.RELEVANCE, ORDER.ASC);
    }

    @Override
    public List<PrintableReference> search(@NonNull String term, @NonNull SORT sort) throws SearchManagerException {
        return search(term, Lists.newArrayList(), sort, ORDER.ASC);
    }

    @Override
    public List<PrintableReference> search(@NonNull String term, @NonNull SORT sort, ORDER order) throws SearchManagerException {
        return search(term, Lists.newArrayList(), sort, order);
    }

    @Override
    public List<PrintableReference> search(@NonNull String term, @NonNull List<Filter> filter, @NonNull SORT sort) throws SearchManagerException
    {
        return search(term, Lists.newArrayList(), sort, ORDER.ASC);
    }

    @Override
    public List<PrintableReference> search(@NonNull String term, @NonNull List<Filter> filter, @NonNull SORT sort, @NonNull ORDER order) throws SearchManagerException
    {
        try {
            SolrQuery solrQuery = solrQueryFactory.getQueryWith(term, filter, sort, order);

            QueryResponse queryResponse = solrClient.query(COLLECTION, solrQuery);

            List<PrintableReference> results = printableReferenceFactory.get(queryResponse.getResults());

            return results;

        } catch (Exception exception) {
            throw new SearchManagerException(exception);
        }

    }

    @Override
    public void addToIndex(@NonNull List<Indexable> indexables) throws SearchManagerException {
        for (Indexable indexable : indexables)
            addToIndex(indexable, false);

        commit();
    }

    @Override
    public void addToIndex(@NonNull Indexable indexable) throws SearchManagerException {
        this.addToIndex(indexable, true);
    }

    /**
     * Adds an print model to the Solr index
     *
     * @param indexable The print model that should be added to the Solr index
     * @param commit Whether the actual commit to the Solr search engine should be executed or not
     * @throws SearchManagerException SearchManagerException
     */
    private void addToIndex(@NonNull Indexable indexable, boolean commit) throws SearchManagerException {
        SolrInputDocument inputDocument = new SolrInputDocumentFactory().getInputDocumentWith(indexable);

        try {
            solrClient.add(COLLECTION, inputDocument);
        } catch (Exception exception) {
            throw new SearchManagerException(exception);
        }

        if (commit)
            commit();
    }

    /**
     * Commits changes to the Solr search engine
     *
     * @throws SearchManagerException SearchManagerException
     */
    private void commit() throws SearchManagerException {
        try {
            solrClient.commit();
        } catch (Exception exception) {
            throw new SearchManagerException(exception);
        }
    }

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
     * Creates and returns a SolrSearchManager-object with a SolrClient-object initialized with a base URL
     *
     * @param baseURL The base URL the for the SolrSearchManager required Solr client-object should be initialized with
     * @return SolrSearchManager-object
     */
    public static SolrSearchManager createWithBaseURL(@NonNull String baseURL) {
        SolrClient solrClient = new HttpSolrClient.Builder().withBaseSolrUrl(baseURL).build();

        return new SolrSearchManager(solrClient);
    }

    /**
     * Creates and returns a SolrSearchManager-object with a SolrClient-object passed as parameter
     *
     * @param solrClient The SolrClient-object the SolrSearchManager should be initialized with
     * @return SolrSearchManager-object
     */
    public static SolrSearchManager createWithSolrClient(@NonNull SolrClient solrClient) {
        return new SolrSearchManager(solrClient);
    }

    @Override
    public void deleteFromIndex(@NonNull List<PrintableReference> removables) throws SearchManagerException {
        for (PrintableReference removable : removables)
            deleteFromIndex(removable, false);

        commit();
    }

    @Override
    public void deleteFromIndex(@NonNull PrintableReference removable) throws SearchManagerException {
        deleteFromIndex(removable, true);
    }

    /**
     * Deletes a given print model from the Solr index
     *
     * @param removable The print model that should be deleted from the index
     * @param commit Whether the actual commit to the Solr search engine should be executed or not
     * @throws SearchManagerException SearchManagerException
     */
    private void deleteFromIndex(@NonNull PrintableReference removable, boolean commit) throws SearchManagerException {

        try {
            solrClient.deleteById(COLLECTION, "" + removable.getId());
        } catch (Exception e) {
            throw new SearchManagerException(e);
        }

        if (commit)
            commit();
    }
}
