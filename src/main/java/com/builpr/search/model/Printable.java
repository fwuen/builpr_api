package com.builpr.search.model;

import com.builpr.search.filter.MinimumRatingFilter;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import lombok.Getter;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;
import java.util.List;

/**
 * Class to store data of print-models to add them to the index of the search-engine
 */
public class Printable extends Indexable {

    @Getter
    @Field
    private int id;
    
    @Getter
    @Field
    private String title;
    
    @Getter
    @Field
    private String description;

    @Getter
    @Field
    private int uploader_id;
    
    @Getter
    @Field
    private Date upload_date;
    
    @Getter
    @Field
    private double rating = 0;
    
    @Getter
    @Field
    private List<String> categories;

    @Getter
    @Field
    private int number_of_downloads;

    
    /**
     * Creates an Printable-object
     */
    private Printable() {
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
     * Internal Builder-class of Printable for public creations of Printable-objects
     */
    public static class Builder {
        
        private Printable toBuild;
        
        /**
         * Creates a new Builder-object
         * Triggers the creation of a new Printable-object and assigns it to the Builder-object
         */
        public Builder() {
            this.toBuild = new Printable();
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
         * @param uploaderId The uploader-id of the PrintModel (references to the user who provided the PrintModel)
         * @return Builder-object with the new parameter added to it
         */
        public Builder withUploaderId(int uploaderId) {
            Preconditions.checkArgument(uploaderId >= 0);
            
            toBuild.uploader_id = uploaderId;
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
            
            toBuild.upload_date = uploadDate;
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
         * @param categories List of Strings representing the categories of the PrintModel
         * @return Builder-object with the new parameter added to it
         */
        public Builder withCategories(List<String> categories) {
            Preconditions.checkNotNull(categories);
            
            toBuild.categories = categories;
            return this;
        }

        /**
         * Adds the new parameter to the Builder-object and returns it
         *
         * @param numberOfDownloads The number of Downloads the Indexable has reached
         * @return Builder-object with the new parameter added to it
         */
        public Builder withNumberOfDownloads(int numberOfDownloads) {
            Preconditions.checkArgument(numberOfDownloads > 0);

            toBuild.number_of_downloads = numberOfDownloads;
            return this;
        }
        
        /**
         * Verifies the proprierity of the builded Printable-object and returns it
         *
         * @return The builded Printable-object
         */
        public Printable build() {
            
            Verify.verifyNotNull(toBuild.title);
            Verify.verifyNotNull(toBuild.description);
            Verify.verify(toBuild.id > 0);
            Verify.verify(toBuild.rating >= MinimumRatingFilter.LOWEST_POSSIBLE_RATING);
            Verify.verify(toBuild.rating <= MinimumRatingFilter.HIGHEST_POSSIBLE_RATING);
            Verify.verify(toBuild.uploader_id > 0);
            Verify.verifyNotNull(toBuild.upload_date);
            Verify.verifyNotNull(toBuild.categories);
            Verify.verify(toBuild.number_of_downloads >= 0);
            
            return this.toBuild;
        }
        
    }
    
}
