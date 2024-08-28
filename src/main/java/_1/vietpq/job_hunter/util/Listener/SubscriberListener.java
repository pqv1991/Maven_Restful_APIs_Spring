package _1.vietpq.job_hunter.util.Listener;

import _1.vietpq.job_hunter.domain.Subscriber;
import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.util.SecurityUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class SubscriberListener {
    @PrePersist
    private void beforeAnyCreate(Subscriber subscriber){
        subscriber.setCreatedAt(Instant.now());
        subscriber.setCreatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
    @PreUpdate
    private void beforeAnyUpdate(Subscriber subscriber){
        subscriber.setUpdatedAt(Instant.now());
        subscriber.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "");
    }
 }
