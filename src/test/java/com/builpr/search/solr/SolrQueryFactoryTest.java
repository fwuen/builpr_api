package com.builpr.search.solr;

import com.builpr.search.ORDER;
import com.builpr.search.SORT;
import com.builpr.search.filter.Filter;
import com.google.common.base.Verify;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

//Majority of functionality is already Tested by SolrSearchTest class
//Because of that only some null tests are required for the SolrQueryFactoryTest class

/**
 * @author Felix Wünsche
 */
public class SolrQueryFactoryTest {
    @Test
    public void testCreate() {
        SolrQueryFactory solrQueryFactory = new SolrQueryFactory();
        Assert.assertNotNull(solrQueryFactory);
    }
    
    @Test(expected = NullPointerException.class)
    public void createQueryWithAllNull() {
        SolrQueryFactory solrQueryFactory = new SolrQueryFactory();
        solrQueryFactory.getQueryWith(null, null, null, null);
    }
    
    @Test(expected = NullPointerException.class)
    public void createQueryWithTermNull() {
        SolrQueryFactory solrQueryFactory = new SolrQueryFactory();
        List<Filter> filters = Lists.newArrayList();
        solrQueryFactory.getQueryWith(null, filters, SORT.ALPHABETICAL, ORDER.ASC);
    }
    
    @Test(expected = NullPointerException.class)
    public void createQueryWithFiltersNull() {
        SolrQueryFactory solrQueryFactory = new SolrQueryFactory();
        solrQueryFactory.getQueryWith("", null, SORT.ALPHABETICAL, ORDER.ASC);
    }
    
    @Test(expected = NullPointerException.class)
    public void createWithSortNull() {
        SolrQueryFactory solrQueryFactory = new SolrQueryFactory();
        List<Filter> filters = Lists.newArrayList();
        solrQueryFactory.getQueryWith("", filters, null, ORDER.ASC);
    }
    
    @Test(expected = NullPointerException.class)
    public void createWithOrderNull() {
        SolrQueryFactory solrQueryFactory = new SolrQueryFactory();
        List<Filter> filters = Lists.newArrayList();
        solrQueryFactory.getQueryWith("", filters, SORT.ALPHABETICAL, null);
    }
}
