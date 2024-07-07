package tooltime;

import tooltime.model.*;
import tooltime.service.Context;
import tooltime.util.Tools;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static tooltime.model.Type.Jackhammer;

public class TestData {
    public static String AMERICA_NEW_YORK = "America/New_York";
    public static final List<Tuple2<Rental, RentalAgreement>> testCases = new ArrayList<>();

    public static final Tool Chainsaw = Context.get().getDataService().toolForCode("CHNS").get();
    public static final Tool Ladder = Context.get().getDataService().toolForCode("LADW").get();
    public static final Tool DeWaltJackhammer = Context.get().getDataService().toolForCode("JAKD").get();
    public static final Tool RidgidJackHammer = Context.get().getDataService().toolForCode("JAKR").get();

    public static final Charges ChainsawCharges = Context.get().getDataService().scheduleForType(Type.Chainsaw).get();
    public static final Charges LadderCharges = Context.get().getDataService().scheduleForType(Type.Ladder).get();
    public static final Charges JackHammerCharges = Context.get().getDataService().scheduleForType(Jackhammer).get();

    static {
        // chainsaw crossing three weekend days
        Rental r1 = Rental.builder()
                .tool(Chainsaw)
                .checkOutDate(Tools.dayMonthYearFormat("07/03/24", AMERICA_NEW_YORK))
                .rentalDayCount(10)
                .percentDiscount(0)
                .build();

        testCases.add(new Tuple2<>(r1, RentalAgreement.builder()
                .rental(r1)
                .dueDate(Tools.dayMonthYearFormat("07/13/24", AMERICA_NEW_YORK))
                .dailyRentalCharge(ChainsawCharges.daily())
                .chargeDays(7)
                .preDiscountCharge(new BigDecimal("10.43"))
                .discountAmount(new BigDecimal("0"))
                .finalCharge(new BigDecimal("10.43"))
                .build()));

        // ladder with Fourth of july on saturday
        Rental r2 = Rental.builder()
                .tool(Ladder)
                .checkOutDate(Tools.dayMonthYearFormat("07/01/26", AMERICA_NEW_YORK))
                .rentalDayCount(10)
                .percentDiscount(0)
                .build();

        testCases.add(new Tuple2<>(r2, RentalAgreement.builder()
                .rental(r2)
                .dueDate(Tools.dayMonthYearFormat("07/11/26", AMERICA_NEW_YORK))
                .dailyRentalCharge(LadderCharges.daily())
                .chargeDays(9)
                .preDiscountCharge(new BigDecimal("17.91"))
                .discountAmount(new BigDecimal("0"))
                .finalCharge(new BigDecimal("17.91"))
                .build()));

        // jackhammer with Fourth of july on saturday
        Rental r3 = Rental.builder()
                .tool(DeWaltJackhammer)
                .checkOutDate(Tools.dayMonthYearFormat("07/01/26", AMERICA_NEW_YORK))
                .rentalDayCount(10)
                .percentDiscount(0)
                .build();

        testCases.add(new Tuple2<>(r3, RentalAgreement.builder()
                .rental(r3)
                .dueDate(Tools.dayMonthYearFormat("07/11/26", AMERICA_NEW_YORK))
                .dailyRentalCharge(JackHammerCharges.daily())
                .chargeDays(6)
                .preDiscountCharge(new BigDecimal("17.94"))
                .discountAmount(new BigDecimal("0"))
                .finalCharge(new BigDecimal("17.94"))
                .build()));

        // jackhammer with Fourth of july on saturday
        Rental r4 = Rental.builder()
                .tool(DeWaltJackhammer)
                .checkOutDate(Tools.dayMonthYearFormat("07/01/26", AMERICA_NEW_YORK))
                .rentalDayCount(10)
                .percentDiscount(33)
                .build();

        testCases.add(new Tuple2<>(r4, RentalAgreement.builder()
                .rental(r4)
                .dueDate(Tools.dayMonthYearFormat("07/11/26", AMERICA_NEW_YORK))
                .dailyRentalCharge(JackHammerCharges.daily())
                .chargeDays(6)
                .preDiscountCharge(new BigDecimal("17.94"))
                .discountAmount(new BigDecimal("5.92"))
                .finalCharge(new BigDecimal("12.02"))
                .build()));
    }

}
