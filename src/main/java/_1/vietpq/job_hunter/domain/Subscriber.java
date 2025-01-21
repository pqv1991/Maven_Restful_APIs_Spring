package _1.vietpq.job_hunter.domain;

import _1.vietpq.job_hunter.util.Listener.SubscriberListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "subscribers")
@Getter
@Setter
@EntityListeners(SubscriberListener.class)
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "subscriber_skill",joinColumns = @JoinColumn(name = "subscriber_id"),inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @JsonIgnoreProperties(value = "subscribers")
    private List<Skill> skills;


}
