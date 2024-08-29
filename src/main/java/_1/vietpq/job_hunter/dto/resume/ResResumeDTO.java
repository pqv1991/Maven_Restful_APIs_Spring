package _1.vietpq.job_hunter.dto.resume;

import _1.vietpq.job_hunter.util.contant.ResumeStateEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResResumeDTO {
    private long id;
    private String email;
    private String url;
    @Enumerated(EnumType.STRING)
    private ResumeStateEnum status;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    private String companyName;
    private JobResume job;
    private UserResume user;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public  static class JobResume{
        private long id;
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public  static class UserResume{
        private long id;
        private String name;
    }

}
