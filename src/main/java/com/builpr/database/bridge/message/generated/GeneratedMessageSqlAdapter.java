package com.builpr.database.bridge.message.generated;

import com.builpr.database.bridge.message.Message;
import com.builpr.database.bridge.message.MessageImpl;
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
 * com.builpr.database.bridge.message.Message} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedMessageSqlAdapter {
    
    private final TableIdentifier<Message> tableIdentifier;
    
    protected GeneratedMessageSqlAdapter() {
        this.tableIdentifier = TableIdentifier.of("builpr", "builpr", "message");
    }
    
    @ExecuteBefore(RESOLVED)
    void installMethodName(@WithState(RESOLVED) SqlStreamSupplierComponent streamSupplierComponent, @WithState(RESOLVED) SqlPersistenceComponent persistenceComponent) {
        streamSupplierComponent.install(tableIdentifier, this::apply);
        persistenceComponent.install(tableIdentifier);
    }
    
    protected Message apply(ResultSet resultSet) throws SpeedmentException {
        final Message entity = createEntity();
        try {
            entity.setMessageId(  resultSet.getInt(1)    );
            entity.setSenderId(   resultSet.getInt(2)    );
            entity.setReceiverId( resultSet.getInt(3)    );
            entity.setText(       resultSet.getString(4) );
        } catch (final SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        return entity;
    }
    
    protected MessageImpl createEntity() {
        return new MessageImpl();
    }
}