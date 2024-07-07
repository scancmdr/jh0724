package tooltime.controller;

import org.junit.jupiter.api.Test;
import tooltime.TestData;
import tooltime.model.RentalAgreement;
import tooltime.util.Tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ToolRentalControllerTest {

    @Test
    void calculatePositive() throws ValidationException, CalculationException {
        for (var testData : TestData.testCases) {
            ToolRentalController toolRentalController = new ToolRentalController();
            RentalAgreement actual = toolRentalController.calculate(testData.getA());
            RentalAgreement expected = testData.getB();

            if (actual.getChargeDays() != expected.getChargeDays()) {
                fail("Charge days mismatch, actual: " + actual + "\n, expected: " + expected);
            }
            if (!actual.getRental().equals(expected.getRental())) {
                fail("Rental mismatch, actual: " + actual + "\n, expected: " + expected);
            }
            if (!actual.getDueDate().equals(expected.getDueDate())) {
                fail("Due date mismatch, actual: " + actual + "\n, expected: " + expected);
            }
            if (!Tools.equals(actual.getDailyRentalCharge(),expected.getDailyRentalCharge())) {
                fail("Daily rental charge mismatch, actual: " + actual + "\n, expected: " + expected);
            }
            if (!Tools.equals(actual.getPreDiscountCharge(),expected.getPreDiscountCharge())) {
                fail("Pre discount charge mismatch, actual: " + actual + "\n, expected: " + expected);
            }
            if (!Tools.equals(actual.getDiscountAmount(),expected.getDiscountAmount())) {
                fail("Discount amount mismatch, actual: " + actual + "\n, expected: " + expected);
            }
            if (!Tools.equals(actual.getFinalCharge(),expected.getFinalCharge())) {
                fail("Final charge mismatch, actual: " + actual + "\n, expected: " + expected);
            }

            assertEquals(testData.getB(),actual);
        }
    }


}