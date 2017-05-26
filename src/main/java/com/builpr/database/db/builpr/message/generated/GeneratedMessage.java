package com.builpr.database.db.builpr.message.generated;

import com.builpr.database.db.builpr.message.Message;
import com.builpr.database.db.builpr.user.User;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.IntForeignKeyField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 * The generated base for the {@link
 * com.builpr.database.db.builpr.message.Message}-interface representing
 * entities of the {@code message}-table in the database.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public interface GeneratedMessage {
    
    /**
     * This Field corresponds to the {@link Message} field that can be obtained
     * using the {@link Message#getMessageId()} method.
     */
    IntField<Message, Integer> MESSAGE_ID = IntField.create(
        Identifier.MESSAGE_ID,
        Message::getMessageId,
        Message::setMessageId,
        TypeMapper.primitive(), 
        true
    );
    /**
     * This Field corresponds to the {@link Message} field that can be obtained
     * using the {@link Message#getSender()} method.
     */
    IntForeignKeyField<Message, Integer, User> SENDER = IntForeignKeyField.create(
        Identifier.SENDER,
        Message::getSender,
        Message::setSender,
        User.USER_ID,
        TypeMapper.primitive(), 
        false
    );
    /**
     * This Field corresponds to the {@link Message} field that can be obtained
     * using the {@link Message#getReceiver()} method.
     */
    IntForeignKeyField<Message, Integer, User> RECEIVER = IntForeignKeyField.create(
        Identifier.RECEIVER,
        Message::getReceiver,
        Message::setReceiver,
        User.USER_ID,
        TypeMapper.primitive(), 
        false
    );
    /**
     * This Field corresponds to the {@link Message} field that can be obtained
     * using the {@link Message#getContent()} method.
     */
    StringField<Message, String> CONTENT = StringField.create(
        Identifier.CONTENT,
        Message::getContent,
        Message::setContent,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Message} field that can be obtained
     * using the {@link Message#getSubject()} method.
     */
    StringField<Message, String> SUBJECT = StringField.create(
        Identifier.SUBJECT,
        Message::getSubject,
        Message::setSubject,
        TypeMapper.identity(), 
        false
    );
    
    /**
     * Returns the messageId of this Message. The messageId field corresponds to
     * the database column builpr.builpr.message.message_id.
     * 
     * @return the messageId of this Message
     */
    int getMessageId();
    
    /**
     * Returns the sender of this Message. The sender field corresponds to the
     * database column builpr.builpr.message.sender.
     * 
     * @return the sender of this Message
     */
    int getSender();
    
    /**
     * Returns the receiver of this Message. The receiver field corresponds to
     * the database column builpr.builpr.message.receiver.
     * 
     * @return the receiver of this Message
     */
    int getReceiver();
    
    /**
     * Returns the content of this Message. The content field corresponds to the
     * database column builpr.builpr.message.content.
     * 
     * @return the content of this Message
     */
    String getContent();
    
    /**
     * Returns the subject of this Message. The subject field corresponds to the
     * database column builpr.builpr.message.subject.
     * 
     * @return the subject of this Message
     */
    String getSubject();
    
    /**
     * Sets the messageId of this Message. The messageId field corresponds to
     * the database column builpr.builpr.message.message_id.
     * 
     * @param messageId to set of this Message
     * @return          this Message instance
     */
    Message setMessageId(int messageId);
    
    /**
     * Sets the sender of this Message. The sender field corresponds to the
     * database column builpr.builpr.message.sender.
     * 
     * @param sender to set of this Message
     * @return       this Message instance
     */
    Message setSender(int sender);
    
    /**
     * Sets the receiver of this Message. The receiver field corresponds to the
     * database column builpr.builpr.message.receiver.
     * 
     * @param receiver to set of this Message
     * @return         this Message instance
     */
    Message setReceiver(int receiver);
    
    /**
     * Sets the content of this Message. The content field corresponds to the
     * database column builpr.builpr.message.content.
     * 
     * @param content to set of this Message
     * @return        this Message instance
     */
    Message setContent(String content);
    
    /**
     * Sets the subject of this Message. The subject field corresponds to the
     * database column builpr.builpr.message.subject.
     * 
     * @param subject to set of this Message
     * @return        this Message instance
     */
    Message setSubject(String subject);
    
    /**
     * Queries the specified manager for the referenced User. If no such User
     * exists, an {@code NullPointerException} will be thrown.
     * 
     * @param foreignManager the manager to query for the entity
     * @return               the foreign entity referenced
     */
    User findSender(Manager<User> foreignManager);
    
    /**
     * Queries the specified manager for the referenced User. If no such User
     * exists, an {@code NullPointerException} will be thrown.
     * 
     * @param foreignManager the manager to query for the entity
     * @return               the foreign entity referenced
     */
    User findReceiver(Manager<User> foreignManager);
    
    enum Identifier implements ColumnIdentifier<Message> {
        
        MESSAGE_ID ("message_id"),
        SENDER     ("sender"),
        RECEIVER   ("receiver"),
        CONTENT    ("content"),
        SUBJECT    ("subject");
        
        private final String columnName;
        private final TableIdentifier<Message> tableIdentifier;
        
        Identifier(String columnName) {
            this.columnName      = columnName;
            this.tableIdentifier = TableIdentifier.of(    getDbmsName(), 
                getSchemaName(), 
                getTableName());
        }
        
        @Override
        public String getDbmsName() {
            return "builpr";
        }
        
        @Override
        public String getSchemaName() {
            return "builpr";
        }
        
        @Override
        public String getTableName() {
            return "message";
        }
        
        @Override
        public String getColumnName() {
            return this.columnName;
        }
        
        @Override
        public TableIdentifier<Message> asTableIdentifier() {
            return this.tableIdentifier;
        }
    }
}