package _1.vietpq.job_hunter.controller;

import _1.vietpq.job_hunter.domain.Resume;
import _1.vietpq.job_hunter.dto.convertToDTO.ConvertToResumeDTO;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.dto.resume.ResCreateResumeDTO;
import _1.vietpq.job_hunter.dto.resume.ResResumeDTO;
import _1.vietpq.job_hunter.dto.resume.ResUpdateResumeDTO;
import _1.vietpq.job_hunter.exception.InValidException;
import _1.vietpq.job_hunter.service.resume.ResumeService;
import _1.vietpq.job_hunter.service.user.UserService;
import _1.vietpq.job_hunter.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;

    public ResumeController(ResumeService resumeService, UserService userService) {
        this.resumeService = resumeService;
        this.userService = userService;
    }

    @PostMapping("/resumes")
    @ApiMessage("FETCH CREATE RESUME")
    public ResponseEntity<ResCreateResumeDTO> createResume( @RequestBody Resume resume) throws InValidException {

        return ResponseEntity.status(HttpStatus.CREATED).body(ConvertToResumeDTO.convertToCreateResumeDTO(resumeService.handleCreateResume(resume)));
    }
    @PutMapping("/resumes")
    @ApiMessage("FETCH UPDATE RESUME")
    public ResponseEntity<ResUpdateResumeDTO> updateResume(@RequestBody Resume resume)  {
        return ResponseEntity.ok().body(ConvertToResumeDTO.convertToUpdateResumeDTO(resumeService.handleUpdateResume(resume)));
    }

    @DeleteMapping("/resumes/{id}")
    @ApiMessage("FETCH DELETE RESUME")
    public ResponseEntity<Void> deleteResume(@PathVariable("id") long id)  {
        resumeService.handleDeleteResume(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/resumes/{id}")
    @ApiMessage("FETCH GET RESUME BY ID")
    public ResponseEntity<ResResumeDTO> getResumeById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(ConvertToResumeDTO.convertToResumeDTO(this.resumeService.fetchResumeById(id).get()));
    }

    @GetMapping("/resumes")
    @ApiMessage("FETCH ALL RESUME")
    public ResponseEntity<ResultPaginationDTO> fetchAllResume(@Filter Specification<Resume> spec,
                                                              Pageable pageable) {

        return ResponseEntity.ok().body(this.resumeService.fetchAllResumes(spec, pageable));
    }

    @PostMapping("/resumes/by-user")
    @ApiMessage("FETCH RESUME BY USER")
    public ResponseEntity<ResultPaginationDTO> fetchResumeByUser(Pageable pageable) {

        return ResponseEntity.ok().body(this.resumeService.fetchAllResumesByUser(pageable));
    }

}
