package _1.vietpq.job_hunter.service.resume;

import _1.vietpq.job_hunter.domain.Resume;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.exception.InValidException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface ResumeService {
    boolean checkResumeExistsByUserAndJob(Resume resume);
    Optional<Resume> fetchResumeById(long id);
    Resume handleCreateResume(Resume resume) throws InValidException;
    Resume handleUpdateResume(Resume resume);
    ResultPaginationDTO fetchAllResumes(Specification<Resume> spec, Pageable pageable);
    ResultPaginationDTO fetchAllResumesByUser(Pageable pageable);
    void handleDeleteResume(long id);
}
