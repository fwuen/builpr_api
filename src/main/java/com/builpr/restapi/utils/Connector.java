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
import org.springframework.security.crypto.bcrypt.BCrypt;


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

}
