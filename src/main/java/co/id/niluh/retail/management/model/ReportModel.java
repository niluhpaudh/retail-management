package co.id.niluh.retail.management.model;

import co.id.niluh.retail.management.enumz.UserStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReportModel {

    private LocalDateTime StartDate;
    private LocalDateTime endDate;
    private String productId;

    public ReportModel(){};

    public ReportModel(LocalDateTime startDate, LocalDateTime endDate, String productId) {
        StartDate = startDate;
        this.endDate = endDate;
        this.productId = productId;
    }

    public LocalDateTime getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        StartDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
