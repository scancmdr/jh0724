package tooltime;

import lombok.experimental.NonFinal;
import picocli.CommandLine;
import tooltime.controller.CalculationException;
import tooltime.controller.ToolRentalController;
import tooltime.controller.ValidationException;
import tooltime.model.Rental;
import tooltime.model.Tool;
import tooltime.service.Context;
import tooltime.service.data.DataService;
import tooltime.service.rental.RentalService;
import tooltime.util.Tools;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Tool Time entry point
 *
 * @TODO: https://picocli.info/#_internationalization
 * @author jay
 */
@CommandLine.Command(name = "tooltime",
        mixinStandardHelpOptions = true,
        version = "tooltime 1.0",
        description = "ToolTime - command line tool rental utility")
public class Main implements Callable<Integer> {

    @CommandLine.Option(names = {"-t", "--code"}, paramLabel = "TOOL_CODE", description = "code for tool to rent", required = true)
    private String toolCode;

    @CommandLine.Option(names = {"-d", "--days"}, paramLabel = "DAY_COUNT", description = "number of days for the rental (1 or more)", required = true)
    private int dayCount;

    @CommandLine.Option(names = {"-s", "--discount"}, paramLabel = "PERCENT", description = "percent discount, integer (0-100)", required = true)
    private int discount;

    @CommandLine.Option(names = {"-c", "--checkoutDate"}, paramLabel = "DATE", description = "checkout date (MM/DD/YY), example: 04-Jul-2024", required = true)
    private String checkOutDate;

    @NonFinal
    @CommandLine.Option(names = {"--timeZone"}, paramLabel = "TIME_ZONE_ID", description = "(Optional) Time zone ID (defaults to America/New_York)", required = false)
    private String timeZoneId = "America/New_York";

    public static void main(String[] args) throws Exception {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() /* throws Exception */ {
        ToolRentalController rentalController = new ToolRentalController();

        // lookup tool for code
        Optional<Tool> toolQ = rentalController.toolForCode(toolCode);
        if (toolQ.isEmpty()) {
            System.err.println("Tool not found for code: " + toolCode);
            return 1;
        }

        // validate the date, forgive missing leading zeros
        ZonedDateTime zonedCheckOutDate;
        try {
            zonedCheckOutDate = Tools.dayMonthYearFormat(checkOutDate, timeZoneId);
        } catch (DateTimeParseException parseError) {
            if (checkOutDate.indexOf('/') < 2) {
                // likely missing leading zero
                checkOutDate = "0" + checkOutDate;
                try {
                zonedCheckOutDate = Tools.dayMonthYearFormat(checkOutDate, timeZoneId);
                } catch (DateTimeParseException fatalParseError) {
                    System.err.println("Invalid checkout date: " + checkOutDate);
                    return 2;
                }
            } else {
                System.err.println("Invalid checkout date: " + checkOutDate);
                return 2;
            }
        }

        // model our rental input
        Rental rental = Rental.builder()
                .tool(toolQ.get())
                .checkOutDate(zonedCheckOutDate)
                .percentDiscount(discount)
                .rentalDayCount(dayCount)
                .build();

        // use our controller to calculate agreement
        int exitStatus = 0;
        // @TODO we'd likely use a more robust logging approach here, or at least emit the trace to a log file
        try {
            System.out.println(rentalController.calculate(rental));
        } catch (ValidationException e) {
            System.err.println("Validation error: " + e.getMessage());
            exitStatus = 3;
        } catch (CalculationException e) {
            System.err.println("Calculation error: " + e.getMessage());
            System.err.println("for rental:\n" + rental);
            e.printStackTrace();
            exitStatus = 4;
        }

        return exitStatus;
    }
}
