package _1.vietpq.job_hunter.dto.convertToDTO;

import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.dto.user.ResCreateUserDTO;
import _1.vietpq.job_hunter.dto.user.ResUpdateUserDTO;
import _1.vietpq.job_hunter.dto.user.ResUserDTO;

public class ConvertToUserDTO {
    public  static ResCreateUserDTO convertResCreateUserDTO(User user){
        ResCreateUserDTO res = new ResCreateUserDTO();
        ResCreateUserDTO.CompanyUser  companyUser= new ResCreateUserDTO.CompanyUser();
       res.setId(user.getId());
       res.setEmail(user.getEmail());
       res.setName(user.getName());
       res.setPhoneNumber(user.getPhoneNumber());
       res.setAddress(user.getAddress());
       res.setAge(user.getAge());
       res.setGender( user.getGender());
       res.setCreatedAt(user.getCreatedAt());
       if(user.getCompany() !=null ){
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            res.setCompany(companyUser);
       }
       return  res;
    }
    
    public  static ResUpdateUserDTO convertResUpdateUserDTO(User user){
        ResUpdateUserDTO res = new ResUpdateUserDTO();
        ResUpdateUserDTO.CompanyUser companyUser = new ResUpdateUserDTO.CompanyUser();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
         res.setName(user.getName());
        res.setPhoneNumber(user.getPhoneNumber());
         res.setAddress(user.getAddress());
        res.setAge(user.getAge());
        res.setGender( user.getGender());
       res.setUpdatedAt(user.getUpdatedAt());
        if(user.getCompany() !=null ){
        companyUser.setId(user.getCompany().getId());
        companyUser.setName(user.getCompany().getName());
        res.setCompany(companyUser);
   }
       return  res;
    }

    public  static ResUserDTO convertResUserDTO(User user){
        ResUserDTO res = new ResUserDTO();
        ResUserDTO.CompanyUser companyUser = new  ResUserDTO.CompanyUser();
       res.setId(user.getId());
       res.setEmail(user.getEmail());
       res.setName(user.getName());
       res.setPhoneNumber(user.getPhoneNumber());
       res.setAddress(user.getAddress());
       res.setAge(user.getAge());
       res.setGender( user.getGender());
       res.setCreatedAt(user.getCreatedAt());
       res.setUpdatedAt(user.getUpdatedAt());
       if(user.getCompany() !=null ){
        companyUser.setId(user.getCompany().getId());
        companyUser.setName(user.getCompany().getName());
        res.setCompany(companyUser);
   }
       return  res;
    }
}
