package _1.vietpq.job_hunter.dto.job;

import _1.vietpq.job_hunter.domain.Skill;
import _1.vietpq.job_hunter.util.contant.LevelEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class ResCreateJobDTO {
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
    private Instant createdAt;
    private String createdBy;
    @Getter
    @Setter
    public static class CompanyJob{
        private long id;
        private String name;
    }
}
