package br.com.productapi.models.dtos.responses;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class JwtResponseTest {

    @Test
    public void testConstructorWithFields() {
        JwtResponse response = new JwtResponse(1, "John Doe", "john@example.com");

        assertEquals(1, response.getId().intValue());
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
    }

    @Test
    public void testGetAndSetId() {
        JwtResponse response = new JwtResponse();
        response.setId(1);

        assertEquals(1, response.getId().intValue());
    }

    @Test
    public void testGetAndSetName() {
        JwtResponse response = new JwtResponse();
        response.setName("John Doe");

        assertEquals("John Doe", response.getName());
    }

    @Test
    public void testGetAndSetEmail() {
        JwtResponse response = new JwtResponse();
        response.setEmail("john@example.com");

        assertEquals("john@example.com", response.getEmail());
    }

    @Test
    public void testToString() {
        JwtResponse response = new JwtResponse(1, "John Doe", "john@example.com");
        String expectedString = "JwtResponse(id=1, name=John Doe, email=john@example.com)";

        assertEquals(expectedString, response.toString());
    }
}

