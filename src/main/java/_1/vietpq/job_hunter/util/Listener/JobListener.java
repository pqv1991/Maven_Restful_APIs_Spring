package _1.vietpq.job_hunter.util.Listener;

import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.util.SecurityUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class JobListener {
    @PrePersist
    private void beforeAnyCreate(Job job){
        job.setCreatedAt(Instant.now());
        job.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
    @PreUpdate
    private void beforeAnyUpdate(Job job){
        job.setUpdatedAt(Instant.now());
        job.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
 }
