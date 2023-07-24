package br.com.productapi.exceptions.settings;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ExceptionDetailsTest {

    @Test
    public void testSetterAndGetterForStatus() {
        ExceptionDetails details = new ExceptionDetails();
        details.setStatus(404);

        assertEquals(404, details.getStatus());
    }

    @Test
    public void testSetterAndGetterForMessage() {
        ExceptionDetails details = new ExceptionDetails();
        details.setMessage("Not Found");

        assertEquals("Not Found", details.getMessage());
    }

    @Test
    public void testToString() {
        ExceptionDetails details = new ExceptionDetails();
        details.setStatus(500);
        details.setMessage("Internal Server Error");

        String expectedString = "ExceptionDetails(status=500, message=Internal Server Error)";
        assertEquals(expectedString, details.toString());
    }
}