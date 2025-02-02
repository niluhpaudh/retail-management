package co.id.niluh.retail.management.api.open;

import co.id.niluh.retail.management.auth.model.AuditIdentity;
import co.id.niluh.retail.management.entity.auth.Product;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.service.ProductService;
import co.id.niluh.retail.management.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/open/product")
@Api(tags = "Master Product Data API")
public class OpenProductController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @GetMapping()
    public ResponseEntity<?> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct().toArray());
    }

    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody Product create){
        productService.createProduct(create, AuditIdentity.get());
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<?> updateProduct(@RequestBody Product update){
        productService.updateProduct(update, AuditIdentity.get());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId, AuditIdentity.get());
        return ResponseEntity.ok().build();
    }

}
