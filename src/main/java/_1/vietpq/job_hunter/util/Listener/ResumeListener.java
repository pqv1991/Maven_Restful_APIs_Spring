package _1.vietpq.job_hunter.util.Listener;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.domain.Resume;
import _1.vietpq.job_hunter.util.SecurityUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class ResumeListener {
    @PrePersist
    private void beforeAnyCreate(Resume resume){
        resume.setCreatedAt(Instant.now());
        resume.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent()? SecurityUtil.getCurrentUserLogin().get() : "" );
    }
    @PreUpdate
    private void beforeAnyUpdate(Resume resume){
        resume.setUpdatedAt(Instant.now());
        resume.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
 }
