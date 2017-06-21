package com.builpr.search.solr;

import com.builpr.search.SearchManagerException;
import org.junit.Assert;
import org.junit.Test;

public class SearchManagerExceptionTest {
    @Test
    public void createSearchManagerException() {
        SearchManagerException exception = new SearchManagerException(new Exception());
        Assert.assertNotNull(exception);
    }

    @Test
    public void getExceptionMessageFromSearchManagerException() {
        SearchManagerException exception = new SearchManagerException(new Exception());
        Assert.assertNotNull(exception.getException());
    }

    @Test (expected = NullPointerException.class)
    public void createSearchManagerExceptionWithNull() {
        SearchManagerException searchManagerException = new SearchManagerException(null);
    }
}
