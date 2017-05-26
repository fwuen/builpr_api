package com.builpr.restapi.utils;


import com.builpr.database.BuilprApplication;
import com.builpr.database.BuilprApplicationBuilder;
import com.builpr.database.db.builpr.collection.Collection;
import com.builpr.database.db.builpr.collection.CollectionManager;
import com.builpr.database.db.builpr.conversation.Conversation;
import com.builpr.database.db.builpr.conversation.ConversationManager;
import com.builpr.database.db.builpr.customtags.Customtags;
import com.builpr.database.db.builpr.customtags.CustomtagsManager;
import com.builpr.database.db.builpr.message.MessageManager;
import com.builpr.database.db.builpr.printable.Printable;
import com.builpr.database.db.builpr.printable.PrintableManager;
import com.builpr.database.db.builpr.printablecategory.Printablecategory;
import com.builpr.database.db.builpr.printablecategory.PrintablecategoryManager;
import com.builpr.database.db.builpr.printablecustomtag.Printablecustomtag;
import com.builpr.database.db.builpr.printablecustomtag.PrintablecustomtagManager;
import com.builpr.database.db.builpr.rating.Rating;
import com.builpr.database.db.builpr.rating.RatingManager;
import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.user.UserManager;
import com.builpr.database.db.builpr.usercategory.Usercategory;
import com.builpr.database.db.builpr.usercategory.UsercategoryManager;
import com.builpr.restapi.utils.help_interfaces.DatabaseObject;
import com.speedment.runtime.core.manager.Manager;
import org.springframework.security.crypto.bcrypt.BCrypt;


public class Connector {

    private static String schemaName;

    public static BuilprApplication getConnection() {
        if(schemaName == null) {
            return new BuilprApplicationBuilder().build();
        }else{
            return new BuilprApplicationBuilder().withSchema(schemaName).build();
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

    public static CustomtagsManager getCustomTagsManager() {
        return getConnection().getOrThrow(CustomtagsManager.class);
    }

    public static MessageManager getMessageManager() {
        return getConnection().getOrThrow(MessageManager.class);
    }

    public static PrintableManager getPrintableManager() {
        return getConnection().getOrThrow(PrintableManager.class);
    }

    public static PrintablecategoryManager getPrintableCategoryManager() {
        return getConnection().getOrThrow(PrintablecategoryManager.class);
    }

    public static PrintablecustomtagManager getPrintableCustomTagManager() {
        return getConnection().getOrThrow(PrintablecustomtagManager.class);
    }

    public static RatingManager getRatingManager() {
        return getConnection().getOrThrow(RatingManager.class);
    }

    public static UserManager getUserManager() {
        return getConnection().getOrThrow(UserManager.class);
    }

    public static UsercategoryManager getUserCateogriesManager() {
        return getConnection().getOrThrow(UsercategoryManager.class);
    }

    public static Manager getManagerByDatabaseObject(DatabaseObject databaseObject) throws Exception{
        if (databaseObject instanceof Collection) {
            return getCollectionManager();
        } else if(databaseObject instanceof Conversation) {
            return getConversationManager();
        } else if (databaseObject instanceof Customtags) {
            return getCustomTagsManager();
        } else if (databaseObject instanceof MessageManager) {
            return getMessageManager();
        } else if (databaseObject instanceof Printable) {
            return getPrintableManager();
        } else if (databaseObject instanceof Printablecategory) {
            return getPrintableCategoryManager();
        } else if (databaseObject instanceof Printablecustomtag) {
            return getPrintableCustomTagManager();
        } else if (databaseObject instanceof Rating) {
            return getRatingManager();
        } else if (databaseObject instanceof User) {
            return getUserManager();
        } else if (databaseObject instanceof Usercategory) {
            return getUserCateogriesManager();
        } else {
            throw new Exception("Wrong datatype!"); // @todo exception erstellen
        }
    }

}
