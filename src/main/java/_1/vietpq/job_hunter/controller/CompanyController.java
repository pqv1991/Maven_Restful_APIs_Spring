package _1.vietpq.job_hunter.controller;

import java.util.List;
import java.util.Optional;

import _1.vietpq.job_hunter.exception.message.CompanyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.dto.company.ResCompanyDTO;
import _1.vietpq.job_hunter.dto.company.ResCreateCompanyDTO;
import _1.vietpq.job_hunter.dto.company.ResUpdateCompanyDTO;
import _1.vietpq.job_hunter.dto.convertToDTO.ConvertToCompanyDTO;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.exception.InValidException;
import _1.vietpq.job_hunter.service.company.CompanyService;
import _1.vietpq.job_hunter.service.user.UserService;
import _1.vietpq.job_hunter.util.annotation.ApiMessage;

@RestController
@RequestMapping("api/v1")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired 
    private UserService userService;

    @PostMapping("/companies")
    @ApiMessage("FETCH CREATE COMPANY")
    public ResponseEntity<ResCreateCompanyDTO> createCompany(@RequestBody Company company) throws InValidException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ConvertToCompanyDTO.convertResCreateCompanyDTO(companyService.handleCreateCompany(company)));
    }

    @GetMapping("/companies")
    @ApiMessage("FETCH ALL COMPANIES")
    public ResponseEntity<ResultPaginationDTO> getAllCompanies(@Filter Specification<Company> spec, Pageable pageable){
        return ResponseEntity.ok().body(companyService.fetAllCompany(spec, pageable));
    }

    @PutMapping("/companies")
    @ApiMessage("FETCH UPDATE COMPANY")
    public ResponseEntity<ResUpdateCompanyDTO> updateCompany(@RequestBody Company company) throws InValidException {
        Optional<Company> comOptional = companyService.fetchCompanyById(company.getId());
        if(comOptional == null){
            throw  new InValidException(CompanyMessage.NOT_FOUND);
        }

        return ResponseEntity.ok().body(ConvertToCompanyDTO.convertResUpdateCompanyDTO(companyService.handleUpdateCompany(company)));
    }

    @GetMapping("/companies/{id}")
    @ApiMessage("Fetch company get id")
    public ResponseEntity<ResCompanyDTO> getCompanyById(@RequestParam("id") long id) throws InValidException {
        Optional<Company> company = companyService.fetchCompanyById(id);
        if(company.isEmpty()){
            throw new InValidException(CompanyMessage.NOT_FOUND);
        }
        return ResponseEntity.ok().body(ConvertToCompanyDTO.convertResCompanyDTO(company.get()));
    }

    @DeleteMapping("/companies/{id}")
    @ApiMessage("FETCH DELETE COMPANY")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") long id) throws InValidException {
        Optional<Company> comOptional = companyService.fetchCompanyById(id);
        if(comOptional == null ){
            throw  new InValidException(CompanyMessage.NOT_FOUND);
        }else{
            Company company = comOptional.get();
            List<User>  users = company.getUsers();
            userService.handleDeleteAllUsersInCompany(users);
        }
        companyService.handleDeleteCompany(id);
        return ResponseEntity.ok().body(null);
    }

}
