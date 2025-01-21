package _1.vietpq.job_hunter.controller;

import _1.vietpq.job_hunter.domain.Subscriber;
import _1.vietpq.job_hunter.service.subscriber.SubscriberService;
import _1.vietpq.job_hunter.util.SecurityUtil;
import _1.vietpq.job_hunter.util.annotation.ApiMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribers")
    @ApiMessage("FETCH CREATE SUBSCRIBER")
    public ResponseEntity<Subscriber> createSubscriber(@RequestBody Subscriber subscriber) {

        return ResponseEntity.status(HttpStatus.CREATED).body(subscriberService.handleCreateSubscriber(subscriber));
    }



    @GetMapping("/subscribers/skills")
    @ApiMessage("FETCH SUBSCRIBER SKILL")
    public ResponseEntity<Subscriber> getSubscribersKill()  {
        String email = SecurityUtil.getCurrentUserLogin().isPresent()? SecurityUtil.getCurrentUserLogin().get() : "";
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriberService.getSubscriberByEmail(email));
    }

}
