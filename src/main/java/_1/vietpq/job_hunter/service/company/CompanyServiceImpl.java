package _1.vietpq.job_hunter.service.company;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import _1.vietpq.job_hunter.dto.convertToDTO.ConvertToCompanyDTO;
import _1.vietpq.job_hunter.exception.DuplicatedException;
import _1.vietpq.job_hunter.exception.message.CompanyMessage;
import _1.vietpq.job_hunter.util.validator.CompanyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.dto.company.ResCompanyDTO;
import _1.vietpq.job_hunter.dto.pagination.ResultPaginationDTO;
import _1.vietpq.job_hunter.repository.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private  CompanyRepository companyRepository;

    @Override
    public Optional<Company> fetchCompanyById(long id) {
        Optional<Company> comOptional = companyRepository.findById(id);
        if(comOptional.isPresent()){
            return comOptional;
        } 
        return  null;
    }

    @Override
    public ResultPaginationDTO fetAllCompany(Specification<Company> spec, Pageable pageable) {
       Page<Company> pageCompanys = companyRepository.findAll(spec, pageable);
       ResultPaginationDTO rs = new ResultPaginationDTO();
       ResultPaginationDTO.Meta  mt = new ResultPaginationDTO.Meta();
       mt.setPage(pageable.getPageNumber()+1);
       mt.setPageSize(pageable.getPageSize());
       mt.setPages(pageCompanys.getTotalPages());
       mt.setTotal(pageCompanys.getTotalElements());
       rs.setMeta(mt);
       List<ResCompanyDTO> listComDTO = pageCompanys.getContent().stream().map(ConvertToCompanyDTO::convertResCompanyDTO).collect(Collectors.toList());
        rs.setResult(listComDTO);          
    return rs;
    }

    @Override
    public Company handleCreateCompany(Company company) {
        CompanyValidator.notNullName(company.getName());
        if(companyRepository.existsByName(company.getName())){
            throw new DuplicatedException(CompanyMessage.NAME_ALREADY_EXIST);
        }
        return companyRepository.save(company);
    }

    @Override
    public Company handleUpdateCompany(Company company) {
        Optional<Company> comOptional = companyRepository.findById(company.getId());
        if(comOptional.isPresent()){
            Company comUpdate = comOptional.get();
            comUpdate.setName(company.getName());
            comUpdate.setAddress(company.getAddress());
            comUpdate.setDescription(company.getDescription());
            comUpdate.setLogo(company.getLogo());
            return companyRepository.save(comUpdate);
        }
       return null;
    }

    @Override
    public void handleDeleteCompany(long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public boolean isNameExist(String name) {
        return companyRepository.existsByName(name);
    }

    
}
