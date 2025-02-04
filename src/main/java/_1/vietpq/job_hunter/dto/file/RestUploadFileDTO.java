package _1.vietpq.job_hunter.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestUploadFileDTO {
    private String fileName;
    private Instant uploadedAt;
}
