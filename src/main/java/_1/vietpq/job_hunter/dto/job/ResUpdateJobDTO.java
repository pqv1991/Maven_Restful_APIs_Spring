package _1.vietpq.job_hunter.dto.job;

import _1.vietpq.job_hunter.util.contant.LevelEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class ResUpdateJobDTO {
    private long id;
    private String name;
    private String location;
    private double salary;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private LevelEnum level;
    private Instant startDate;
    private Instant endDate;
    private boolean active;
    private CompanyJob company;
    private List<String> skills;
    private Instant updatedAt;
    private String updatedBy;
    @Getter
    @Setter
    public static class CompanyJob{
        private long id;
        private String name;
    }
}
