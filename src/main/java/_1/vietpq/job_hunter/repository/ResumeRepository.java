package _1.vietpq.job_hunter.repository;

import _1.vietpq.job_hunter.domain.Resume;
import _1.vietpq.job_hunter.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume>  {

}
