package tooltime.service.rental;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tooltime.TestData;
import tooltime.model.Rental;
import tooltime.util.Tools;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tooltime.TestData.AMERICA_NEW_YORK;
import static tooltime.TestData.Ladder;

class SimpleRentalAgreementMachineTest {
    SimpleRentalAgreementMachine machine;
    @BeforeEach
    void setUp() {
        machine = new SimpleRentalAgreementMachine();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void operate() {

        Rental rental = Rental.builder()
                .tool(Ladder)
                .checkOutDate(Tools.dayMonthYearFormat("07/01/26", AMERICA_NEW_YORK))
                .rentalDayCount(10)
                .percentDiscount(0)
                .build();

        machine.operate(rental, TestData.LadderCharges);


    }

    @Test
    void getChargeableDays() {
        Rental rental = Rental.builder()
                .tool(Ladder)
                .checkOutDate(Tools.dayMonthYearFormat("07/01/26", AMERICA_NEW_YORK))
                .rentalDayCount(10)
                .percentDiscount(0)
                .build();

        RentalAgreementCalculationResult result = machine.operate(rental, TestData.LadderCharges);
        assertEquals(9, result.getChargeableDays());
    }

    @Test
    void getPreDiscountCharge() {
        Rental rental = Rental.builder()
                .tool(Ladder)
                .checkOutDate(Tools.dayMonthYearFormat("07/01/26", AMERICA_NEW_YORK))
                .rentalDayCount(10)
                .percentDiscount(0)
                .build();

        RentalAgreementCalculationResult result = machine.operate(rental, TestData.LadderCharges);
        assertEquals(new BigDecimal("17.91"), result.getPreDiscountCharge());
    }

    @Test
    void getWeekdays() {
    }

    @Test
    void getWeekends() {
    }

    @Test
    void getHolidays() {
    }
}