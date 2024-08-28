package _1.vietpq.job_hunter.repository;

import _1.vietpq.job_hunter.domain.Skill;
import _1.vietpq.job_hunter.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>, JpaSpecificationExecutor<Subscriber>  {

}
