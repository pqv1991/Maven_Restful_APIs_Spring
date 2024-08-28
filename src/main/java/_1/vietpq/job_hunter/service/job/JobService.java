package _1.vietpq.job_hunter.service.job;

import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface JobService {
    Job handleCreateJob(Job job);
    Job handleUpdateJob(Job job);
    Job fetchJobById (long id);
    ResultPaginationDTO fetchAllJobs(Specification<Job> specification, Pageable pageable);
    void handleDeleteJob(long  id);
}
