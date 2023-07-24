package br.com.productapi.sales.dtos;

import br.com.productapi.sales.enums.SalesStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class SalesConfirmationDTOTest {

    @Test
    public void testNoArgsConstructor() {
        SalesConfirmationDTO dto = new SalesConfirmationDTO();

        assertNull(dto.getSalesId());
        assertNull(dto.getStatus());
    }

    @Test
    public void testAllArgsConstructor() {
        String salesId = "SALE001";
        SalesStatus status = SalesStatus.APPROVED;
        SalesConfirmationDTO dto = new SalesConfirmationDTO(salesId, status);

        assertEquals(salesId, dto.getSalesId());
        assertEquals(status, dto.getStatus());
    }

    @Test
    public void testSetterAndGetter() {
        String salesId = "SALE001";
        SalesStatus status = SalesStatus.REJECTED;
        SalesConfirmationDTO dto = new SalesConfirmationDTO();

        dto.setSalesId(salesId);
        dto.setStatus(status);

        assertEquals(salesId, dto.getSalesId());
        assertEquals(status, dto.getStatus());
    }
}
