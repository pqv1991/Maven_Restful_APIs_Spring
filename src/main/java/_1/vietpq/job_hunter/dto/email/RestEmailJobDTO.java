package _1.vietpq.job_hunter.dto.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestEmailJobDTO {
    private String name;
    private double salary;

    private CompanyEmail company;
    private List<SkillEmail> skills;


    @Getter
    @Setter
    @AllArgsConstructor
    public  static class CompanyEmail{
        private String name;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static  class SkillEmail{
        private String name;
    }

}
