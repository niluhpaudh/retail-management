package co.id.niluh.retail.management.api.open;

import co.id.niluh.retail.management.auth.model.AuditIdentity;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.enumz.ErrorCodes;
import co.id.niluh.retail.management.exception.AuthenticationException;
import co.id.niluh.retail.management.security.token.AccessToken;
import co.id.niluh.retail.management.security.token.security.token.TokenConfiguration;
import co.id.niluh.retail.management.security.token.security.token.TokenProvider;
import co.id.niluh.retail.management.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("api/open/auth")
@Api(tags = "Authentifikasi API")
public class OpenAuthController {
    @Autowired
    UserService userService;

    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(value = "login")
    public ResponseEntity<?> authorize(@RequestBody AccessToken accessToken) {
        Authentication authentication;
        switch (accessToken.getGranType()){
            case refresh_token:
                authentication = tokenProvider.getAuthenticationRefreshToken(accessToken.getRefreshToken());
                break;
            case password:
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accessToken.getUsername(), accessToken.getPassword());
                authentication = this.authenticationManager.authenticate(authenticationToken);
                break;
            default:
                throw new AuthenticationException(ErrorCodes.BAD_CREDENTIAL.getDefaultMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(tokenProvider.createToken(authentication));
    }

    @GetMapping(value = "logout")
    public ResponseEntity<?> logout(HttpServletRequest request, @RequestParam String userId){
        String token = request.getHeader(TokenConfiguration.AUTHORIZATION_HEADER);
        if(token != null){
            tokenProvider.revokeToken(token);
        }
        userService.logout(userId);
        return ResponseEntity.ok(userId);
    }

}
