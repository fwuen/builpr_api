package com.builpr.database.bridge.printable_category.generated;

import com.builpr.database.bridge.printable_category.PrintableCategory;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.AbstractManager;
import com.speedment.runtime.field.Field;
import java.util.stream.Stream;

/**
 * The generated base implementation for the manager of every {@link
 * com.builpr.database.bridge.printable_category.PrintableCategory} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedPrintableCategoryManagerImpl extends AbstractManager<PrintableCategory> implements GeneratedPrintableCategoryManager {
    
    private final TableIdentifier<PrintableCategory> tableIdentifier;
    
    protected GeneratedPrintableCategoryManagerImpl() {
        this.tableIdentifier = TableIdentifier.of("builpr", "builpr", "printable_category");
    }
    
    @Override
    public TableIdentifier<PrintableCategory> getTableIdentifier() {
        return tableIdentifier;
    }
    
    @Override
    public Stream<Field<PrintableCategory>> fields() {
        return Stream.of(
            PrintableCategory.PRINTABLE_ID,
            PrintableCategory.CATEGORY_ID
        );
    }
    
    @Override
    public Stream<Field<PrintableCategory>> primaryKeyFields() {
        return Stream.of(
            PrintableCategory.CATEGORY_ID,
            PrintableCategory.PRINTABLE_ID
        );
    }
}