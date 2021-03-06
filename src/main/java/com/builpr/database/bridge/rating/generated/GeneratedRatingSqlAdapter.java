package com.builpr.database.bridge.rating.generated;

import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.rating.RatingImpl;
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
 * com.builpr.database.bridge.rating.Rating} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedRatingSqlAdapter {
    
    private final TableIdentifier<Rating> tableIdentifier;
    
    protected GeneratedRatingSqlAdapter() {
        this.tableIdentifier = TableIdentifier.of("builpr", "builpr", "rating");
    }
    
    @ExecuteBefore(RESOLVED)
    void installMethodName(@WithState(RESOLVED) SqlStreamSupplierComponent streamSupplierComponent, @WithState(RESOLVED) SqlPersistenceComponent persistenceComponent) {
        streamSupplierComponent.install(tableIdentifier, this::apply);
        persistenceComponent.install(tableIdentifier);
    }
    
    protected Rating apply(ResultSet resultSet) throws SpeedmentException {
        final Rating entity = createEntity();
        try {
            entity.setUserId(      resultSet.getInt(1)       );
            entity.setPrintableId( resultSet.getInt(2)       );
            entity.setRating(      resultSet.getInt(3)       );
            entity.setMsg(         resultSet.getString(4)    );
            entity.setRatingTime(  resultSet.getTimestamp(5) );
            entity.setRatingId(    resultSet.getInt(6)       );
        } catch (final SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        return entity;
    }
    
    protected RatingImpl createEntity() {
        return new RatingImpl();
    }
}