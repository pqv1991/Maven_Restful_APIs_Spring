package _1.vietpq.job_hunter.util.Listener;

import java.time.Instant;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.util.SecurityUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class CompanyListener {
    @PrePersist
    private void beforeAnyCreate(Company company){
        company.setCreatedAt(Instant.now());
        company.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent()? SecurityUtil.getCurrentUserLogin().get() : "" );
    }
    @PreUpdate
    private void beforeAnyUpdate(Company company){
        company.setUpdatedAt(Instant.now());
        company.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
 }
