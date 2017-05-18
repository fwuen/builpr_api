package com.builpr.database.db.builpr.message.generated;

import com.builpr.database.db.builpr.message.Message;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.AbstractManager;
import com.speedment.runtime.field.Field;
import java.util.stream.Stream;

/**
 * The generated base implementation for the manager of every {@link
 * com.builpr.database.db.builpr.message.Message} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedMessageManagerImpl extends AbstractManager<Message> implements GeneratedMessageManager {
    
    private final TableIdentifier<Message> tableIdentifier;
    
    protected GeneratedMessageManagerImpl() {
        this.tableIdentifier = TableIdentifier.of("builpr", "builpr", "Message");
    }
    
    @Override
    public TableIdentifier<Message> getTableIdentifier() {
        return tableIdentifier;
    }
    
    @Override
    public Stream<Field<Message>> fields() {
        return Stream.of(
            Message.MESSAGE_ID,
            Message.SENDER,
            Message.RECEIVER,
            Message.CONTENT,
            Message.SUBJECT
        );
    }
    
    @Override
    public Stream<Field<Message>> primaryKeyFields() {
        return Stream.of(
            Message.MESSAGE_ID
        );
    }
}