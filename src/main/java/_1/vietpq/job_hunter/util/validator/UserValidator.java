package _1.vietpq.job_hunter.util.validator;

import _1.vietpq.job_hunter.domain.User;
import _1.vietpq.job_hunter.exception.InValidException;
import _1.vietpq.job_hunter.exception.NotNullException;
import _1.vietpq.job_hunter.exception.message.UserMessage;

public class UserValidator {
    private static int MIN_LENGTH_PASSWORD = 8;

    public static  void notNullEmail(String email) throws NotNullException{
        if(email == null || email.trim().isEmpty())
            throw  new NotNullException(UserMessage.EMAIL_REQUIRED);
    }

    public static  void notNullPassword(String password) throws NotNullException{
        if(password == null || password.trim().isEmpty())
            throw  new NotNullException(UserMessage.PASSWORD_REQUIRED);
    }

    public static void inValidEmail(String email) throws InValidException {
        if(!ValidUser.isValidEmail(email))
            throw new InValidException(UserMessage.EMAIL_INVALID);
    }
    public static void inValidPassword(String password) throws InValidException {
        if(password.length()<MIN_LENGTH_PASSWORD)
            throw new InValidException(UserMessage.PASSWORD_INVALID);
    }




    public static void validatorUser(User user) throws InValidException {
        notNullEmail(user.getEmail());
        inValidEmail(user.getEmail());
        notNullPassword(user.getPassword());
        inValidPassword(user.getPassword());


    }
}
