package co.id.niluh.retail.management.api.open;

import co.id.niluh.retail.management.auth.model.AuditIdentity;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.model.UserRegisModel;
import co.id.niluh.retail.management.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/open/user/registration")
@Api(tags = "Register User API")
public class OpenUserRegistrationController {
    @Autowired
    UserService userService;
    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody UserRegisModel create){
        userService.createUser(create, AuditIdentity.get());
        return ResponseEntity.ok().build();
    }
}
