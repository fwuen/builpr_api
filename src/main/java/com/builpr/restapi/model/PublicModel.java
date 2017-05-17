package com.builpr.restapi.model;


import com.builpr.database.db.builpr.model.Model;
import lombok.Getter;

public class PublicModel {

    @Getter
    private int uid;
    @Getter
    private String title;
    @Getter
    private String description;
    @Getter
    private String file;
    @Getter
    private boolean age_restriction;

    public PublicModel(Model model) {
        title = model.getTitle();
        file = model.getFile();
        age_restriction = model.getAgeRestriction();
        uid = model.getUid();
        if (model.getDescription().isPresent()) {
            description = model.getDescription().get();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublicModel that = (PublicModel) o;

        if (age_restriction != that.age_restriction) return false;
        if (uid != that.uid) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return file != null ? file.equals(that.file) : that.file == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (age_restriction ? 1 : 0);
        result = 31 * result + uid;
        return result;
    }


}
