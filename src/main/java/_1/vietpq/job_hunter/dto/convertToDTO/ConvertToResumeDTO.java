package _1.vietpq.job_hunter.dto.convertToDTO;

import _1.vietpq.job_hunter.domain.Resume;
import _1.vietpq.job_hunter.dto.resume.ResCreateResumeDTO;
import _1.vietpq.job_hunter.dto.resume.ResResumeDTO;
import _1.vietpq.job_hunter.dto.resume.ResUpdateResumeDTO;

public class ConvertToResumeDTO {
    public static ResCreateResumeDTO convertToCreateResumeDTO(Resume resume){
        return new ResCreateResumeDTO(resume.getId(),resume.getCreatedAt(),resume.getCreatedBy());
    }

    public static ResUpdateResumeDTO convertToUpdateResumeDTO(Resume resume){
        return new ResUpdateResumeDTO(resume.getId(), resume.getUpdatedAt(),resume.getUpdatedBy());
    }
    public static ResResumeDTO convertToResumeDTO(Resume resume){
        ResResumeDTO res = new ResResumeDTO();
        ResResumeDTO.JobResume jobResume = new ResResumeDTO.JobResume();
        ResResumeDTO.UserResume userResume = new ResResumeDTO.UserResume();
        res.setId(resume.getId());
        res.setEmail(resume.getEmail());
        res.setUrl(resume.getUrl());
        res.setStatus(resume.getStatus());
        res.setCreatedAt(resume.getCreatedAt());
        res.setCreatedBy(resume.getCreatedBy());
        res.setUpdatedAt(resume.getUpdatedAt());
        res.setUpdatedBy(resume.getUpdatedBy());
        if(resume.getJob() != null){
            res.setCompanyName(resume.getJob().getCompany().getName());
        }
        if(resume.getJob() !=null) {
            jobResume.setId(resume.getJob().getId());
            jobResume.setName(resume.getJob().getName());
        }
        res.setJob(jobResume);
        if(resume.getUser() !=null) {
            userResume.setId(resume.getUser().getId());
            userResume.setName(resume.getUser().getName());
        }
        res.setUser(userResume);
        return res;
    }

}
