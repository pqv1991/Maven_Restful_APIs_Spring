package _1.vietpq.job_hunter.service.company;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;

public interface CompanyService {

    Optional<Company> fetchCompanyById(long id);
    
    ResultPaginationDTO fetAllCompany(Specification<Company> spec, Pageable pageable);

    Company handleCreateCompany(Company company);

    Company handleUpdateCompany(Company company);

    void handleDeleteCompany(long id);

    boolean isNameExist(String name);
    
}
