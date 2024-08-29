package _1.vietpq.job_hunter.controller;

import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.dto.convertToDTO.ConvertToJobDTO;
import _1.vietpq.job_hunter.dto.job.ResCreateJobDTO;
import _1.vietpq.job_hunter.dto.job.ResUpdateJobDTO;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.service.job.JobService;
import _1.vietpq.job_hunter.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @PostMapping("/jobs")
    @ApiMessage("FETCH CREATE JOB")
    public ResponseEntity<ResCreateJobDTO> createJob(@RequestBody Job job){
        return ResponseEntity.status(HttpStatus.CREATED).body(ConvertToJobDTO.convertCreateJobDTO(this.jobService.handleCreateJob(job)));
    }

    @PutMapping("/jobs")
    @ApiMessage("FETCH UPDATE JOB")
    public ResponseEntity<ResUpdateJobDTO> updateJob(@RequestBody Job job){
        return ResponseEntity.ok().body(ConvertToJobDTO.convertToUpdateJobDto(this.jobService.handleUpdateJob(job)));
    }

    @GetMapping("/jobs")
    @ApiMessage("FETCH GET ALL JOBS")
    public ResponseEntity<ResultPaginationDTO> getAllJobs(@Filter Specification<Job> specification, Pageable pageable){
        return ResponseEntity.ok().body(this.jobService.fetchAllJobs(specification,pageable));
    }

    @GetMapping("/jobs/{id}")
    @ApiMessage("FETCH GET JOB BY ID")
    public ResponseEntity<Job> getJobById(@PathVariable("id") long id){
        return ResponseEntity.ok().body(this.jobService.fetchJobById(id));
    }

    @DeleteMapping("/jobs/{id}")
    @ApiMessage("DELETE A JOB")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        this.jobService.handleDeleteJob(id);
        return ResponseEntity.ok().body(null);

    }
}
