package com.builpr.database.db.builpr.rating.generated;

import com.builpr.database.db.builpr.printable.Printable;
import com.builpr.database.db.builpr.rating.Rating;
import com.builpr.database.db.builpr.user.User;
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
import java.sql.Date;
import java.util.Optional;

/**
 * The generated base for the {@link
 * com.builpr.database.db.builpr.rating.Rating}-interface representing entities
 * of the {@code Rating}-table in the database.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public interface GeneratedRating {
    
    /**
     * This Field corresponds to the {@link Rating} field that can be obtained
     * using the {@link Rating#getUserId()} method.
     */
    final IntForeignKeyField<Rating, Integer, User> USER_ID = IntForeignKeyField.create(
        Identifier.USER_ID,
        Rating::getUserId,
        Rating::setUserId,
        User.USER_ID,
        TypeMapper.primitive(), 
        false
    );
    /**
     * This Field corresponds to the {@link Rating} field that can be obtained
     * using the {@link Rating#getPrintableId()} method.
     */
    final IntForeignKeyField<Rating, Integer, Printable> PRINTABLE_ID = IntForeignKeyField.create(
        Identifier.PRINTABLE_ID,
        Rating::getPrintableId,
        Rating::setPrintableId,
        Printable.PRINTABLE_ID,
        TypeMapper.primitive(), 
        false
    );
    /**
     * This Field corresponds to the {@link Rating} field that can be obtained
     * using the {@link Rating#getRating()} method.
     */
    final IntField<Rating, Integer> RATING = IntField.create(
        Identifier.RATING,
        Rating::getRating,
        Rating::setRating,
        TypeMapper.primitive(), 
        false
    );
    /**
     * This Field corresponds to the {@link Rating} field that can be obtained
     * using the {@link Rating#getMsg()} method.
     */
    final StringField<Rating, String> MSG = StringField.create(
        Identifier.MSG,
        o -> OptionalUtil.unwrap(o.getMsg()),
        Rating::setMsg,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Rating} field that can be obtained
     * using the {@link Rating#getRatingTime()} method.
     */
    final ComparableField<Rating, Date, Date> RATING_TIME = ComparableField.create(
        Identifier.RATING_TIME,
        Rating::getRatingTime,
        Rating::setRatingTime,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Rating} field that can be obtained
     * using the {@link Rating#getRatingId()} method.
     */
    final IntField<Rating, Integer> RATING_ID = IntField.create(
        Identifier.RATING_ID,
        Rating::getRatingId,
        Rating::setRatingId,
        TypeMapper.primitive(), 
        true
    );
    
    /**
     * Returns the userId of this Rating. The userId field corresponds to the
     * database column builpr.builpr.Rating.user_id.
     * 
     * @return the userId of this Rating
     */
    int getUserId();
    
    /**
     * Returns the printableId of this Rating. The printableId field corresponds
     * to the database column builpr.builpr.Rating.printable_id.
     * 
     * @return the printableId of this Rating
     */
    int getPrintableId();
    
    /**
     * Returns the rating of this Rating. The rating field corresponds to the
     * database column builpr.builpr.Rating.rating.
     * 
     * @return the rating of this Rating
     */
    int getRating();
    
    /**
     * Returns the msg of this Rating. The msg field corresponds to the database
     * column builpr.builpr.Rating.msg.
     * 
     * @return the msg of this Rating
     */
    Optional<String> getMsg();
    
    /**
     * Returns the ratingTime of this Rating. The ratingTime field corresponds
     * to the database column builpr.builpr.Rating.rating_time.
     * 
     * @return the ratingTime of this Rating
     */
    Date getRatingTime();
    
    /**
     * Returns the ratingId of this Rating. The ratingId field corresponds to
     * the database column builpr.builpr.Rating.rating_id.
     * 
     * @return the ratingId of this Rating
     */
    int getRatingId();
    
    /**
     * Sets the userId of this Rating. The userId field corresponds to the
     * database column builpr.builpr.Rating.user_id.
     * 
     * @param userId to set of this Rating
     * @return       this Rating instance
     */
    Rating setUserId(int userId);
    
    /**
     * Sets the printableId of this Rating. The printableId field corresponds to
     * the database column builpr.builpr.Rating.printable_id.
     * 
     * @param printableId to set of this Rating
     * @return            this Rating instance
     */
    Rating setPrintableId(int printableId);
    
    /**
     * Sets the rating of this Rating. The rating field corresponds to the
     * database column builpr.builpr.Rating.rating.
     * 
     * @param rating to set of this Rating
     * @return       this Rating instance
     */
    Rating setRating(int rating);
    
    /**
     * Sets the msg of this Rating. The msg field corresponds to the database
     * column builpr.builpr.Rating.msg.
     * 
     * @param msg to set of this Rating
     * @return    this Rating instance
     */
    Rating setMsg(String msg);
    
    /**
     * Sets the ratingTime of this Rating. The ratingTime field corresponds to
     * the database column builpr.builpr.Rating.rating_time.
     * 
     * @param ratingTime to set of this Rating
     * @return           this Rating instance
     */
    Rating setRatingTime(Date ratingTime);
    
    /**
     * Sets the ratingId of this Rating. The ratingId field corresponds to the
     * database column builpr.builpr.Rating.rating_id.
     * 
     * @param ratingId to set of this Rating
     * @return         this Rating instance
     */
    Rating setRatingId(int ratingId);
    
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
    
    enum Identifier implements ColumnIdentifier<Rating> {
        
        USER_ID      ("user_id"),
        PRINTABLE_ID ("printable_id"),
        RATING       ("rating"),
        MSG          ("msg"),
        RATING_TIME  ("rating_time"),
        RATING_ID    ("rating_id");
        
        private final String columnName;
        private final TableIdentifier<Rating> tableIdentifier;
        
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
            return "Rating";
        }
        
        @Override
        public String getColumnName() {
            return this.columnName;
        }
        
        @Override
        public TableIdentifier<Rating> asTableIdentifier() {
            return this.tableIdentifier;
        }
    }
}