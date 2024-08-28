package _1.vietpq.job_hunter.util.validator;

import _1.vietpq.job_hunter.exception.NotNullException;
import _1.vietpq.job_hunter.exception.message.CompanyMessage;
import _1.vietpq.job_hunter.exception.message.SkillMessage;

public class SkillValidator {
    public static  void notNullName(String name) throws NotNullException {
        if(name == null || name.trim().isEmpty())
            throw  new NotNullException(SkillMessage.SKILL_NAME_REQUIRED);
    }
}
