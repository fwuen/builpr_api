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

    public void index(List<IndexablePrintModel> indexable);
    public void index(IndexablePrintModel indexable);

    public boolean isReachable();

}
