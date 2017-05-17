package com.builpr.database.db.builpr.comingsoon.generated;

import com.builpr.database.db.builpr.comingsoon.Comingsoon;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.AbstractManager;
import com.speedment.runtime.field.Field;
import java.util.stream.Stream;

/**
 * The generated base implementation for the manager of every {@link
 * com.builpr.database.db.builpr.comingsoon.Comingsoon} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedComingsoonManagerImpl extends AbstractManager<Comingsoon> implements GeneratedComingsoonManager {
    
    private final TableIdentifier<Comingsoon> tableIdentifier;
    
    protected GeneratedComingsoonManagerImpl() {
        this.tableIdentifier = TableIdentifier.of("builpr.com", "builpr", "comingsoon");
    }
    
    @Override
    public TableIdentifier<Comingsoon> getTableIdentifier() {
        return tableIdentifier;
    }
    
    @Override
    public Stream<Field<Comingsoon>> fields() {
        return Stream.of(
            Comingsoon.ID,
            Comingsoon.EMAIL,
            Comingsoon.ACTIVATED,
            Comingsoon.KEY
        );
    }
    
    @Override
    public Stream<Field<Comingsoon>> primaryKeyFields() {
        return Stream.of(
            Comingsoon.ID
        );
    }
}