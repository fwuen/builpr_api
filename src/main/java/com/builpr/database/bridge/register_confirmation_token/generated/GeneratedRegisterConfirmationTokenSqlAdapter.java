package com.builpr.database.bridge.register_confirmation_token.generated;

import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationToken;
import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationTokenImpl;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.sql.SqlPersistenceComponent;
import com.speedment.runtime.core.component.sql.SqlStreamSupplierComponent;
import com.speedment.runtime.core.exception.SpeedmentException;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.speedment.common.injector.State.RESOLVED;

/**
 * The generated Sql Adapter for a {@link
 * com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationToken}
 * entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedRegisterConfirmationTokenSqlAdapter {
    
    private final TableIdentifier<RegisterConfirmationToken> tableIdentifier;
    
    protected GeneratedRegisterConfirmationTokenSqlAdapter() {
        this.tableIdentifier = TableIdentifier.of("builpr", "builpr", "register_confirmation_token");
    }
    
    @ExecuteBefore(RESOLVED)
    void installMethodName(@WithState(RESOLVED) SqlStreamSupplierComponent streamSupplierComponent, @WithState(RESOLVED) SqlPersistenceComponent persistenceComponent) {
        streamSupplierComponent.install(tableIdentifier, this::apply);
        persistenceComponent.install(tableIdentifier);
    }
    
    protected RegisterConfirmationToken apply(ResultSet resultSet) throws SpeedmentException {
        final RegisterConfirmationToken entity = createEntity();
        try {
            entity.setUserId( resultSet.getInt(1)    );
            entity.setToken(  resultSet.getString(2) );
        } catch (final SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        return entity;
    }
    
    protected RegisterConfirmationTokenImpl createEntity() {
        return new RegisterConfirmationTokenImpl();
    }
}