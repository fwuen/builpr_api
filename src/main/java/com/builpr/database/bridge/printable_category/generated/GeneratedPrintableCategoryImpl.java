package com.builpr.database.bridge.printable_category.generated;

import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable_category.PrintableCategory;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.core.manager.Manager;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The generated base implementation of the {@link
 * com.builpr.database.bridge.printable_category.PrintableCategory}-interface.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedPrintableCategoryImpl implements PrintableCategory {
    
    private int printableId;
    private int categoryId;
    
    protected GeneratedPrintableCategoryImpl() {
        
    }
    
    @Override
    public int getPrintableId() {
        return printableId;
    }
    
    @Override
    public int getCategoryId() {
        return categoryId;
    }
    
    @Override
    public PrintableCategory setPrintableId(int printableId) {
        this.printableId = printableId;
        return this;
    }
    
    @Override
    public PrintableCategory setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }
    
    @Override
    public Printable findPrintableId(Manager<Printable> foreignManager) {
        return foreignManager.stream().filter(Printable.PRINTABLE_ID.equal(getPrintableId())).findAny().orElse(null);
    }
    
    @Override
    public Category findCategoryId(Manager<Category> foreignManager) {
        return foreignManager.stream().filter(Category.CATEGORY_ID.equal(getCategoryId())).findAny().orElse(null);
    }
    
    @Override
    public String toString() {
        final StringJoiner sj = new StringJoiner(", ", "{ ", " }");
        sj.add("printableId = " + Objects.toString(getPrintableId()));
        sj.add("categoryId = "  + Objects.toString(getCategoryId()));
        return "PrintableCategoryImpl " + sj.toString();
    }
    
    @Override
    public boolean equals(Object that) {
        if (this == that) { return true; }
        if (!(that instanceof PrintableCategory)) { return false; }
        final PrintableCategory thatPrintableCategory = (PrintableCategory)that;
        if (this.getPrintableId() != thatPrintableCategory.getPrintableId()) {return false; }
        if (this.getCategoryId() != thatPrintableCategory.getCategoryId()) {return false; }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Integer.hashCode(getPrintableId());
        hash = 31 * hash + Integer.hashCode(getCategoryId());
        return hash;
    }
}