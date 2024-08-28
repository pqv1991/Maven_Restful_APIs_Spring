package _1.vietpq.job_hunter.domain;

import _1.vietpq.job_hunter.util.Listener.ResumeListener;
import _1.vietpq.job_hunter.util.contant.ResumeStateEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Entity
@Table(name = "resumes")
@Getter
@Setter
@EntityListeners(ResumeListener.class)
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String url;
    private ResumeStateEnum status;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
