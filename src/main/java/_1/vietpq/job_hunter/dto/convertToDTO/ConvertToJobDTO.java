package _1.vietpq.job_hunter.dto.convertToDTO;

import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.domain.Skill;
import _1.vietpq.job_hunter.dto.job.ResCreateJobDTO;
import _1.vietpq.job_hunter.dto.job.ResUpdateJobDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertToJobDTO {

    public  static ResCreateJobDTO convertCreateJobDTO(Job job){
        ResCreateJobDTO dto = new ResCreateJobDTO();
        ResCreateJobDTO.CompanyJob companyJob = new ResCreateJobDTO.CompanyJob();
        dto.setId(job.getId());
        dto.setName(job.getName());
        dto.setSalary(job.getSalary());
        dto.setQuantity(job.getQuantity());
        dto.setLocation(job.getLocation());
        dto.setLevel(job.getLevel());
        dto.setStartDate(job.getStartDate());
        dto.setEndDate(job.getEndDate());
        dto.setActive(job.isActive());
        if(job.getCompany() != null){
            companyJob.setId(job.getCompany().getId());
            companyJob.setName(job.getCompany().getName());
            dto.setCompany(companyJob);
        }
        dto.setCreatedAt(job.getCreatedAt());
        dto.setCreatedBy(job.getCreatedBy());
        if(job.getSkills()!=null){
            List<String> skills = job.getSkills().stream().map(Skill::getName).collect(Collectors.toList());
            dto.setSkills(skills);
        }
        return  dto;
    }
    public static ResUpdateJobDTO convertToUpdateJobDto(Job job) {
        ResUpdateJobDTO res = new ResUpdateJobDTO();
        ResUpdateJobDTO.CompanyJob  companyJob = new ResUpdateJobDTO.CompanyJob();
        res.setId(job.getId());
        res.setName(job.getName());
        res.setSalary(job.getSalary());
        res.setQuantity(job.getQuantity());
        res.setLocation(job.getLocation());
        res.setLevel(job.getLevel());
        res.setStartDate(job.getStartDate());
        res.setEndDate(job.getEndDate());
        res.setActive(job.isActive());
        if(job.getCompany() != null){
            companyJob.setId(job.getCompany().getId());
            companyJob.setName(job.getCompany().getName());
            res.setCompany(companyJob);
        }
        res.setUpdatedAt(job.getUpdatedAt());
        res.setUpdatedBy(job.getUpdatedBy());
        if(job.getSkills()!=null){
            List<String> skills = job.getSkills().stream().map(item->item.getName()).collect(Collectors.toList());
            res.setSkills(skills);
        }
        return  res;
    }


}
