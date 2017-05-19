package com.builpr.database.db.builpr.customtags.generated;

import com.builpr.database.db.builpr.customtags.CustomTags;
import com.speedment.common.annotation.GeneratedCode;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The generated base implementation of the {@link
 * com.builpr.database.db.builpr.customtags.CustomTags}-interface.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedCustomTagsImpl implements CustomTags {
    
    private int tagId;
    private String tagName;
    
    protected GeneratedCustomTagsImpl() {
        
    }
    
    @Override
    public int getTagId() {
        return tagId;
    }
    
    @Override
    public String getTagName() {
        return tagName;
    }
    
    @Override
    public CustomTags setTagId(int tagId) {
        this.tagId = tagId;
        return this;
    }
    
    @Override
    public CustomTags setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }
    
    @Override
    public String toString() {
        final StringJoiner sj = new StringJoiner(", ", "{ ", " }");
        sj.add("tagId = "   + Objects.toString(getTagId()));
        sj.add("tagName = " + Objects.toString(getTagName()));
        return "CustomTagsImpl " + sj.toString();
    }
    
    @Override
    public boolean equals(Object that) {
        if (this == that) { return true; }
        if (!(that instanceof CustomTags)) { return false; }
        final CustomTags thatCustomTags = (CustomTags)that;
        if (this.getTagId() != thatCustomTags.getTagId()) {return false; }
        if (!Objects.equals(this.getTagName(), thatCustomTags.getTagName())) {return false; }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Integer.hashCode(getTagId());
        hash = 31 * hash + Objects.hashCode(getTagName());
        return hash;
    }
}