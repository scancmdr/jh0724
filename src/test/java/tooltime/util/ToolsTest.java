package tooltime.util;

import org.junit.jupiter.api.Test;
import tooltime.model.DayType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ToolsTest {

    private ZonedDateTime date(int year, int month, int day) {
        return LocalDate.of(year, month, day).atStartOfDay(ZoneId.of("America/New_York"));
    }

    private DayType dayTypeFor(int year, int month, int day) {
        return Tools.dayTypeFor(LocalDate.of(year, month, day),"America/New_York");
    }

    @Test
    public void testLaborDay() {
        assertFalse(Tools.isLaborDay(date(2024,9,1)));
        assertTrue(Tools.isLaborDay(date(2024,9,2)));
        assertFalse(Tools.isLaborDay(date(2024,9,3)));

        assertFalse(Tools.isLaborDay(date(2024,8,2)));
        assertFalse(Tools.isLaborDay(date(2024,10,2)));
        assertFalse(Tools.isLaborDay(date(2023,9,3)));
        assertFalse(Tools.isLaborDay(date(2023,9,5)));

        assertTrue(Tools.isLaborDay(date(2023,9,4)));
        assertTrue(Tools.isLaborDay(date(2025,9,1)));
    }

    @Test
    public void testFourthOfJuly() {
        assertTrue(Tools.isFourthOfJuly(date(2023,7,4)));
        assertTrue(Tools.isFourthOfJuly(date(2024,7,4)));
        assertTrue(Tools.isFourthOfJuly(date(2025,7,4)));

        assertFalse(Tools.isFourthOfJuly(date(2024,7,3)));
        assertFalse(Tools.isFourthOfJuly(date(2024,7,5)));
    }

    @Test
    public void testDayBeforeForthOfJuly() {
        assertTrue(Tools.isFourthOfJulyRelative(date(2023,7,3),-1));
        assertTrue(Tools.isFourthOfJulyRelative(date(2023,7,5),+1));
        assertTrue(Tools.isFourthOfJulyRelative(date(2023,7,4),+0));

        assertFalse(Tools.isFourthOfJulyRelative(date(2023,7,5),-1));
    }

    @Test
    public void testDayTypeFor() {
        assertEquals(DayType.Weekday,dayTypeFor(2024,7,3));
        assertEquals(DayType.Weekday,dayTypeFor(2024,7,1));
        assertEquals(DayType.Weekend,dayTypeFor(2024,7,6));
        assertEquals(DayType.Holiday,dayTypeFor(2024,9,2));
        assertEquals(DayType.Holiday,dayTypeFor(2026,7,3)); // 3-Jul-2026 is a Friday before 4th
        assertEquals(DayType.Holiday,dayTypeFor(2027,7,5)); // 5-Jul-2027 is a Monday after 4th
    }

    @Test
    public void testBigDecimalEquals() {
        BigDecimal left = new BigDecimal("0");
        BigDecimal right = new BigDecimal("0.0");
        assertNotEquals(left,right);
        assertTrue(Tools.equals(left,right));
        BigDecimal a = new BigDecimal("2");
        BigDecimal b = new BigDecimal("2.00");
        assertNotEquals(a,b);
        assertTrue(Tools.equals(a,b));
    }
}
