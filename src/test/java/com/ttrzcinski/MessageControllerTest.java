package com.ttrzcinski;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;
import static io.micronaut.http.HttpMethod.POST;
import static io.micronaut.http.HttpMethod.GET;
import static io.micronaut.http.HttpStatus.OK;
import static io.micronaut.http.HttpStatus.NOT_FOUND;
import static io.micronaut.http.MediaType.APPLICATION_JSON;

public class MessageControllerTest {

    private static MicronautLambdaHandler handler;
    private static Context lambdaContext = new MockLambdaContext();
    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setupSpec() {
        try {
            handler = new MicronautLambdaHandler();
            objectMapper = handler.getApplicationContext().getBean(ObjectMapper.class);

        } catch (ContainerInitializationException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void cleanupSpec() {
        handler.getApplicationContext().close();
    }

    @Test
    void testPostMessage() throws JsonProcessingException {
        // Arrange
        String testEmailAddress = "smb@example.com";
        String testEmailTitle = "Test those damm microservices.";
        String testEmailContent = "Test simple text 1.";
        long testMagicNumber = 3L;
        Message message = new Message();
        message.setEmail(testEmailAddress);
        message.setTitle(testEmailTitle);
        message.setContent(testEmailContent);
        message.setMagicNumber(testMagicNumber);
        String json = objectMapper.writeValueAsString(message);
        AwsProxyRequest request = new AwsProxyRequestBuilder("/api/message", POST.toString())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(json)
                .build();
        // Act
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
        // Assert
        Assertions.assertEquals(OK.getCode(), response.getStatusCode());
        MessageSaved messageSaved = objectMapper.readValue(response.getBody(), MessageSaved.class);
        Assertions.assertEquals(messageSaved.getEmail().toLowerCase(), message.getEmail().toLowerCase());
        Assertions.assertEquals(messageSaved.getTitle(), message.getTitle());
        Assertions.assertEquals(messageSaved.getContent(), message.getContent());
        Assertions.assertEquals(messageSaved.getMagicNumber(), message.getMagicNumber());
        Assertions.assertNotNull(messageSaved.getID());
        Assertions.assertNotNull(messageSaved.getCreatedDate());
    }

    @Test
    void testGetMessages() throws JsonProcessingException {
        // Arrange
        String testEmailAddress = "smb@example.com";
        String testEmailTitle = "Test those damm microservices.";
        String testEmailContent = "Test simple text 1.";
        long testMagicNumber = 3L;
        Message message = new Message();
        message.setEmail(testEmailAddress);
        message.setTitle(testEmailTitle);
        message.setContent(testEmailContent);
        message.setMagicNumber(testMagicNumber);
        String json = objectMapper.writeValueAsString(message);
        AwsProxyRequest request = new AwsProxyRequestBuilder("/api/message", POST.toString())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(json)
                .build();
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
        Assertions.assertEquals(OK.getCode(), response.getStatusCode());
        MessageQuery messageQuery = new MessageQuery();
        messageQuery.setEmail(testEmailAddress);
        // Act
        String readJson = objectMapper.writeValueAsString(messageQuery);
        AwsProxyRequest readRequest = new AwsProxyRequestBuilder("/api/messages/"+testEmailAddress, GET.toString())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(readJson)
                .build();
        // Act
        AwsProxyResponse readResponse = handler.handleRequest(readRequest, lambdaContext);
        // Assert
        Assertions.assertEquals(OK.getCode(), readResponse.getStatusCode());
        Assertions.assertNotNull(readResponse.getBody());
    }

    @Test
    void testSendMessages() throws JsonProcessingException {
        // Arrange
        String testEmailAddress = "smb@example.com";
        String testEmailTitle = "Test those damm microservices.";
        String testEmailContent = "Test simple text 1.";
        long testMagicNumber = 3L;
        Message message = new Message();
        message.setEmail(testEmailAddress);
        message.setTitle(testEmailTitle);
        message.setContent(testEmailContent);
        message.setMagicNumber(testMagicNumber);
        String json = objectMapper.writeValueAsString(message);
        AwsProxyRequest request = new AwsProxyRequestBuilder("/api/send", POST.toString())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(json)
                .build();
        // Act
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
        // Assert
        Assertions.assertEquals(OK.getCode(), response.getStatusCode());
    }
}
