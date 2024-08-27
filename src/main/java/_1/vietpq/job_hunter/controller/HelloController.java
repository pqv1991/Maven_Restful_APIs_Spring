package _1.vietpq.job_hunter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import _1.vietpq.job_hunter.exception.InValidException;
@RestController
public class HelloController {
     @GetMapping("/")
    public String getHelloWorld() throws InValidException {
        if (true)
            throw new InValidException("check mate hoidanit");
        return "Hello World (Hỏi Dân IT & Eric)";

    }
}
