package com.builpr.database.db.builpr.comingsoon.generated;

import com.builpr.database.db.builpr.comingsoon.Comingsoon;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.util.OptionalUtil;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.TypeMapper;
import java.util.Optional;

/**
 * The generated base for the {@link
 * com.builpr.database.db.builpr.comingsoon.Comingsoon}-interface representing
 * entities of the {@code comingsoon}-table in the database.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public interface GeneratedComingsoon {
    
    /**
     * This Field corresponds to the {@link Comingsoon} field that can be
     * obtained using the {@link Comingsoon#getId()} method.
     */
    final IntField<Comingsoon, Integer> ID = IntField.create(
        Identifier.ID,
        Comingsoon::getId,
        Comingsoon::setId,
        TypeMapper.primitive(), 
        true
    );
    /**
     * This Field corresponds to the {@link Comingsoon} field that can be
     * obtained using the {@link Comingsoon#getEmail()} method.
     */
    final StringField<Comingsoon, String> EMAIL = StringField.create(
        Identifier.EMAIL,
        Comingsoon::getEmail,
        Comingsoon::setEmail,
        TypeMapper.identity(), 
        true
    );
    /**
     * This Field corresponds to the {@link Comingsoon} field that can be
     * obtained using the {@link Comingsoon#getActivated()} method.
     */
    final IntField<Comingsoon, Integer> ACTIVATED = IntField.create(
        Identifier.ACTIVATED,
        Comingsoon::getActivated,
        Comingsoon::setActivated,
        TypeMapper.primitive(), 
        false
    );
    /**
     * This Field corresponds to the {@link Comingsoon} field that can be
     * obtained using the {@link Comingsoon#getKey()} method.
     */
    final StringField<Comingsoon, String> KEY = StringField.create(
        Identifier.KEY,
        o -> OptionalUtil.unwrap(o.getKey()),
        Comingsoon::setKey,
        TypeMapper.identity(), 
        true
    );
    
    /**
     * Returns the id of this Comingsoon. The id field corresponds to the
     * database column builpr.com.builpr.comingsoon.id.
     * 
     * @return the id of this Comingsoon
     */
    int getId();
    
    /**
     * Returns the email of this Comingsoon. The email field corresponds to the
     * database column builpr.com.builpr.comingsoon.email.
     * 
     * @return the email of this Comingsoon
     */
    String getEmail();
    
    /**
     * Returns the activated of this Comingsoon. The activated field corresponds
     * to the database column builpr.com.builpr.comingsoon.activated.
     * 
     * @return the activated of this Comingsoon
     */
    int getActivated();
    
    /**
     * Returns the key of this Comingsoon. The key field corresponds to the
     * database column builpr.com.builpr.comingsoon.key.
     * 
     * @return the key of this Comingsoon
     */
    Optional<String> getKey();
    
    /**
     * Sets the id of this Comingsoon. The id field corresponds to the database
     * column builpr.com.builpr.comingsoon.id.
     * 
     * @param id to set of this Comingsoon
     * @return   this Comingsoon instance
     */
    Comingsoon setId(int id);
    
    /**
     * Sets the email of this Comingsoon. The email field corresponds to the
     * database column builpr.com.builpr.comingsoon.email.
     * 
     * @param email to set of this Comingsoon
     * @return      this Comingsoon instance
     */
    Comingsoon setEmail(String email);
    
    /**
     * Sets the activated of this Comingsoon. The activated field corresponds to
     * the database column builpr.com.builpr.comingsoon.activated.
     * 
     * @param activated to set of this Comingsoon
     * @return          this Comingsoon instance
     */
    Comingsoon setActivated(int activated);
    
    /**
     * Sets the key of this Comingsoon. The key field corresponds to the
     * database column builpr.com.builpr.comingsoon.key.
     * 
     * @param key to set of this Comingsoon
     * @return    this Comingsoon instance
     */
    Comingsoon setKey(String key);
    
    enum Identifier implements ColumnIdentifier<Comingsoon> {
        
        ID        ("id"),
        EMAIL     ("email"),
        ACTIVATED ("activated"),
        KEY       ("key");
        
        private final String columnName;
        private final TableIdentifier<Comingsoon> tableIdentifier;
        
        Identifier(String columnName) {
            this.columnName      = columnName;
            this.tableIdentifier = TableIdentifier.of(    getDbmsName(), 
                getSchemaName(), 
                getTableName());
        }
        
        @Override
        public String getDbmsName() {
            return "builpr.com";
        }
        
        @Override
        public String getSchemaName() {
            return "builpr";
        }
        
        @Override
        public String getTableName() {
            return "comingsoon";
        }
        
        @Override
        public String getColumnName() {
            return this.columnName;
        }
        
        @Override
        public TableIdentifier<Comingsoon> asTableIdentifier() {
            return this.tableIdentifier;
        }
    }
}