package _1.vietpq.job_hunter.controller;

import _1.vietpq.job_hunter.exception.message.AuthMessage;
import _1.vietpq.job_hunter.util.validator.AuthValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.dto.login.LoginDTO;
import _1.vietpq.job_hunter.dto.login.ResLoginDTO;
import _1.vietpq.job_hunter.exception.InValidException;
import _1.vietpq.job_hunter.service.user.UserService;
import _1.vietpq.job_hunter.util.SecurityUtil;


@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;
    private final  AuthenticationManagerBuilder authenticationManagerBuilder;
    private final  SecurityUtil securityUtil;
    private final  UserService userService;



    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
            UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping("auth/login")
    public ResponseEntity<ResLoginDTO>  login( @RequestBody LoginDTO loginDTO ) throws InValidException {
        AuthValidator.validatorLoginDTO(loginDTO);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
       
        User userData = userService.handleGetUserByUsername(loginDTO.getUsername());
        ResLoginDTO resLoginDTO = new ResLoginDTO();
        if(userData !=null){
            ResLoginDTO.LoginUser loginUser =  new ResLoginDTO.LoginUser(userData.getId(),userData.getEmail(), userData.getName());
            resLoginDTO.setUser(loginUser);
        }
        String access_token = securityUtil.createAccessToken(authentication.getName(),resLoginDTO);
        resLoginDTO.setAccess_token(access_token);
        //update refresh_token
        String refresh_token = securityUtil.createRefreshToken(loginDTO.getUsername(),resLoginDTO);

        userService.updateUserToken(refresh_token, loginDTO.getUsername());

        //set cookies
        ResponseCookie resCookie = ResponseCookie.from("refresh_token",refresh_token)
                                                .httpOnly(true).secure(true).path("/").maxAge(refreshTokenExpiration).build();

        
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookie.toString()).body(resLoginDTO);
    }
    @GetMapping("/auth/account")
    public ResponseEntity<ResLoginDTO.UserGetAccount> getAccount(){
        String email = SecurityUtil.getCurrentUserLogin().isPresent()?SecurityUtil.getCurrentUserLogin().get():"";
        User currentUserDb = userService.handleGetUserByUsername(email);
        ResLoginDTO.LoginUser loginUser = new ResLoginDTO.LoginUser();
        ResLoginDTO.UserGetAccount userGetAccount = new ResLoginDTO.UserGetAccount();
        if(currentUserDb != null){
            loginUser.setId(currentUserDb.getId());
            loginUser.setEmail(currentUserDb.getEmail());
            loginUser.setName(currentUserDb.getName());
            userGetAccount.setUser(loginUser);
        }

        return ResponseEntity.ok().body(userGetAccount);
    }
    @GetMapping("/auth/refresh")
    public ResponseEntity<ResLoginDTO> geRefreshToken(@CookieValue(name="refresh_token") String refresh_token) throws InValidException {
        Jwt decodeToken = securityUtil.checkValidRefreshToken(refresh_token);
        String email = decodeToken.getSubject();

        User currentUser = userService.fetchUserByRefreshTokenAndEmail(refresh_token, email);

        if(currentUser == null){
            throw new InValidException(AuthMessage.REFRESH_TOKEN_INVALID);
        }

        ResLoginDTO resLoginDTO = new ResLoginDTO();
        User userData = userService.handleGetUserByUsername(email);
        if(userData !=null){
            ResLoginDTO.LoginUser loginUser = new ResLoginDTO.LoginUser(userData.getId(),userData.getEmail(),userData.getName());
            resLoginDTO.setUser(loginUser);
        }

        String access_token = securityUtil.createAccessToken(email, resLoginDTO);
        resLoginDTO.setAccess_token(access_token);
        String new_refresh_token = securityUtil.createRefreshToken(email, resLoginDTO);
        userService.updateUserToken(new_refresh_token, email);

        ResponseCookie resCookie = ResponseCookie.from("refresh_token",new_refresh_token)
        .httpOnly(true).secure(true).path("/").maxAge(refreshTokenExpiration).build();


        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookie.toString()).body(resLoginDTO);
    }
    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout () throws InValidException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent()?SecurityUtil.getCurrentUserLogin().get():"";
        if(email.equals("")){
            throw  new InValidException(AuthMessage.ACCESS_TOKEN_INVALID);
        }
        userService.updateUserToken(null, email);

        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token",null)
        .httpOnly(true).secure(true).path("/").maxAge(0).build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, deleteCookie.toString()).body(null);

    }
 }
