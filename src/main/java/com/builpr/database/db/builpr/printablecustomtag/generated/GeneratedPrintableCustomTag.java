package com.builpr.database.db.builpr.printablecustomtag.generated;

import com.builpr.database.db.builpr.customtags.CustomTags;
import com.builpr.database.db.builpr.printable.Printable;
import com.builpr.database.db.builpr.printablecustomtag.PrintableCustomTag;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.IntForeignKeyField;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 * The generated base for the {@link
 * com.builpr.database.db.builpr.printablecustomtag.PrintableCustomTag}-interface
 * representing entities of the {@code PrintableCustomTag}-table in the
 * database.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public interface GeneratedPrintableCustomTag {
    
    /**
     * This Field corresponds to the {@link PrintableCustomTag} field that can
     * be obtained using the {@link PrintableCustomTag#getPrintableId()} method.
     */
    final IntForeignKeyField<PrintableCustomTag, Integer, Printable> PRINTABLE_ID = IntForeignKeyField.create(
        Identifier.PRINTABLE_ID,
        PrintableCustomTag::getPrintableId,
        PrintableCustomTag::setPrintableId,
        Printable.PRINTABLE_ID,
        TypeMapper.primitive(), 
        false
    );
    /**
     * This Field corresponds to the {@link PrintableCustomTag} field that can
     * be obtained using the {@link PrintableCustomTag#getTagId()} method.
     */
    final IntForeignKeyField<PrintableCustomTag, Integer, CustomTags> TAG_ID = IntForeignKeyField.create(
        Identifier.TAG_ID,
        PrintableCustomTag::getTagId,
        PrintableCustomTag::setTagId,
        CustomTags.TAG_ID,
        TypeMapper.primitive(), 
        false
    );
    
    /**
     * Returns the printableId of this PrintableCustomTag. The printableId field
     * corresponds to the database column
     * builpr.builpr.PrintableCustomTag.printable_id.
     * 
     * @return the printableId of this PrintableCustomTag
     */
    int getPrintableId();
    
    /**
     * Returns the tagId of this PrintableCustomTag. The tagId field corresponds
     * to the database column builpr.builpr.PrintableCustomTag.tag_id.
     * 
     * @return the tagId of this PrintableCustomTag
     */
    int getTagId();
    
    /**
     * Sets the printableId of this PrintableCustomTag. The printableId field
     * corresponds to the database column
     * builpr.builpr.PrintableCustomTag.printable_id.
     * 
     * @param printableId to set of this PrintableCustomTag
     * @return            this PrintableCustomTag instance
     */
    PrintableCustomTag setPrintableId(int printableId);
    
    /**
     * Sets the tagId of this PrintableCustomTag. The tagId field corresponds to
     * the database column builpr.builpr.PrintableCustomTag.tag_id.
     * 
     * @param tagId to set of this PrintableCustomTag
     * @return      this PrintableCustomTag instance
     */
    PrintableCustomTag setTagId(int tagId);
    
    /**
     * Queries the specified manager for the referenced Printable. If no such
     * Printable exists, an {@code NullPointerException} will be thrown.
     * 
     * @param foreignManager the manager to query for the entity
     * @return               the foreign entity referenced
     */
    Printable findPrintableId(Manager<Printable> foreignManager);
    
    /**
     * Queries the specified manager for the referenced CustomTags. If no such
     * CustomTags exists, an {@code NullPointerException} will be thrown.
     * 
     * @param foreignManager the manager to query for the entity
     * @return               the foreign entity referenced
     */
    CustomTags findTagId(Manager<CustomTags> foreignManager);
    
    enum Identifier implements ColumnIdentifier<PrintableCustomTag> {
        
        PRINTABLE_ID ("printable_id"),
        TAG_ID       ("tag_id");
        
        private final String columnName;
        private final TableIdentifier<PrintableCustomTag> tableIdentifier;
        
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
            return "PrintableCustomTag";
        }
        
        @Override
        public String getColumnName() {
            return this.columnName;
        }
        
        @Override
        public TableIdentifier<PrintableCustomTag> asTableIdentifier() {
            return this.tableIdentifier;
        }
    }
}