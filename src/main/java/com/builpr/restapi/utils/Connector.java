package com.builpr.restapi.utils;


import com.builpr.database.BuilprApplication;
import com.builpr.database.BuilprApplicationBuilder;
import com.builpr.database.db.builpr.collection.Collection;
import com.builpr.database.db.builpr.collection.CollectionManager;
import com.builpr.database.db.builpr.conversation.Conversation;
import com.builpr.database.db.builpr.conversation.ConversationManager;
import com.builpr.database.db.builpr.customtags.CustomTags;
import com.builpr.database.db.builpr.customtags.CustomTagsManager;
import com.builpr.database.db.builpr.message.MessageManager;
import com.builpr.database.db.builpr.printable.Printable;
import com.builpr.database.db.builpr.printable.PrintableManager;
import com.builpr.database.db.builpr.printablecategory.PrintableCategory;
import com.builpr.database.db.builpr.printablecategory.PrintableCategoryManager;
import com.builpr.database.db.builpr.printablecustomtag.PrintableCustomTag;
import com.builpr.database.db.builpr.printablecustomtag.PrintableCustomTagManager;
import com.builpr.database.db.builpr.rating.Rating;
import com.builpr.database.db.builpr.rating.RatingManager;
import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.user.UserManager;
import com.builpr.database.db.builpr.usercategories.UserCategories;
import com.builpr.database.db.builpr.usercategories.UserCategoriesManager;
import com.builpr.restapi.utils.help_interfaces.DatabaseObject;
import com.speedment.runtime.core.manager.Manager;


public class Connector {

    private static String schemaName;

    public static BuilprApplication getConnection() {
        if(schemaName == null) {
            return new BuilprApplicationBuilder().withPassword("builpr123").build();
        }else{
            return new BuilprApplicationBuilder().withSchema(schemaName).withPassword("builpr123").build();
        }

    }

    public static void setSchemaName(String newSchemaName) {
        schemaName = newSchemaName;
    }

    public static CollectionManager getCollectionManager() {
        return getConnection().getOrThrow(CollectionManager.class);
    }

    public static ConversationManager getConversationManager() {
        return getConnection().getOrThrow(ConversationManager.class);
    }

    public static CustomTagsManager getCustomTagsManager() {
        return getConnection().getOrThrow(CustomTagsManager.class);
    }

    public static MessageManager getMessageManager() {
        return getConnection().getOrThrow(MessageManager.class);
    }

    public static PrintableManager getPrintableManager() {
        return getConnection().getOrThrow(PrintableManager.class);
    }

    public static PrintableCategoryManager getPrintableCategoryManager() {
        return getConnection().getOrThrow(PrintableCategoryManager.class);
    }

    public static PrintableCustomTagManager getPrintableCustomTagManager() {
        return getConnection().getOrThrow(PrintableCustomTagManager.class);
    }

    public static RatingManager getRatingManager() {
        return getConnection().getOrThrow(RatingManager.class);
    }

    public static UserManager getUserManager() {
        return getConnection().getOrThrow(UserManager.class);
    }

    public static UserCategoriesManager getUserCateogriesManager() {
        return getConnection().getOrThrow(UserCategoriesManager.class);
    }

    public static Manager getManagerByDatabaseObject(DatabaseObject databaseObject) throws Exception{
        if (databaseObject instanceof Collection) {
            return getCollectionManager();
        } else if(databaseObject instanceof Conversation) {
            return getConversationManager();
        } else if (databaseObject instanceof CustomTags) {
            return getCustomTagsManager();
        } else if (databaseObject instanceof MessageManager) {
            return getMessageManager();
        } else if (databaseObject instanceof Printable) {
            return getPrintableManager();
        } else if (databaseObject instanceof PrintableCategory) {
            return getPrintableCategoryManager();
        } else if (databaseObject instanceof PrintableCustomTag) {
            return getPrintableCustomTagManager();
        } else if (databaseObject instanceof Rating) {
            return getRatingManager();
        } else if (databaseObject instanceof User) {
            return getUserManager();
        } else if (databaseObject instanceof UserCategories) {
            return getUserCateogriesManager();
        } else {
            throw new Exception("Wrong datatype!"); // @todo exception erstellen
        }
    }

}
