package _1.vietpq.job_hunter.dto.resume;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class ResUpdateResumeDTO {
    private long id;
    private Instant updatedAt;
    private String updatedBy;
}
