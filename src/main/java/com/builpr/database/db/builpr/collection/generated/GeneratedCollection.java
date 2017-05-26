package com.builpr.database.db.builpr.collection.generated;

import com.builpr.database.db.builpr.collection.Collection;
import com.builpr.database.db.builpr.printable.Printable;
import com.builpr.database.db.builpr.user.User;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.IntForeignKeyField;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 * The generated base for the {@link
 * com.builpr.database.db.builpr.collection.Collection}-interface representing
 * entities of the {@code collection}-table in the database.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public interface GeneratedCollection {
    
    /**
     * This Field corresponds to the {@link Collection} field that can be
     * obtained using the {@link Collection#getUserId()} method.
     */
    IntForeignKeyField<Collection, Integer, User> USER_ID = IntForeignKeyField.create(
        Identifier.USER_ID,
        Collection::getUserId,
        Collection::setUserId,
        User.USER_ID,
        TypeMapper.primitive(), 
        false
    );
    /**
     * This Field corresponds to the {@link Collection} field that can be
     * obtained using the {@link Collection#getPrintableId()} method.
     */
    IntForeignKeyField<Collection, Integer, Printable> PRINTABLE_ID = IntForeignKeyField.create(
        Identifier.PRINTABLE_ID,
        Collection::getPrintableId,
        Collection::setPrintableId,
        Printable.PRINTABLE_ID,
        TypeMapper.primitive(), 
        false
    );
    
    /**
     * Returns the userId of this Collection. The userId field corresponds to
     * the database column builpr.builpr.collection.user_id.
     * 
     * @return the userId of this Collection
     */
    int getUserId();
    
    /**
     * Returns the printableId of this Collection. The printableId field
     * corresponds to the database column builpr.builpr.collection.printable_id.
     * 
     * @return the printableId of this Collection
     */
    int getPrintableId();
    
    /**
     * Sets the userId of this Collection. The userId field corresponds to the
     * database column builpr.builpr.collection.user_id.
     * 
     * @param userId to set of this Collection
     * @return       this Collection instance
     */
    Collection setUserId(int userId);
    
    /**
     * Sets the printableId of this Collection. The printableId field
     * corresponds to the database column builpr.builpr.collection.printable_id.
     * 
     * @param printableId to set of this Collection
     * @return            this Collection instance
     */
    Collection setPrintableId(int printableId);
    
    /**
     * Queries the specified manager for the referenced User. If no such User
     * exists, an {@code NullPointerException} will be thrown.
     * 
     * @param foreignManager the manager to query for the entity
     * @return               the foreign entity referenced
     */
    User findUserId(Manager<User> foreignManager);
    
    /**
     * Queries the specified manager for the referenced Printable. If no such
     * Printable exists, an {@code NullPointerException} will be thrown.
     * 
     * @param foreignManager the manager to query for the entity
     * @return               the foreign entity referenced
     */
    Printable findPrintableId(Manager<Printable> foreignManager);
    
    enum Identifier implements ColumnIdentifier<Collection> {
        
        USER_ID      ("user_id"),
        PRINTABLE_ID ("printable_id");
        
        private final String columnName;
        private final TableIdentifier<Collection> tableIdentifier;
        
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
            return "collection";
        }
        
        @Override
        public String getColumnName() {
            return this.columnName;
        }
        
        @Override
        public TableIdentifier<Collection> asTableIdentifier() {
            return this.tableIdentifier;
        }
    }
}