package _1.vietpq.job_hunter.service.email;

public interface EmailService {
    public void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml);
    public void sendEmailFromTemplateSync(String to, String subject, String templateName,String username,Object value);

}
