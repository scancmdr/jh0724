package tooltime.service.rental;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tooltime.TestData;
import tooltime.controller.ToolRentalController;
import tooltime.model.RentalAgreement;
import tooltime.util.Tools;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleRentalServiceTest {

    SimpleRentalService service;

    @BeforeEach
    void setUp() {
        service = new SimpleRentalService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void rent() {
        for (var testData : TestData.testCases) {
            ToolRentalController toolRentalController = new ToolRentalController();
            RentalAgreement actual = service.rent(testData.getA());
            RentalAgreement expected = testData.getB();
            assertEquals(testData.getB(),actual);
        }
    }

    @Test
    void calculateDiscount() {
        BigDecimal discount = service.calculateDiscount(new BigDecimal("1337"),7);
        assertTrue(Tools.equals(discount,new BigDecimal("93.59")));
    }
}