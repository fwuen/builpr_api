package com.builpr.search.model;

import com.builpr.search.filter.MinimumRatingFilter;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * Class to store data of print-models to add them to the index of the search-engine
 */
public class IndexablePrintModel {

    /* TODO: Attribute auf Vollständigkeit prüfen */
    
    @Getter
    private int id;
    
    @Getter
    private String title;
    
    @Getter
    private String description;
    
    @Getter
    private String type;
    
    @Getter
    private int uploaderId;
    
    @Getter
    private Date uploadDate;
    
    @Getter
    private double rating;
    
    @Getter
    private List<String> tags;
    
    
    /**
     * Creates an IndexablePrintModel-object
     */
    private IndexablePrintModel() {
    }
    
    /**
     * Triggers the creation of a new Builder-object and returns it
     *
     * @return Builder-object
     */
    public static Builder getBuilder() {
        return new Builder();
    }
    
    /**
     * Internal Builder-class of IndexablePrintModel for public creations of IndexablePrintModel-objects
     */
    public static class Builder {
        
        private IndexablePrintModel toBuild;
        
        /**
         * Creates a new Builder-object
         * Triggers the creation of a new IndexablePrintModel-object and assigns it to the Builder-object
         */
        public Builder() {
            this.toBuild = new IndexablePrintModel();
        }
        
        /**
         * Adds the new parameter to the Builder-object and returns it
         *
         * @param id The id of the PrintModel
         * @return Builder-object with the new parameter added to it
         */
        public Builder withId(int id) {
            Preconditions.checkArgument(id >= 0);
            
            toBuild.id = id;
            
            return this;
        }
        
        /**
         * Adds the new parameter to the Builder-object and returns it
         *
         * @param title The title of the PrintModel
         * @return Builder-object with the new parameter added to it
         */
        public Builder withTitle(String title) {
            Preconditions.checkNotNull(title);
            
            toBuild.title = title;
            return this;
        }
        
        /**
         * Adds the new parameter to the Builder-object and returns it
         *
         * @param description The description of the PrintModel
         * @return Builder-object with the new parameter added to it
         */
        public Builder withDescription(String description) {
            Preconditions.checkNotNull(description);
            
            toBuild.description = description;
            return this;
        }
        
        /**
         * Adds the new parameter to the Builder-object and returns it
         *
         * @param type The (file-)type of the PrintModel
         * @return Builder-object with the new parameter added to it
         */
        public Builder withType(String type) {
            Preconditions.checkNotNull(type);
            
            toBuild.type = type;
            return this;
        }
        
        /**
         * Adds the new parameter to the Builder-object and returns it
         *
         * @param uploaderId The uploader-id of the PrintModel (references to the user who provided the PrintModel)
         * @return Builder-object with the new parameter added to it
         */
        public Builder withUploaderId(int uploaderId) {
            Preconditions.checkArgument(uploaderId >= 0);
            
            toBuild.uploaderId = uploaderId;
            return this;
        }
        
        /**
         * Adds the new parameter to the Builder-object and returns it
         *
         * @param uploadDate The upload-date of the PrintModel
         * @return Builder-object with the new parameter added to it
         */
        public Builder withUploadDate(Date uploadDate) {
            Preconditions.checkNotNull(uploadDate);
            
            toBuild.uploadDate = uploadDate;
            return this;
        }
        
        /**
         * Adds the new parameter to the Builder-object and returns it
         *
         * @param rating The rating of the PrintModel
         * @return Builder-object with the new parameter added to it
         */
        public Builder withRating(double rating) {
            Preconditions.checkArgument(rating >= MinimumRatingFilter.LOWEST_POSSIBLE_RATING);
            Preconditions.checkArgument(rating <= MinimumRatingFilter.HIGHEST_POSSIBLE_RATING);
            
            toBuild.rating = rating;
            return this;
        }
        
        /**
         * Adds the new parameter to the Builder-object and returns it
         *
         * @param tags List of Strings representing the tags of the PrintModel
         * @return Builder-object with the new parameter added to it
         */
        public Builder withTags(List<String> tags) {
            Preconditions.checkNotNull(tags);
            
            toBuild.tags = tags;
            return this;
        }
        
        /**
         * Verifies the proprierity of the builded IndexablePrintModel-object and returns it
         *
         * @return The builded IndexablePrintModel-object
         */
        public IndexablePrintModel build() {
            
            Verify.verifyNotNull(toBuild.title);
            Verify.verifyNotNull(toBuild.description);
            Verify.verify(toBuild.id >= 0);
            Verify.verifyNotNull(toBuild.type);
            //TODO: was ist, wenn kein Rating vorhanden?
            Verify.verify(toBuild.rating >= MinimumRatingFilter.LOWEST_POSSIBLE_RATING);
            Verify.verify(toBuild.rating <= MinimumRatingFilter.HIGHEST_POSSIBLE_RATING);
            Verify.verify(toBuild.uploaderId >= 0);
            Verify.verifyNotNull(toBuild.uploadDate);
            Verify.verifyNotNull(toBuild.tags);
            
            return this.toBuild;
        }
        
    }
    
}
