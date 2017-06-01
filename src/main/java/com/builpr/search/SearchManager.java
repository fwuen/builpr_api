package com.builpr.search;

import com.builpr.search.filter.Filter;
import com.builpr.search.model.Printable;
import com.builpr.search.model.PrintableReference;

import java.util.List;

/**
 * Prescribes methods to be implemented for using search engines
 */
public interface SearchManager {
    
    /**
     * Searches for data fitting the passed term
     *
     * @param term Term to execute the search with
     * @return List of PrintableReference-objects
     * @throws SearchManagerException SearchManagerException
     */
    public List<PrintableReference> search(String term) throws SearchManagerException;
    
    /**
     * Searches for data fitting the passed term and filters
     *
     * @param term   Term to execute the search with
     * @param filter Filter to execute the search with
     * @return List of PrintableReference-objects
     * @throws SearchManagerException SearchManagerException
     */
    public List<PrintableReference> search(String term, List<Filter> filter) throws SearchManagerException;
    
    /**
     * Searches for data fitting the passed term and sort regulation
     *
     * @param term  Term to execute the search with
     * @param sort SORT regulation to execute the search with
     * @return List of PrintModelReference-objects
     * @throws SearchManagerException SearchManagerException
     */
    public List<PrintableReference> search(String term, SORT sort) throws SearchManagerException;

    /**
     * Searches for data fitting the passed term, sort and regulations
     *
     * @param term  Term to execute the search with
     * @param sort  SORT regulation to execute the search with
     * @param ORDER ORDER regulation to execute the search with
     * @return List of PrintModelReference-objects
     * @throws SearchManagerException SearchManagerException
     */
    public List<PrintableReference> search(String term, SORT sort, ORDER order) throws  SearchManagerException;

    /**
     * Searches for data fitting the passed term, filters and sort regulation
     *
     * @param term   Term to execute the search with
     * @param filter Filter to execute the search with
     * @param sort  SORT regulation to execute the search with
     * @return List of PrintableReference-objects
     * @throws SearchManagerException SearchManagerException
     */
    public List<PrintableReference> search(String term, List<Filter> filter, SORT sort) throws SearchManagerException;

    /**
     * Searches for data fitting the passed term, filters, sort and ORDER regulations
     *
     * @param term   Term to execute the search with
     * @param filter Filter to execute the search with
     * @param sort  SORT regulation to execute the search with
     * @param ORDER ORDER regulation to execute the search with
     * @return List of PrintableReference-objects
     * @throws SearchManagerException SearchManagerException
     */
    public List<PrintableReference> search(String term, List<Filter> filter, SORT sort, ORDER order) throws SearchManagerException;

    /**
     * Adds print models to the index of the used search engine
     *
     * @param indexables List of Printable-objects to be added to the index
     * @throws SearchManagerException SearchManagerException
     */
    public void addToIndex(List<Printable> indexables) throws SearchManagerException;
    
    /**
     * Adds a single print model to the index of the used search engine
     *
     * @param indexable Printable-object representing the print model to be added to the index
     * @throws SearchManagerException SearchManagerException
     */
    public void addToIndex(Printable indexable) throws SearchManagerException;
    
    /**
     * Deletes print models from the index of the used search engine
     *
     * @param removables List of PrintableReference-objects representing the print models to be deleted from the index
     * @throws SearchManagerException SearchManagerException
     */
    public void deleteFromIndex(List<PrintableReference> removables) throws SearchManagerException;
    
    /**
     * Deletes a single print model from the index of the used search engine
     *
     * @param removable PrintableReference-object representing the print model to be deleted from the index
     * @throws SearchManagerException SearchManagerException
     */
    public void deleteFromIndex(PrintableReference removable) throws SearchManagerException;
    
    /**
     * Checks if the search engine server is reachable
     *
     * @return true - server is reachable, false - server is not reachable
     * @throws SearchManagerException SearchManagerException
     */
    public boolean isReachable() throws SearchManagerException;
    
}