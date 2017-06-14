package com.builpr.database.bridge.register_confirmation_token.generated;

import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationToken;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.AbstractManager;
import com.speedment.runtime.field.Field;
import java.util.stream.Stream;

/**
 * The generated base implementation for the manager of every {@link
 * com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationToken}
 * entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public abstract class GeneratedRegisterConfirmationTokenManagerImpl extends AbstractManager<RegisterConfirmationToken> implements GeneratedRegisterConfirmationTokenManager {
    
    private final TableIdentifier<RegisterConfirmationToken> tableIdentifier;
    
    protected GeneratedRegisterConfirmationTokenManagerImpl() {
        this.tableIdentifier = TableIdentifier.of("builpr", "builpr", "register_confirmation_token");
    }
    
    @Override
    public TableIdentifier<RegisterConfirmationToken> getTableIdentifier() {
        return tableIdentifier;
    }
    
    @Override
    public Stream<Field<RegisterConfirmationToken>> fields() {
        return Stream.of(
            RegisterConfirmationToken.USER_ID,
            RegisterConfirmationToken.TOKEN
        );
    }
    
    @Override
    public Stream<Field<RegisterConfirmationToken>> primaryKeyFields() {
        return Stream.of(
            RegisterConfirmationToken.TOKEN,
            RegisterConfirmationToken.USER_ID
        );
    }
}