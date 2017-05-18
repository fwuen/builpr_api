package com.builpr.search;

import com.builpr.search.filter.Filter;
import com.builpr.search.model.IndexablePrintModel;
import com.builpr.search.model.PrintModelReference;

import java.io.IOException;
import java.util.List;

/**
 * Prescribes methods to be implemented for using search engines
 */
public interface SearchManager {
    
    /**
     * Searches for data fitting the passed term
     *
     * @param term Term to execute the search with
     * @return List of PrintModelReference-objects
     * @throws SearchManagerException SearchManagerException
     */
    public List<PrintModelReference> search(String term) throws SearchManagerException;
    
    /**
     * Searches for data fitting the passed term and filters
     *
     * @param term   Term to execute the search with
     * @param filter Filter to execute the search with
     * @return List of PrintModelReference-objects
     * @throws SearchManagerException SearchManagerException
     */
    public List<PrintModelReference> search(String term, List<Filter> filter) throws SearchManagerException;
    
    /**
     * Searches for data fitting the passed term and order regulation
     *
     * @param term  Term to execute the search with
     * @param order Order regulation to execute the search with
     * @return List of PrintModelReference-objects
     * @throws SearchManagerException SearchManagerException
     */
    public List<PrintModelReference> search(String term, Order order) throws SearchManagerException;
    
    /**
     * Searches for data fitting the passed term, filters and order regulation
     *
     * @param term   Term to execute the search with
     * @param filter Filter to execute the search with
     * @param order  Order regulation to execute the search with
     * @return List of PrintModelReference-objects
     * @throws SearchManagerException SearchManagerException
     */
    public List<PrintModelReference> search(String term, List<Filter> filter, Order order) throws SearchManagerException;
    
    /**
     * Adds print models to the index of the used search engine
     *
     * @param indexables List of IndexablePrintModel-objects to be added to the index
     * @throws SearchManagerException SearchManagerException
     */
    public void addToIndex(List<IndexablePrintModel> indexables) throws SearchManagerException;
    
    /**
     * Adds a single print model to the index of the used search engine
     *
     * @param indexable IndexablePrintModel-object representing the print model to be added to the index
     * @throws SearchManagerException SearchManagerException
     */
    public void addToIndex(IndexablePrintModel indexable) throws SearchManagerException;
    
    /**
     * Deletes print models from the index of the used search engine
     *
     * @param removables List of PrintModelReference-objects representing the print models to be deleted from the index
     * @throws SearchManagerException SearchManagerException
     */
    public void deleteFromIndex(List<PrintModelReference> removables) throws SearchManagerException;
    
    /**
     * Deletes a single print model from the index of the used search engine
     *
     * @param removable PrintModelReference-object representing the print model to be deleted from the index
     * @throws SearchManagerException SearchManagerException
     */
    public void deleteFromIndex(PrintModelReference removable) throws SearchManagerException;
    
    /**
     * Checks if the search engine server is reachable
     *
     * @return true - server is reachable, false - server is not reachable
     * @throws SearchManagerException SearchManagerException
     */
    public boolean isReachable() throws SearchManagerException;
    
}