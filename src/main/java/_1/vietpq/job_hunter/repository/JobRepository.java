package _1.vietpq.job_hunter.repository;

import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job>  {

}
