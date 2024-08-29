package _1.vietpq.job_hunter.dto.resume;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
public class ResCreateResumeDTO {
    private long id;
    private Instant createdAt;
    private String createdBy;
}
