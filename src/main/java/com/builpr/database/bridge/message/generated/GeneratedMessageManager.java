package com.builpr.database.bridge.message.generated;

import com.builpr.database.bridge.message.Message;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.core.manager.Manager;

/**
 * The generated base interface for the manager of every {@link
 * com.builpr.database.bridge.message.Message} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public interface GeneratedMessageManager extends Manager<Message> {
    
    @Override
    default Class<Message> getEntityClass() {
        return Message.class;
    }
}