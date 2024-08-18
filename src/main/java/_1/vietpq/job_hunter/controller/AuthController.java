package _1.vietpq.job_hunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import _1.vietpq.job_hunter.domain.dto.LoginDTO;
import _1.vietpq.job_hunter.domain.dto.ResLoginDTO;
import _1.vietpq.job_hunter.util.SecurityUtil;
@RestController
public class AuthController {
    private final  AuthenticationManagerBuilder authenticationManagerBuilder;
    private final  SecurityUtil securityUtil;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil= securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO>  login(@RequestBody LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String access_token = securityUtil.createToken(authentication);
        ResLoginDTO resLoginDTO = new ResLoginDTO();
        resLoginDTO.setAccess_token(access_token);
        return ResponseEntity.ok().body(resLoginDTO);
    }
    
}
