package com.builpr.database.db.builpr.printablecustomtag.generated;

import com.builpr.database.db.builpr.printablecustomtag.PrintableCustomTag;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.AbstractManager;
import com.speedment.runtime.field.Field;
import java.util.stream.Stream;

/**
 * The generated base implementation for the manager of every {@link
 * com.builpr.database.db.builpr.printablecustomtag.PrintableCustomTag} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedPrintableCustomTagManagerImpl extends AbstractManager<PrintableCustomTag> implements GeneratedPrintableCustomTagManager {
    
    private final TableIdentifier<PrintableCustomTag> tableIdentifier;
    
    protected GeneratedPrintableCustomTagManagerImpl() {
        this.tableIdentifier = TableIdentifier.of("builpr", "builpr", "PrintableCustomTag");
    }
    
    @Override
    public TableIdentifier<PrintableCustomTag> getTableIdentifier() {
        return tableIdentifier;
    }
    
    @Override
    public Stream<Field<PrintableCustomTag>> fields() {
        return Stream.of(
            PrintableCustomTag.PRINTABLE_ID,
            PrintableCustomTag.TAG_ID
        );
    }
    
    @Override
    public Stream<Field<PrintableCustomTag>> primaryKeyFields() {
        return Stream.of(
            PrintableCustomTag.PRINTABLE_ID,
            PrintableCustomTag.TAG_ID
        );
    }
}