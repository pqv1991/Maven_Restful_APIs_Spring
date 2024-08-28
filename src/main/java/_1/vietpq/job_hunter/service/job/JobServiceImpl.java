package _1.vietpq.job_hunter.service.job;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.domain.Skill;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.exception.NotFoundException;
import _1.vietpq.job_hunter.exception.message.JobMessage;
import _1.vietpq.job_hunter.exception.message.SkillMessage;
import _1.vietpq.job_hunter.repository.CompanyRepository;
import _1.vietpq.job_hunter.repository.JobRepository;
import _1.vietpq.job_hunter.repository.SkillRepository;
import _1.vietpq.job_hunter.util.validator.JobValidator;
import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final CompanyRepository companyRepository;

    public JobServiceImpl(JobRepository jobRepository, SkillRepository skillRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Job handleCreateJob(Job job) {
        JobValidator.validatorJob(job);
        List<Skill> skills1 = job.getSkills();
        if(job.getSkills() != null) {
            List<Long> idSkills = job.getSkills().stream().map(Skill::getId).toList();
            List<Skill> skills = this.skillRepository.findByIdIn(idSkills);
            job.setSkills(skills);
        }

        if(job.getCompany() != null) {
            Optional<Company> companyOptional = this.companyRepository.findById(job.getCompany().getId());
            companyOptional.ifPresent(job::setCompany);
        }

        return this.jobRepository.save(job);
    }

    @Override
    public Job handleUpdateJob(Job job) {
        Optional<Job> jobOptional = this.jobRepository.findById(job.getId());
        if(jobOptional.isEmpty()){
            throw new NotFoundException(JobMessage.NOT_FOUND);
        }
        Job jobInDb = jobOptional.get();
        if(job.getSkills() != null) {
            List<Long> idSkills = job.getSkills().stream().map(Skill::getId).toList();
            List<Skill> skills = this.skillRepository.findByIdIn(idSkills);
            jobInDb.setSkills(skills);
        }
        if(job.getCompany() != null) {
            Optional<Company> companyOptional = this.companyRepository.findById(job.getCompany().getId());
            companyOptional.ifPresent(jobInDb::setCompany);
        }
        jobInDb.setName(job.getName());
        jobInDb.setLocation(job.getLocation());
        jobInDb.setSalary(job.getSalary());
        jobInDb.setQuantity(job.getQuantity());
        jobInDb.setLevel(job.getLevel());
        jobInDb.setDescription(job.getDescription());
        jobInDb.setStartDate(job.getStartDate());
        jobInDb.setEndDate(job.getEndDate());
        jobInDb.setActive(job.isActive());

        return this.jobRepository.save(jobInDb);
    }

    @Override
    public Job fetchJobById(long id) {
        Optional<Job> jobOptional = this.jobRepository.findById(id);
        if(jobOptional.isEmpty()){
            throw new NotFoundException(JobMessage.NOT_FOUND);
        }
        return jobOptional.get();
    }

    @Override
    public ResultPaginationDTO fetchAllJobs(Specification<Job> specification, Pageable pageable) {
        Page<Job> jobPage = jobRepository.findAll(specification,pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(jobPage.getTotalPages());
        mt.setTotal(jobPage.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(jobPage.getContent());

        return rs;

    }

    @Override
    public void handleDeleteJob(long id) {
        Optional<Job> jobOptional = this.jobRepository.findById(id);
        if(jobOptional.isEmpty()){
            throw new NotFoundException(JobMessage.NOT_FOUND);
        }
        this.jobRepository.deleteById(id);
    }
}
