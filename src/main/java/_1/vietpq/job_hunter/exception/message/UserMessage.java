package _1.vietpq.job_hunter.exception.message;

import _1.vietpq.job_hunter.domain.Company;
import _1.vietpq.job_hunter.util.contant.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class UserMessage {
    public final static String CURRENT_PASSWORD_REQUIRED = "Current password is required";
    public final static String PASSWORD_REQUIRED = "Password is required";
    public final static String NEW_PASSWORD_REQUIRED = "New password is required";
    public final static String CURRENT_PASSWORD_NOT_MATCH = "Current password is not match";
    public final static String EMAIL_ALREADY_EXIST = "Email is already exists";
    public final static String EMAIL_REQUIRED = "Email is required";
    public final static String EMAIL_INVALID = "Email is invalid";
    public final static String PHONE_NUMBER_INVALID = "Phone number is invalid";
    public final static String PASSWORD_INVALID = "Length of password is 8";
    public final static String USERNAME_INVALID = "Bắt đầu bằng chữ cái. " +
            "Những ký tự còn lại có thể là chữ cái, chữ số và gạch dưới. " +
            "Độ dài: nhỏ nhất là 5, lớn nhất là 25";
    public final static String NOT_FOUND = "User not found";
    public final static String CAN_NOT_BLOCK_MANAGER = "Can not block manager";

}
