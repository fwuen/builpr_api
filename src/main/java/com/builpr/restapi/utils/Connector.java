package com.builpr.restapi.utils;


import com.builpr.database.BuilprApplication;
import com.builpr.database.BuilprApplicationBuilder;
import com.builpr.database.db.builpr.collection.CollectionManager;
import com.builpr.database.db.builpr.conversation.ConversationManager;
import com.builpr.database.db.builpr.message.MessageManager;
import com.builpr.database.db.builpr.model.ModelManager;
import com.builpr.database.db.builpr.rating.RatingManager;
import com.builpr.database.db.builpr.user.UserManager;
import com.builpr.database.db.builpr.usercategories.UserCategoriesManager;

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

    public static MessageManager getMessageManager() {
        return getConnection().getOrThrow(MessageManager.class);
    }

    public static ModelManager getModelManager() {
        return getConnection().getOrThrow(ModelManager.class);
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

}
