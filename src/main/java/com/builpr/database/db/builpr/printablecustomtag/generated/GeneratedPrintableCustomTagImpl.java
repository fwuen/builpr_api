package com.builpr.database.db.builpr.printablecustomtag.generated;

import com.builpr.database.db.builpr.customtags.CustomTags;
import com.builpr.database.db.builpr.printable.Printable;
import com.builpr.database.db.builpr.printablecustomtag.PrintableCustomTag;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.core.manager.Manager;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The generated base implementation of the {@link
 * com.builpr.database.db.builpr.printablecustomtag.PrintableCustomTag}-interface.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedPrintableCustomTagImpl implements PrintableCustomTag {
    
    private int printableId;
    private int tagId;
    
    protected GeneratedPrintableCustomTagImpl() {
        
    }
    
    @Override
    public int getPrintableId() {
        return printableId;
    }
    
    @Override
    public int getTagId() {
        return tagId;
    }
    
    @Override
    public PrintableCustomTag setPrintableId(int printableId) {
        this.printableId = printableId;
        return this;
    }
    
    @Override
    public PrintableCustomTag setTagId(int tagId) {
        this.tagId = tagId;
        return this;
    }
    
    @Override
    public Printable findPrintableId(Manager<Printable> foreignManager) {
        return foreignManager.stream().filter(Printable.PRINTABLE_ID.equal(getPrintableId())).findAny().orElse(null);
    }
    
    @Override
    public CustomTags findTagId(Manager<CustomTags> foreignManager) {
        return foreignManager.stream().filter(CustomTags.TAG_ID.equal(getTagId())).findAny().orElse(null);
    }
    
    @Override
    public String toString() {
        final StringJoiner sj = new StringJoiner(", ", "{ ", " }");
        sj.add("printableId = " + Objects.toString(getPrintableId()));
        sj.add("tagId = "       + Objects.toString(getTagId()));
        return "PrintableCustomTagImpl " + sj.toString();
    }
    
    @Override
    public boolean equals(Object that) {
        if (this == that) { return true; }
        if (!(that instanceof PrintableCustomTag)) { return false; }
        final PrintableCustomTag thatPrintableCustomTag = (PrintableCustomTag)that;
        if (this.getPrintableId() != thatPrintableCustomTag.getPrintableId()) {return false; }
        if (this.getTagId() != thatPrintableCustomTag.getTagId()) {return false; }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Integer.hashCode(getPrintableId());
        hash = 31 * hash + Integer.hashCode(getTagId());
        return hash;
    }
}