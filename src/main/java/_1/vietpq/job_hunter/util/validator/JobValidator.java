package _1.vietpq.job_hunter.util.validator;

import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.exception.NotNullException;
import _1.vietpq.job_hunter.exception.message.JobMessage;
import _1.vietpq.job_hunter.exception.message.SkillMessage;
import org.springframework.security.core.parameters.P;

public class JobValidator {
    public static  void notNullName(String name) throws NotNullException {
        if(name == null || name.trim().isEmpty())
            throw  new NotNullException(JobMessage.JOB_NAME_REQUIRED);
    }

    public static  void notNullSalary(Double salary) throws NotNullException {
        if(salary == null)
            throw  new NotNullException(JobMessage.SALARY_REQUIRED);
    }

    public static void validatorJob(Job job){
        notNullName(job.getName());
        notNullSalary(job.getSalary());
    }
}
