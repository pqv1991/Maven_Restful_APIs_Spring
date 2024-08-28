package _1.vietpq.job_hunter.util.Listener;

import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.domain.Skill;
import _1.vietpq.job_hunter.util.SecurityUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class SkillListener {
    @PrePersist
    private void beforeAnyCreate(Skill skill){
        skill.setCreatedAt(Instant.now());
        skill.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
    @PreUpdate
    private void beforeAnyUpdate(Skill skill){
        skill.setUpdatedAt(Instant.now());
        skill.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
 }
