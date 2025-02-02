package co.id.niluh.retail.management.api.open;

import co.id.niluh.retail.management.auth.model.AuditIdentity;
import co.id.niluh.retail.management.entity.auth.TransactionLog;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.model.ReportModel;
import co.id.niluh.retail.management.service.TransactionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RestController
@RequestMapping("api/open/report")
@Api(tags = "Report API")
public class OpenReportController {
    @Autowired
    TransactionService transactionService;

    @GetMapping()
    public ResponseEntity<?> getTransactionList(@RequestParam(name = "productId", required = false) String productId,
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "startDate", required = false) LocalDate startDate,
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "endDate", required = false) LocalDate endDate){

        return ResponseEntity.ok(
                transactionService.getTransactionList(startDate,endDate,productId));
    }
}

