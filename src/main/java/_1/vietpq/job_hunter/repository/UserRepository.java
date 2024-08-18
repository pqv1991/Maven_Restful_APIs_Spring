package _1.vietpq.job_hunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import _1.vietpq.job_hunter.domain.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
    User  findByEmail(String email);
}
