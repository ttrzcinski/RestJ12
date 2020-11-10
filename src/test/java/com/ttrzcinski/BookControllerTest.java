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
import static io.micronaut.http.HttpStatus.OK;
import static io.micronaut.http.MediaType.APPLICATION_JSON;

public class BookControllerTest {

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
    void testSaveBook() throws JsonProcessingException {
        // Arrange
        String testObjectName = "How to properly test those damm microservices.";
        Book book = new Book();
        book.setName(testObjectName);
        String json = objectMapper.writeValueAsString(book);
        AwsProxyRequest request = new AwsProxyRequestBuilder("/api/book", POST.toString())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(json)
                .build();
        // Act
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
        // Assert
        Assertions.assertEquals(OK.getCode(), response.getStatusCode());
        BookSaved bookSaved = objectMapper.readValue(response.getBody(), BookSaved.class);
        Assertions.assertEquals(bookSaved.getName(), book.getName());
        Assertions.assertNotNull(bookSaved.getIsbn());
    }
}
