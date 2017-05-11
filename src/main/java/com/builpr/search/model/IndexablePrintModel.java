package com.builpr.search.model;

import com.builpr.search.filter.MinimumRatingFilter;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;

import java.util.Date;
import java.util.List;

public class IndexablePrintModel {

    /* TODO: Diese Klasse stellt ein komplettes Daten-Model des 3D-Print-Solr-Models dar. Ergänze also alle Felder die indexiert werden müssen. */

    private int id;

    private String title;

    private String description;

    private int ageRestriction;

    private int uploaderId;

    private Date uploadDate;

    private double rating;

    private List<String> tags;





    private IndexablePrintModel() { }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private IndexablePrintModel toBuild;

        public Builder() {
            this.toBuild = new IndexablePrintModel();
        }

        public Builder withId(int id) {
            Preconditions.checkArgument(id >= 0);

            toBuild.id = id;

            return this;
        }
    
        public Builder withTitle(String title) {
            Preconditions.checkNotNull(title);
        
            toBuild.title = title;
            return this;
        }
    
        public Builder withDescription(String description) {
            Preconditions.checkNotNull(description);
        
            toBuild.description = description;
            return this;
        }

        public Builder withAgeRestriction(int ageRestriction) {
            Preconditions.checkArgument(ageRestriction >= 0);

            toBuild.ageRestriction = ageRestriction;
            return this;
        }

        public Builder withUploaderId(int uploaderId) {
            Preconditions.checkArgument(uploaderId >= 0);

            toBuild.uploaderId = uploaderId;
            return this;
        }

        public Builder withUploadDate(Date uploadDate) {
            Preconditions.checkNotNull(uploadDate);

            toBuild.uploadDate = uploadDate;
            return this;
        }

        public Builder withRating(double rating) {
            //TODO: so ok?
            Preconditions.checkArgument(rating >= MinimumRatingFilter.LOWEST_POSSIBLE_RATING);
            Preconditions.checkArgument(rating <= MinimumRatingFilter.HIGHEST_POSSIBLE_RATING);

            toBuild.rating = rating;
            return this;
        }

        public Builder withTags(List<String> tags) {
            Preconditions.checkNotNull(tags);

            toBuild.tags = tags;
            return this;
        }
        
        /* TODO: with-Methoden */

        public IndexablePrintModel build() {
            /* TODO: Hier mit Verify überprüfen ob alle Felder gesetzt sind */

            Verify.verifyNotNull(toBuild.title);
            Verify.verifyNotNull(toBuild.description);
            Verify.verify(toBuild.id >= 0);
            Verify.verify(toBuild.ageRestriction >= 0);
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
