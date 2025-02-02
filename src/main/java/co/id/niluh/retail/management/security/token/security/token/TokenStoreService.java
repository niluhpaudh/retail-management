package co.id.niluh.retail.management.security.token.security.token;

import co.id.niluh.retail.management.security.RefreshToken;

import java.util.Optional;

public interface TokenStoreService {

	void putToken(String key, Token token);

	Optional<Token> getToken(String key);

	void removeToken(String key);

	void putRefreshToken(String key, RefreshToken refreshToken);

	Optional<RefreshToken> getRefreshToken(String key);

	void removeRefreshToken(String key);
}
