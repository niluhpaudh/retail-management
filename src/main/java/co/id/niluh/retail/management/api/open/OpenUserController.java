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
@RequestMapping("api/open/user")
@Api(tags = "Register API")
public class OpenUserController {
    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping()
    public ResponseEntity<?> addUserByAdmin(@RequestBody UserRegisModel create){
        userService.createUserByAdmin(create, AuditIdentity.get());
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody UserRegisModel update){
        userService.updateUser(update, AuditIdentity.get());
        return ResponseEntity.ok().build();
    }
    @PutMapping("activate/{userName}")
    public ResponseEntity<?> activeUser(@PathVariable String userName){
        userService.activeUser(userName, AuditIdentity.get());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName) {
        userService.deleteUser(userName, AuditIdentity.get());
        return ResponseEntity.ok().build();
    }

}
