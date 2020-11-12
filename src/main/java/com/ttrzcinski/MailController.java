package com.ttrzcinski;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import javax.validation.Valid;

@Controller("/mail")
public class MailController {
    
    private final IEmailService emailService;

    public MailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @Post("/send")
    public HttpResponse send(@Body @Valid Message msg) {
        emailService.send(msg);
        return HttpResponse.ok();
    }
}
