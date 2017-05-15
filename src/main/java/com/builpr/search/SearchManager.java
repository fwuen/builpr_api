package com.builpr.search;

import com.builpr.search.filter.Filter;
import com.builpr.search.model.IndexablePrintModel;
import com.builpr.search.model.PrintModelReference;

import java.io.IOException;
import java.util.List;

public interface SearchManager {

    public List<PrintModelReference> search(String term) throws SearchException;
    public List<PrintModelReference> search(String term, List<Filter> filter) throws SearchException;
    public List<PrintModelReference> search(String term, Order order) throws SearchException;
    public List<PrintModelReference> search(String term, List<Filter> filter, Order order) throws SearchException;

    //TODO: Statt "void" die "UpdateResponse" in irgendeiner Form zurückgeben?
    public void addToIndex(List<IndexablePrintModel> indexables) throws IndexException;
    public void addToIndex(IndexablePrintModel indexable) throws IndexException;

    public void deleteFromIndex(List<PrintModelReference> removables) throws  IndexException;
    public void deleteFromIndex(PrintModelReference removable) throws IndexException;

    public boolean isReachable() throws ConnectionException;

}
