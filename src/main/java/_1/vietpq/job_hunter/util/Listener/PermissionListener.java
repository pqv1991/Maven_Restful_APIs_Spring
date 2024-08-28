package _1.vietpq.job_hunter.util.Listener;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.domain.Permission;
import _1.vietpq.job_hunter.util.SecurityUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class PermissionListener {
    @PrePersist
    private void beforeAnyCreate(Permission permission){
        permission.setCreatedAt(Instant.now());
        permission.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent()? SecurityUtil.getCurrentUserLogin().get() : "" );
    }
    @PreUpdate
    private void beforeAnyUpdate(Permission permission){
        permission.setUpdatedAt(Instant.now());
        permission.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
 }
