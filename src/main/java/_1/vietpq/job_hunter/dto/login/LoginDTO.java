package _1.vietpq.job_hunter.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @NotBlank(message="Username is required!")
    private String username;
    @NotBlank(message="Username is required!")
    private String password;

    
}
