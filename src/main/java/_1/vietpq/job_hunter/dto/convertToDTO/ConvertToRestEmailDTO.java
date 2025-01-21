package _1.vietpq.job_hunter.dto.convertToDTO;

import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.domain.Skill;
import _1.vietpq.job_hunter.dto.email.RestEmailJobDTO;

import java.util.List;

public class ConvertToRestEmailDTO {
    public static RestEmailJobDTO convertResEmailJobDTO(Job job){
        RestEmailJobDTO res = new RestEmailJobDTO();

        res.setName(job.getName());
        res.setSalary(job.getSalary());
        res.setCompany(new RestEmailJobDTO.CompanyEmail(job.getCompany().getName()));
        List<Skill> skills = job.getSkills();
        List<RestEmailJobDTO.SkillEmail> s = skills.stream().map(skill -> new RestEmailJobDTO.SkillEmail(skill.getName())).toList();
        res.setSkills(s);
        return res;


    }

}
