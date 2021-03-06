package com.builpr.database.bridge.printable.generated;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
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
 * com.builpr.database.bridge.printable.Printable} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedPrintableSqlAdapter {
    
    private final TableIdentifier<Printable> tableIdentifier;
    
    protected GeneratedPrintableSqlAdapter() {
        this.tableIdentifier = TableIdentifier.of("builpr", "builpr", "printable");
    }
    
    @ExecuteBefore(RESOLVED)
    void installMethodName(@WithState(RESOLVED) SqlStreamSupplierComponent streamSupplierComponent, @WithState(RESOLVED) SqlPersistenceComponent persistenceComponent) {
        streamSupplierComponent.install(tableIdentifier, this::apply);
        persistenceComponent.install(tableIdentifier);
    }
    
    protected Printable apply(ResultSet resultSet) throws SpeedmentException {
        final Printable entity = createEntity();
        try {
            entity.setPrintableId(  resultSet.getInt(1)       );
            entity.setTitle(        resultSet.getString(2)    );
            entity.setDescription(  resultSet.getString(3)    );
            entity.setUploaderId(   resultSet.getInt(4)       );
            entity.setUploadTime(   resultSet.getTimestamp(5) );
            entity.setFilePath(     resultSet.getString(6)    );
            entity.setNumDownloads( resultSet.getInt(7)       );
        } catch (final SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        return entity;
    }
    
    protected PrintableImpl createEntity() {
        return new PrintableImpl();
    }
}