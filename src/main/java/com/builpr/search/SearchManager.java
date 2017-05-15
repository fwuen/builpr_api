package com.builpr.search;

import com.builpr.search.filter.Filter;
import com.builpr.search.model.IndexablePrintModel;
import com.builpr.search.model.PrintModelReference;

import java.io.IOException;
import java.util.List;

public interface SearchManager {

    public List<PrintModelReference> search(String term) throws SearchManagerException;
    public List<PrintModelReference> search(String term, List<Filter> filter) throws SearchManagerException;
    public List<PrintModelReference> search(String term, Order order) throws SearchManagerException;
    public List<PrintModelReference> search(String term, List<Filter> filter, Order order) throws SearchManagerException;

    //TODO: Statt "void" die "UpdateResponse" in irgendeiner Form zurückgeben?
    public void addToIndex(List<IndexablePrintModel> indexables) throws SearchManagerException;
    public void addToIndex(IndexablePrintModel indexable) throws SearchManagerException;

    public void deleteFromIndex(List<PrintModelReference> removables) throws  SearchManagerException;
    public void deleteFromIndex(PrintModelReference removable) throws SearchManagerException;

    public boolean isReachable() throws SearchManagerException;

}
