package _1.vietpq.job_hunter.util.validator;

import _1.vietpq.job_hunter.dto.login.LoginDTO;
import _1.vietpq.job_hunter.exception.InValidException;
import _1.vietpq.job_hunter.exception.NotNullException;
import _1.vietpq.job_hunter.exception.message.AuthMessage;
import _1.vietpq.job_hunter.exception.message.UserMessage;

public class AuthValidator {

    private static int MIN_LENGTH_PASSWORD = 8;

    public static  void notNullEmail(String email) throws NotNullException {
        if(email == null || email.trim().isEmpty())
            throw  new NotNullException(AuthMessage.EMAIL_REQUIRED);
    }

    public static  void notNullPassword(String password) throws NotNullException{
        if(password == null || password.trim().isEmpty())
            throw  new NotNullException(AuthMessage.PASSWORD_REQUIRED);
    }

    public static void inValidEmail(String email) throws InValidException {
        if(!ValidUser.isValidEmail(email))
            throw new InValidException(AuthMessage.EMAIL_INVALID);
    }
    public static void inValidPassword(String password) throws InValidException {
        if(password.length()<MIN_LENGTH_PASSWORD)
            throw new InValidException(AuthMessage.PASSWORD_INVALID);
    }

    public static void validatorLoginDTO(LoginDTO loginDTO) throws InValidException {
       AuthValidator.notNullEmail(loginDTO.getUsername());
       AuthValidator.inValidEmail(loginDTO.getUsername());
       AuthValidator.notNullPassword(loginDTO.getPassword());
       AuthValidator.inValidPassword(loginDTO.getPassword());
    }


}
