package com.ttrzcinski;

import com.amazonaws.services.simpleemail.model.SendEmailResult;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Singleton
@Requires(beans = AwsCredentialsProviderService.class)
@Requires(beans = AwsCredentialsProviderService.class)
@Primary
public class AwsSesMailService implements IEmailService {
    
    protected final String awsRegion;

    protected final String sourceEmail;

    protected final AwsCredentialsProviderService awsCredentialsProviderService;

    public AwsSesMailService(@Value("${AWS_REGION:none}") String awsRegionEnv,
                  @Value("${AWS_SOURCE_EMAIL:none}") String sourceEmailEnv,
                      @Value("${aws.region:none}") String awsRegionProp,
                      @Value("${aws.sourceemail:none}") String sourceEmailProp,
                      AwsCredentialsProviderService awsCredentialsProviderService
                      ) {
        this.awsRegion = awsRegionEnv != null && !awsRegionEnv.equals("none") ? awsRegionEnv : awsRegionProp;
        this.sourceEmail = sourceEmailEnv != null && !sourceEmailEnv.equals("none") ? sourceEmailEnv : sourceEmailProp;
        this.awsCredentialsProviderService = awsCredentialsProviderService;
    }

    /*private Body content(IMessage msg) {

        if (msg.getContent() != null && !msg.getContent().isEmpty()) {
            Content c = new Content().withData(msg.getContent());
            Body body = new com.amazonaws.services.simpleemail.model.Body();
            body.setContent(c);
            return body;
        }
        return new Body();
    }*/

    @Override
    public void send(IMessage msg) {

        if ( awsCredentialsProviderService == null ) {
            System.err.println("AWS Credentials provider not configured");
            return;
        }

        Destination destination = new Destination().withToAddresses(msg.getEmail());
        Content subject = new Content().withData(msg.getContent());
        Body body = new Body(); //.withContent(msg.getContent()); //content(msg);
        Message message = new Message().withSubject(subject).withBody(body);

        SendEmailRequest request = new SendEmailRequest()
                .withSource("noreply@example.com")
                .withDestination(destination)
                .withMessage(message);

        try {
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                    .withCredentials(awsCredentialsProviderService)
                    .withRegion(awsRegion)
                    .build();

            SendEmailResult sendEmailResult = client.sendEmail(request);

            System.out.printf("Email sent! %s", sendEmailResult.toString());
        } catch (Exception ex) {
            System.err.println("The email was not sent.");
            System.err.printf("Error message: %s", ex.getMessage());
        }
    }
}
