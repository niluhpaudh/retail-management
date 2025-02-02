package co.id.niluh.retail.management.security.token.security.token;

import co.id.niluh.retail.management.auth.model.UserDetails;
import co.id.niluh.retail.management.exception.AuthenticationException;
import co.id.niluh.retail.management.security.RefreshToken;
import co.id.niluh.retail.management.security.SecurityAudit;

import co.id.niluh.retail.management.security.token.security.LoginDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

	private TokenStoreService tokenStoreService;

	public TokenProvider(TokenStoreService tokenStoreService) {
		this.tokenStoreService = tokenStoreService;
	}

	public TokenData createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.joining(","));
		if (authentication == null || authentication.getPrincipal() == null) {
			throw new RuntimeException("Authentication failed or principal is null");
		}

		String key = authentication.getPrincipal() instanceof UserDetails ?
				((UserDetails) authentication.getPrincipal()).getUsername() :
				authentication.getPrincipal().toString();

		String token = generateToken(key);
		String refreshToken = generateToken(key);
		Token tokenStore = new Token();
		tokenStore.setToken(token);
		tokenStore.setRefreshToken(refreshToken);
		tokenStore.setAuthorities(authorities);
		tokenStore.setInfo(authentication.getPrincipal());
		tokenStoreService.putToken(key, tokenStore);

		RefreshToken refreshTokenStore = new RefreshToken();
		refreshTokenStore.setRefreshToken(refreshToken);
		refreshTokenStore.setAuthorities(authorities);
		refreshTokenStore.setInfo(authentication.getPrincipal());
		tokenStoreService.putRefreshToken(key, refreshTokenStore);
		return new TokenData(token, refreshToken, authentication.getPrincipal());
	}

	public void revokeToken(String token) {
		tokenStoreService.removeToken(getSubject(token));
		tokenStoreService.removeRefreshToken(getSubject(token));
	}

	public Authentication getAuthenticationToken(String token) {
		String subject = getSubject(token);
		Token tokenStore = tokenStoreService.getToken(subject).orElseThrow(() -> new AuthenticationException("Akes Tidak diizinkan"));
		if (!tokenStore.getToken().equals(token)) {
			throw new AuthenticationException("Akes Tidak diizinkan");
		}
		tokenStoreService.putToken(subject, tokenStore);
		RefreshToken refreshTokenStore = new RefreshToken();
		refreshTokenStore.setRefreshToken(tokenStore.getRefreshToken());
		refreshTokenStore.setAuthorities(tokenStore.getAuthorities());
		refreshTokenStore.setInfo(tokenStore.getInfo());
		tokenStoreService.putRefreshToken(subject, refreshTokenStore);
		List<GrantedAuthority> authorities = Arrays.asList(tokenStore.getAuthorities().split(",")).stream()
				.map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
		LoginDetails details = new LoginDetails((LoginDetails) tokenStore.getInfo(), authorities, new SecurityAudit());
		return new UsernamePasswordAuthenticationToken(details, "", authorities);
	}

	public Authentication getAuthenticationRefreshToken(String refreshToken) {
		String subject = getSubject(refreshToken);
		RefreshToken tokenStoreRefresh = tokenStoreService.getRefreshToken(subject).orElseThrow(() -> new AuthenticationException("Akses tidak diizinkan"));
		if (!tokenStoreRefresh.getRefreshToken().equals(refreshToken)) {
			throw new AuthenticationException("Akses tidak diizinkan");
		}
		String tokenNew = generateToken(subject);
		String refreshTokenNew = generateToken(subject);
		Token tokenStoreNew = new Token();
		tokenStoreNew.setToken(tokenNew);
		tokenStoreNew.setRefreshToken(refreshTokenNew);
		tokenStoreNew.setAuthorities(tokenStoreRefresh.getAuthorities());
		tokenStoreNew.setInfo(tokenStoreRefresh.getInfo());
		tokenStoreService.putToken(subject, tokenStoreNew);

		RefreshToken refreshTokenStoreNew = new RefreshToken();
		refreshTokenStoreNew.setRefreshToken(refreshToken);
		refreshTokenStoreNew.setAuthorities(tokenStoreRefresh.getAuthorities());
		refreshTokenStoreNew.setInfo(tokenStoreRefresh.getInfo());
		tokenStoreService.putRefreshToken(subject, refreshTokenStoreNew);

		List<GrantedAuthority> authorities = Arrays.asList(tokenStoreRefresh.getAuthorities().split(",")).stream()
				.map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
		LoginDetails details = new LoginDetails((LoginDetails) tokenStoreRefresh.getInfo(), authorities, new SecurityAudit());
		return new UsernamePasswordAuthenticationToken(details, "", authorities);
	}

	public String getSubject(String token) {
		String subject = token.split("\\$")[0];
		try {
			return new String(Base64.getDecoder().decode(subject), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new AuthenticationException("Akses Tidak diizinkan");
		}
	}

	public String generateToken(String subject) {
		try {
			return new String(Base64.getEncoder().encode(subject.getBytes()), "UTF-8") + "$" + UUID.randomUUID().toString();
		} catch (UnsupportedEncodingException e) {
			throw new AuthenticationException("Akses Tidak diizinkan");
		}
	}

}
