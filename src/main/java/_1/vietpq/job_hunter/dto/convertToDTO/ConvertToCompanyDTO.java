package _1.vietpq.job_hunter.dto.convertToDTO;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.dto.company.ResCompanyDTO;
import _1.vietpq.job_hunter.dto.company.ResCreateCompanyDTO;
import _1.vietpq.job_hunter.dto.company.ResUpdateCompanyDTO;

public class ConvertToCompanyDTO {

    public  static ResCreateCompanyDTO convertResCreateCompanyDTO(Company company){
        ResCreateCompanyDTO res = new ResCreateCompanyDTO();
       res.setId(company.getId());
       res.setName(company.getName());
       res.setAddress(company.getAddress());
       res.setDescription(company.getDescription());
       res.setLogo(company.getLogo());
       res.setCreatedAt(company.getCreatedAt());

       return  res;
    }

    public  static ResUpdateCompanyDTO convertResUpdateCompanyDTO(Company company){
        ResUpdateCompanyDTO res = new ResUpdateCompanyDTO();
       res.setId(company.getId());
       res.setName(company.getName());
       res.setAddress(company.getAddress());
       res.setDescription(company.getDescription());
       res.setLogo(company.getLogo());
       res.setUpdatedAt(company.getUpdatedAt());

       return  res;
    }

    public  static ResCompanyDTO convertResCompanyDTO(Company company){
        ResCompanyDTO res = new ResCompanyDTO();
       res.setId(company.getId());
       res.setName(company.getName());
       res.setAddress(company.getAddress());
       res.setDescription(company.getDescription());
       res.setLogo(company.getLogo());
       res.setCreatedAt(company.getCreatedAt());
       res.setUpdatedAt(company.getUpdatedAt());

       return  res;
    }
   
    
}
