package com.builpr.database.db.builpr.printable_category.generated;

import com.builpr.database.db.builpr.printable_category.PrintableCategory;
import com.builpr.database.db.builpr.printable_category.PrintableCategoryImpl;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.sql.SqlPersistenceComponent;
import com.speedment.runtime.core.component.sql.SqlStreamSupplierComponent;
import com.speedment.runtime.core.exception.SpeedmentException;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.speedment.common.injector.State.RESOLVED;

/**
 * The generated Sql Adapter for a {@link
 * com.builpr.database.db.builpr.printable_category.PrintableCategory} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedPrintableCategorySqlAdapter {
    
    private final TableIdentifier<PrintableCategory> tableIdentifier;
    
    protected GeneratedPrintableCategorySqlAdapter() {
        this.tableIdentifier = TableIdentifier.of("builpr", "builpr", "printable_category");
    }
    
    @ExecuteBefore(RESOLVED)
    void installMethodName(@WithState(RESOLVED) SqlStreamSupplierComponent streamSupplierComponent, @WithState(RESOLVED) SqlPersistenceComponent persistenceComponent) {
        streamSupplierComponent.install(tableIdentifier, this::apply);
        persistenceComponent.install(tableIdentifier);
    }
    
    protected PrintableCategory apply(ResultSet resultSet) throws SpeedmentException {
        final PrintableCategory entity = createEntity();
        try {
            entity.setPrintableId( resultSet.getInt(1) );
            entity.setCategoryId(  resultSet.getInt(2) );
        } catch (final SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        return entity;
    }
    
    protected PrintableCategoryImpl createEntity() {
        return new PrintableCategoryImpl();
    }
}