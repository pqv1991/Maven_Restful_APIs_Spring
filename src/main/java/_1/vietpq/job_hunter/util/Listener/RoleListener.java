package _1.vietpq.job_hunter.util.Listener;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.domain.Role;
import _1.vietpq.job_hunter.util.SecurityUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class RoleListener {
    @PrePersist
    private void beforeAnyCreate(Role role){
        role.setCreatedAt(Instant.now());
        role.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent()? SecurityUtil.getCurrentUserLogin().get() : "" );
    }
    @PreUpdate
    private void beforeAnyUpdate(Role role){
        role.setUpdatedAt(Instant.now());
        role.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
 }
