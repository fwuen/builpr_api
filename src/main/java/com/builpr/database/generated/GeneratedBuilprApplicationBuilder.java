package com.builpr.database.generated;

import com.builpr.database.BuilprApplication;
import com.builpr.database.BuilprApplicationBuilder;
import com.builpr.database.BuilprApplicationImpl;
import com.builpr.database.db.builpr.collection.CollectionManagerImpl;
import com.builpr.database.db.builpr.collection.CollectionSqlAdapter;
import com.builpr.database.db.builpr.comingsoon.ComingsoonManagerImpl;
import com.builpr.database.db.builpr.comingsoon.ComingsoonSqlAdapter;
import com.builpr.database.db.builpr.conversation.ConversationManagerImpl;
import com.builpr.database.db.builpr.conversation.ConversationSqlAdapter;
import com.builpr.database.db.builpr.message.MessageManagerImpl;
import com.builpr.database.db.builpr.message.MessageSqlAdapter;
import com.builpr.database.db.builpr.model.ModelManagerImpl;
import com.builpr.database.db.builpr.model.ModelSqlAdapter;
import com.builpr.database.db.builpr.rating.RatingManagerImpl;
import com.builpr.database.db.builpr.rating.RatingSqlAdapter;
import com.builpr.database.db.builpr.user.UserManagerImpl;
import com.builpr.database.db.builpr.user.UserSqlAdapter;
import com.builpr.database.db.builpr.usercategories.UserCategoriesManagerImpl;
import com.builpr.database.db.builpr.usercategories.UserCategoriesSqlAdapter;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.common.injector.Injector;
import com.speedment.runtime.core.internal.AbstractApplicationBuilder;

/**
 * A generated base {@link
 * com.speedment.runtime.core.internal.AbstractApplicationBuilder} class for the
 * {@link com.speedment.runtime.config.Project} named builpr.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedBuilprApplicationBuilder extends AbstractApplicationBuilder<BuilprApplication, BuilprApplicationBuilder> {
    
    protected GeneratedBuilprApplicationBuilder() {
        super(BuilprApplicationImpl.class, GeneratedBuilprMetadata.class);
        withManager(CollectionManagerImpl.class);
        withManager(ConversationManagerImpl.class);
        withManager(MessageManagerImpl.class);
        withManager(ModelManagerImpl.class);
        withManager(RatingManagerImpl.class);
        withManager(UserManagerImpl.class);
        withManager(UserCategoriesManagerImpl.class);
        withManager(ComingsoonManagerImpl.class);
        withComponent(CollectionSqlAdapter.class);
        withComponent(ConversationSqlAdapter.class);
        withComponent(MessageSqlAdapter.class);
        withComponent(ModelSqlAdapter.class);
        withComponent(RatingSqlAdapter.class);
        withComponent(UserSqlAdapter.class);
        withComponent(UserCategoriesSqlAdapter.class);
        withComponent(ComingsoonSqlAdapter.class);
    }
    
    @Override
    public BuilprApplication build(Injector injector) {
        return injector.getOrThrow(BuilprApplication.class);
    }
}