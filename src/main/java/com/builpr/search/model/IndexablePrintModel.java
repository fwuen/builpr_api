package com.builpr.search.model;

import com.builpr.search.filter.MinimumRatingFilter;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import lombok.Getter;

import java.util.Date;
import java.util.List;

public class IndexablePrintModel {

    /* TODO: Diese Klasse stellt ein komplettes Daten-Model des 3D-Print-Solr-Models dar. Ergänze also alle Felder die indexiert werden müssen. */

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
     *
     */
    private IndexablePrintModel() { }

    /**
     *
     * @return
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private IndexablePrintModel toBuild;

        /**
         *
         */
        public Builder() {
            this.toBuild = new IndexablePrintModel();
        }

        /**
         *
         * @param id
         * @return
         */
        public Builder withId(int id) {
            Preconditions.checkArgument(id >= 0);

            toBuild.id = id;

            return this;
        }

        /**
         *
         * @param title
         * @return
         */
        public Builder withTitle(String title) {
            Preconditions.checkNotNull(title);
        
            toBuild.title = title;
            return this;
        }

        /**
         *
         * @param description
         * @return
         */
        public Builder withDescription(String description) {
            Preconditions.checkNotNull(description);
        
            toBuild.description = description;
            return this;
        }

        /**
         *
         * @param type
         * @return
         */
        public Builder withType(String type) {
            Preconditions.checkNotNull(type);

            toBuild.type = type;
            return this;
        }

        /**
         *
         * @param uploaderId
         * @return
         */
        public Builder withUploaderId(int uploaderId) {
            Preconditions.checkArgument(uploaderId >= 0);

            toBuild.uploaderId = uploaderId;
            return this;
        }

        /**
         *
         * @param uploadDate
         * @return
         */
        public Builder withUploadDate(Date uploadDate) {
            Preconditions.checkNotNull(uploadDate);

            toBuild.uploadDate = uploadDate;
            return this;
        }

        /**
         *
         * @param rating
         * @return
         */
        public Builder withRating(double rating) {
            //TODO: so ok?
            Preconditions.checkArgument(rating >= MinimumRatingFilter.LOWEST_POSSIBLE_RATING);
            Preconditions.checkArgument(rating <= MinimumRatingFilter.HIGHEST_POSSIBLE_RATING);

            toBuild.rating = rating;
            return this;
        }

        /**
         *
         * @param tags
         * @return
         */
        public Builder withTags(List<String> tags) {
            Preconditions.checkNotNull(tags);

            toBuild.tags = tags;
            return this;
        }
        
        /* TODO: with-Methoden */

        /**
         *
         * @return
         */
        public IndexablePrintModel build() {
            /* TODO: Hier mit Verify überprüfen ob alle Felder gesetzt sind */

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
