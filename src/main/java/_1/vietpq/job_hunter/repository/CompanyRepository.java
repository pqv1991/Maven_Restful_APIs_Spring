package _1.vietpq.job_hunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import _1.vietpq.job_hunter.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>,JpaSpecificationExecutor<Company> {

    boolean existsByName(String name);
    
}
