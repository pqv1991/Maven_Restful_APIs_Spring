package _1.vietpq.job_hunter.dto.login;

import _1.vietpq.job_hunter.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResLoginDTO {
    private String access_token;
    private LoginUser user;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginUser {
        private long id;
        private String email;
        private String name;
        private Role role;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserGetAccount {
        private LoginUser user;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInsideToken {
        private long id;
        private String email;
        private String name;

    }
}
