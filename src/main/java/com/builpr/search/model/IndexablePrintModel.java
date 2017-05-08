package com.builpr.search.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;

public class IndexablePrintModel {

    /* TODO: Diese Klasse stellt ein komplettes Daten-Model des 3D-Print-Solr-Models dar. Ergänze also alle Felder die indexiert werden müssen. */

    private int id;

    private String title;

    private String description;



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
            Preconditions.checkArgument(id > 0);

            toBuild.id = id;

            return this;
        }

        /* TODO: with-Methoden */

        public IndexablePrintModel build() {
            /* TODO: Hier mit Verify überprüfen ob alle Felder gesetzt sind */

            Verify.verifyNotNull(toBuild.title);
            Verify.verifyNotNull(toBuild.description);
            Verify.verify(toBuild.id > 0);

            return this.toBuild;
        }

    }

}
