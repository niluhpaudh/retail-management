package co.id.niluh.retail.management.security.token.security.token;

import co.id.niluh.retail.management.security.RefreshToken;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TokenStoreServiceImpl implements TokenStoreService {
	private final IMap<String, Token> tokenData;
	private final IMap<String, RefreshToken> refreshTokenData;

	@Value("${auth.expired.token:3}")
	private long tokenExpired;

	@Value("${auth.expired.refreshToken:3}")
	private long refreshTokenExpired;

	public TokenStoreServiceImpl(HazelcastInstance hazelcastInstance) {
		tokenData = hazelcastInstance.getMap("tokenData");
		refreshTokenData = hazelcastInstance.getMap("refreshTokenData");
	}

	@Override
	public void putToken(String key, Token token) {
		tokenData.put(key, token, refreshTokenExpired, TimeUnit.MINUTES);
	}

	@Override
	public Optional<Token> getToken(String key) {
		Token token = tokenData.get(key);
		if (token != null) {
			return Optional.of(token);
		}
		return Optional.empty();
	}

	@Override
	public void removeToken(String key) {
		tokenData.remove(key);
	}

	@Override
	public void putRefreshToken(String key, RefreshToken refreshToken) {
		refreshTokenData.put(key, refreshToken, refreshTokenExpired, TimeUnit.MINUTES);
	}

	@Override
	public Optional<RefreshToken> getRefreshToken(String key) {
		RefreshToken token = refreshTokenData.get(key);
		if (token != null) {
			return Optional.of(token);
		}
		return Optional.empty();
	}

	@Override
	public void removeRefreshToken(String key) {
		refreshTokenData.remove(key);
	}
}
