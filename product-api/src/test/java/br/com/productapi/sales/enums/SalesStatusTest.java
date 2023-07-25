package br.com.productapi.sales.enums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class SalesStatusTest {

    @Test
    public void testEnumValues() {
        SalesStatus[] values = SalesStatus.values();
        assertEquals(2, values.length);
        assertEquals(SalesStatus.APPROVED, values[0]);
        assertEquals(SalesStatus.REJECTED, values[1]);
    }

    @Test
    public void testEnumOrdinal() {
        assertEquals(0, SalesStatus.APPROVED.ordinal());
        assertEquals(1, SalesStatus.REJECTED.ordinal());
    }

    @Test
    public void testEnumValueOf() {
        assertEquals(SalesStatus.APPROVED, SalesStatus.valueOf("APPROVED"));
        assertEquals(SalesStatus.REJECTED, SalesStatus.valueOf("REJECTED"));
    }

    @Test
    public void testEnumToString() {
        assertEquals("APPROVED", SalesStatus.APPROVED.toString());
        assertEquals("REJECTED", SalesStatus.REJECTED.toString());
    }
}
