package _1.vietpq.job_hunter.dto.user;

import java.time.Instant;

import _1.vietpq.job_hunter.util.contant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUserDTO {
    private long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String address;
    private int age;
    private GenderEnum gender;
    private Instant createdAt;
    private Instant updatedAt;
    private CompanyUser company;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompanyUser{
        private long id;
        private String name;
    }
    
    
   
    
}
