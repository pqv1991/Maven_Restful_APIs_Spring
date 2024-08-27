package _1.vietpq.job_hunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import _1.vietpq.job_hunter.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>  {
    User  findByEmail(String email);
    Boolean existsByEmail(String email);
    User findByRefreshTokenAndEmail(String refreshToken,String Email);
}
