package _1.vietpq.job_hunter.util.Listener;

import java.time.Instant;

import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.util.SecurityUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserListener {
    @PrePersist
    private void beforeAnyCreate(User user){
        user.setCreatedAt(Instant.now());
        user.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
    @PreUpdate
    private void beforeAnyUpdate(User user){
        user.setUpdatedAt(Instant.now());
        user.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
 }
