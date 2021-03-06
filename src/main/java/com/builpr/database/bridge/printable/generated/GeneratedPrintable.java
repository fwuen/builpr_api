package com.builpr.database.bridge.printable.generated;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.user.User;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.util.OptionalUtil;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.IntForeignKeyField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.TypeMapper;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * The generated base for the {@link
 * com.builpr.database.bridge.printable.Printable}-interface representing
 * entities of the {@code printable}-table in the database.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public interface GeneratedPrintable {
    
    /**
     * This Field corresponds to the {@link Printable} field that can be
     * obtained using the {@link Printable#getPrintableId()} method.
     */
    IntField<Printable, Integer> PRINTABLE_ID = IntField.create(
        Identifier.PRINTABLE_ID,
        Printable::getPrintableId,
        Printable::setPrintableId,
        TypeMapper.primitive(), 
        true
    );
    /**
     * This Field corresponds to the {@link Printable} field that can be
     * obtained using the {@link Printable#getTitle()} method.
     */
    StringField<Printable, String> TITLE = StringField.create(
        Identifier.TITLE,
        Printable::getTitle,
        Printable::setTitle,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Printable} field that can be
     * obtained using the {@link Printable#getDescription()} method.
     */
    StringField<Printable, String> DESCRIPTION = StringField.create(
        Identifier.DESCRIPTION,
        o -> OptionalUtil.unwrap(o.getDescription()),
        Printable::setDescription,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Printable} field that can be
     * obtained using the {@link Printable#getUploaderId()} method.
     */
    IntForeignKeyField<Printable, Integer, User> UPLOADER_ID = IntForeignKeyField.create(
        Identifier.UPLOADER_ID,
        Printable::getUploaderId,
        Printable::setUploaderId,
        User.USER_ID,
        TypeMapper.primitive(), 
        false
    );
    /**
     * This Field corresponds to the {@link Printable} field that can be
     * obtained using the {@link Printable#getUploadTime()} method.
     */
    ComparableField<Printable, Timestamp, Timestamp> UPLOAD_TIME = ComparableField.create(
        Identifier.UPLOAD_TIME,
        Printable::getUploadTime,
        Printable::setUploadTime,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Printable} field that can be
     * obtained using the {@link Printable#getFilePath()} method.
     */
    StringField<Printable, String> FILE_PATH = StringField.create(
        Identifier.FILE_PATH,
        Printable::getFilePath,
        Printable::setFilePath,
        TypeMapper.identity(), 
        true
    );
    /**
     * This Field corresponds to the {@link Printable} field that can be
     * obtained using the {@link Printable#getNumDownloads()} method.
     */
    IntField<Printable, Integer> NUM_DOWNLOADS = IntField.create(
        Identifier.NUM_DOWNLOADS,
        Printable::getNumDownloads,
        Printable::setNumDownloads,
        TypeMapper.primitive(), 
        false
    );
    
    /**
     * Returns the printableId of this Printable. The printableId field
     * corresponds to the database column builpr.builpr.printable.printable_id.
     * 
     * @return the printableId of this Printable
     */
    int getPrintableId();
    
    /**
     * Returns the title of this Printable. The title field corresponds to the
     * database column builpr.builpr.printable.title.
     * 
     * @return the title of this Printable
     */
    String getTitle();
    
    /**
     * Returns the description of this Printable. The description field
     * corresponds to the database column builpr.builpr.printable.description.
     * 
     * @return the description of this Printable
     */
    Optional<String> getDescription();
    
    /**
     * Returns the uploaderId of this Printable. The uploaderId field
     * corresponds to the database column builpr.builpr.printable.uploader_id.
     * 
     * @return the uploaderId of this Printable
     */
    int getUploaderId();
    
    /**
     * Returns the uploadTime of this Printable. The uploadTime field
     * corresponds to the database column builpr.builpr.printable.upload_time.
     * 
     * @return the uploadTime of this Printable
     */
    Timestamp getUploadTime();
    
    /**
     * Returns the filePath of this Printable. The filePath field corresponds to
     * the database column builpr.builpr.printable.file_path.
     * 
     * @return the filePath of this Printable
     */
    String getFilePath();
    
    /**
     * Returns the numDownloads of this Printable. The numDownloads field
     * corresponds to the database column builpr.builpr.printable.num_downloads.
     * 
     * @return the numDownloads of this Printable
     */
    int getNumDownloads();
    
    /**
     * Sets the printableId of this Printable. The printableId field corresponds
     * to the database column builpr.builpr.printable.printable_id.
     * 
     * @param printableId to set of this Printable
     * @return            this Printable instance
     */
    Printable setPrintableId(int printableId);
    
    /**
     * Sets the title of this Printable. The title field corresponds to the
     * database column builpr.builpr.printable.title.
     * 
     * @param title to set of this Printable
     * @return      this Printable instance
     */
    Printable setTitle(String title);
    
    /**
     * Sets the description of this Printable. The description field corresponds
     * to the database column builpr.builpr.printable.description.
     * 
     * @param description to set of this Printable
     * @return            this Printable instance
     */
    Printable setDescription(String description);
    
    /**
     * Sets the uploaderId of this Printable. The uploaderId field corresponds
     * to the database column builpr.builpr.printable.uploader_id.
     * 
     * @param uploaderId to set of this Printable
     * @return           this Printable instance
     */
    Printable setUploaderId(int uploaderId);
    
    /**
     * Sets the uploadTime of this Printable. The uploadTime field corresponds
     * to the database column builpr.builpr.printable.upload_time.
     * 
     * @param uploadTime to set of this Printable
     * @return           this Printable instance
     */
    Printable setUploadTime(Timestamp uploadTime);
    
    /**
     * Sets the filePath of this Printable. The filePath field corresponds to
     * the database column builpr.builpr.printable.file_path.
     * 
     * @param filePath to set of this Printable
     * @return         this Printable instance
     */
    Printable setFilePath(String filePath);
    
    /**
     * Sets the numDownloads of this Printable. The numDownloads field
     * corresponds to the database column builpr.builpr.printable.num_downloads.
     * 
     * @param numDownloads to set of this Printable
     * @return             this Printable instance
     */
    Printable setNumDownloads(int numDownloads);
    
    /**
     * Queries the specified manager for the referenced User. If no such User
     * exists, an {@code NullPointerException} will be thrown.
     * 
     * @param foreignManager the manager to query for the entity
     * @return               the foreign entity referenced
     */
    User findUploaderId(Manager<User> foreignManager);
    
    enum Identifier implements ColumnIdentifier<Printable> {
        
        PRINTABLE_ID  ("printable_id"),
        TITLE         ("title"),
        DESCRIPTION   ("description"),
        UPLOADER_ID   ("uploader_id"),
        UPLOAD_TIME   ("upload_time"),
        FILE_PATH     ("file_path"),
        NUM_DOWNLOADS ("num_downloads");
        
        private final String columnName;
        private final TableIdentifier<Printable> tableIdentifier;
        
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
            return "printable";
        }
        
        @Override
        public String getColumnName() {
            return this.columnName;
        }
        
        @Override
        public TableIdentifier<Printable> asTableIdentifier() {
            return this.tableIdentifier;
        }
    }
}