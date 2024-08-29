package _1.vietpq.job_hunter.domain;

import _1.vietpq.job_hunter.util.Listener.RoleListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@EntityListeners(RoleListener.class)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "permission_role",joinColumns = @JoinColumn(name = "job_id"),inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @JsonIgnoreProperties(value = {"roles"})
    private List<Permission> permissions;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;
}
