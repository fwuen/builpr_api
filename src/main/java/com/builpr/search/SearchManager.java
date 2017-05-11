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

    //TODO: kann man hier eventuell boolean zurückgeben --> war Indexierung erfolgreich?
    public void index(List<IndexablePrintModel> indexables);
    public void index(IndexablePrintModel indexable);

    public int isReachable();

}
