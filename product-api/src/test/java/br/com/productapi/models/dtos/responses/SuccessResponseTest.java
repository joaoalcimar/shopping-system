package br.com.productapi.models.dtos.responses;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpStatus;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class SuccessResponseTest {

    @Test
    public void testBuilder() {
        SuccessResponse response = SuccessResponse.builder()
                .status(200)
                .message("Success!")
                .build();

        assertEquals(200, response.getStatus().intValue());
        assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testCreate() {
        String message = "Success!";
        SuccessResponse response = SuccessResponse.create(message);

        assertEquals(HttpStatus.OK.value(), response.getStatus().intValue());
        assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testSetterAndGetterForStatus() {
        SuccessResponse response = new SuccessResponse();
        response.setStatus(200);

        assertEquals(200, response.getStatus().intValue());
    }

    @Test
    public void testSetterAndGetterForMessage() {
        SuccessResponse response = new SuccessResponse();
        response.setMessage("Success!");

        assertEquals("Success!", response.getMessage());
    }

    @Test
    public void testToString() {
        SuccessResponse response = SuccessResponse.builder()
                .status(200)
                .message("Success!")
                .build();

        String expectedString = "SuccessResponse(status=200, message=Success!)";
        assertEquals(expectedString, response.toString());
    }
}

