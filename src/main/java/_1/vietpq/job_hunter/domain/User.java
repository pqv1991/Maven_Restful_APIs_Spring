package _1.vietpq.job_hunter.domain;

import java.time.Instant;
import java.util.List;

import _1.vietpq.job_hunter.util.Listener.UserListener;
import _1.vietpq.job_hunter.util.contant.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@EntityListeners(UserListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String name;
    private String password;
    private String address;
    private int age;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Resume> resumes;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
