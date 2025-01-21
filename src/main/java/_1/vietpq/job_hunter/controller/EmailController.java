package _1.vietpq.job_hunter.controller;

import _1.vietpq.job_hunter.service.email.EmailService;
import _1.vietpq.job_hunter.service.subscriber.SubscriberService;
import _1.vietpq.job_hunter.util.annotation.ApiMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EmailController {
    private final SubscriberService subscriberService;
    private final EmailService emailService;

    public EmailController(SubscriberService subscriberService, EmailService emailService) {
        this.subscriberService = subscriberService;
        this.emailService = emailService;
    }

    @GetMapping("/email")
    @ApiMessage("Send Email")
    //chay cong viec tu dong
//    @Scheduled(cron = "*/30 * * * * *")
//    @Transactional
    public String sendEmail(){
       this.subscriberService.sendSubscriberEmailJobs();
        //this.emailService.sendEmailSync("pqv.1991.sach@gmail.com","test","<h1><b>hello<b/></h1>",false,false);
        return "ok";
    }
}

