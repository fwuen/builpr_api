package com.builpr.search.solr;

import com.builpr.search.ORDER;
import com.builpr.search.SORT;
import com.google.common.base.Verify;
import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Assert;
import org.junit.Test;

public class SolrEnumMapperTest {
    
    @Test(expected = NullPointerException.class)
    public void enumToSolrEnumWithNullOrder() {
        ORDER order = null;
        SolrEnumMapper.enumToSolrEnum(order);
    }
    
    @Test(expected = NullPointerException.class)
    public void enumToSolrEnumWithNullSort() {
        SORT sort = null;
        SolrEnumMapper.enumToSolrEnum(sort);
    }
    
    @Test
    public void enumToSolrEnumWithDESCOrder() {
        SolrQuery.ORDER order = SolrEnumMapper.enumToSolrEnum(ORDER.DESC);
        Verify.verify(order == SolrQuery.ORDER.desc);
    }
    
    @Test
    public void enumToSolrEnumWithASCOrder() {
        SolrQuery.ORDER order = SolrEnumMapper.enumToSolrEnum(ORDER.ASC);
        Assert.assertTrue(order == SolrQuery.ORDER.asc);
    }
    
    @Test
    public void enumToSolrEnumWithRELEVANCESort() {
        SOLR_SORT sort = SolrEnumMapper.enumToSolrEnum(SORT.RELEVANCE);
        Assert.assertTrue(sort == SOLR_SORT.RELEVANCE);
    }
    
    @Test
    public void enumToSolrEnumWithALPHABETICALSort() {
        SOLR_SORT sort = SolrEnumMapper.enumToSolrEnum(SORT.ALPHABETICAL);
        Assert.assertTrue(sort == SOLR_SORT.title);
    }
    
    @Test
    public void enumToSolrEnumWithRATINGSort() {
        SOLR_SORT sort = SolrEnumMapper.enumToSolrEnum(SORT.RATING);
        Assert.assertTrue(sort == SOLR_SORT.rating);
    }
    
    @Test
    public void enumToSolrEnumWithDOWNLOADSSort() {
        SOLR_SORT sort = SolrEnumMapper.enumToSolrEnum(SORT.DOWNLOADS);
        Assert.assertTrue(sort == SOLR_SORT.numberOfDownloads);
    }
    
    @Test
    public void enumToSolrEnumWithUPLOADDATESort() {
        SOLR_SORT sort = SolrEnumMapper.enumToSolrEnum(SORT.UPLOAD_DATE);
        Assert.assertTrue(sort == SOLR_SORT.uploadDate);
    }
    
}
