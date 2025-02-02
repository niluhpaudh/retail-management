package co.id.niluh.retail.management.api.open;

import co.id.niluh.retail.management.auth.model.AuditIdentity;
import co.id.niluh.retail.management.entity.auth.TransactionLog;
import co.id.niluh.retail.management.model.TransactionLogModel;
import co.id.niluh.retail.management.service.TransactionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/open/transaction")
@Api(tags = "Transaction API")
public class OpenTransactionController {
    @Autowired
    TransactionService transactionService;


    @PostMapping()
    public ResponseEntity<?> doTransaction(@RequestBody TransactionLogModel trx){
        transactionService.doTransaction(trx, AuditIdentity.get());
        return ResponseEntity.ok().build();
    }
}
