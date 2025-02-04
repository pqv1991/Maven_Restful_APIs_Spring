package _1.vietpq.job_hunter.service.email;

import _1.vietpq.job_hunter.domain.Job;
import _1.vietpq.job_hunter.repository.JobRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.List;
@Service
public class EmailServiceImpl implements EmailService {

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final JobRepository jobRepository;

    public EmailServiceImpl(MailSender mailSender, JavaMailSender javaMailSender, SpringTemplateEngine templateEngine, JobRepository jobRepository) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.jobRepository = jobRepository;
    }


    @Override
    @Async
    public void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
// Prepare message using a Spring helper
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content, isHtml);
            this.javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            System.out.println("ERROR SEND EMAIL: " + e);
        }
    }

    @Override
    public void sendEmailFromTemplateSync(String to, String subject, String templateName, String username, Object value) {
        List<Job> jobs = this.jobRepository.findAll();
        Context context = new Context();
        context.setVariable("name",username);
        context.setVariable("jobs",value);
        String content = this.templateEngine.process(templateName, context);
        this.sendEmailSync(to, subject, content, false, true);

    }
}
