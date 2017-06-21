package com.builpr.search.solr;

import com.builpr.search.ORDER;
import com.builpr.search.SORT;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * @author Alexander Zeitler
 *         Utility class for mapping general enums to solr specific enums.
 */
public abstract class SolrEnumMapper {
    
    /**
     * Takes a general order enum and maps it to a solr specific order enum
     *
     * @param order General order enum
     * @return
     */
    
    public static SolrQuery.ORDER enumToSolrEnum(ORDER order) {
        if (order.toString().toUpperCase() == "DESC") {
            return SolrQuery.ORDER.desc;
        }
        
        return SolrQuery.ORDER.asc;
    }
    
    /**
     * Takes a general sort enum and maps it to a solr specific sort enum
     *
     * @param sort General order enum
     * @return
     */
    public static SOLR_SORT enumToSolrEnum(SORT sort) {
        switch (sort.toString().toUpperCase()) {
            case "RELEVANCE":
                return SOLR_SORT.RELEVANCE;
            
            case "ALPHABETICAL":
                return SOLR_SORT.title;
            
            case "RATING":
                return SOLR_SORT.rating;
            
            case "DOWNLOADS":
                return SOLR_SORT.numberOfDownloads;
            
            case "UPLOAD_DATE":
                return SOLR_SORT.uploadDate;
            
            default:
                return SOLR_SORT.RELEVANCE;
        }
    }
    
}
