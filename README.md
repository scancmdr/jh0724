# Tooltime
Tool Rental Command Line Application

## Build
Prerequisites:
- The application requires Java 21, it is recommended to utilize [Amazon Corretto](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/downloads-list.html) or equivalent.
- Apache Maven is required for building, the wrapper is included here.
```shell
$ ./mvnw package
```

Use an alias to easily run after a build:

```shell
alias tooltime='java -jar target/tooltime-1.0.0.jar'
```

## Usage

```shell
Usage: tooltime [-hV] -c=DATE -d=DAY_COUNT -s=PERCENT -t=TOOL_CODE
                [--timeZone=TIME_ZONE_ID]
ToolTime - command line tool rental utility
  -c, --checkoutDate=DATE   checkout date (MM/DD/YY), example: 04-Jul-2024
  -d, --days=DAY_COUNT      number of days for the rental (1 or more)
  -h, --help                Show this help message and exit.
  -s, --discount=PERCENT    percent discount, integer (0-100)
  -t, --code=TOOL_CODE      code for tool to rent
      --timeZone=TIME_ZONE_ID
                            (Optional) Time zone ID (defaults to
                              America/New_York)
  -V, --version             Print version information and exit.
```

## Examples

```shell
$ tooltime --code CHNS --days 10 --discount 11 --checkoutDate=07/03/24
Tool code: CHNS
Tool type: Chainsaw
Tool brand: Stihl
Rental days: 10
Check out date: 07/03/24
Due date: 07/13/24
Daily rental charge: $1.49
Charge days: 7
Pre-discount charge: $10.43
Discount percent: 11%
Discount amount: $1.15
Final charge: $9.28
```

```shell
$ tooltime --code LADW --days 100 --discount 1 --checkoutDate=9/03/01
Tool code: LADW
Tool type: Ladder
Tool brand: Werner
Rental days: 100
Check out date: 09/03/01
Due date: 12/12/01
Daily rental charge: $1.99
Charge days: 100
Pre-discount charge: $199
Discount percent: 1%
Discount amount: $1.99
Final charge: $197.01
```

Some simple internationalization can be done with resource bundles:

```shell
$ java -Duser.country=US -Duser.language=es  tooltime.Main --code LADW --days 100 --discount 1 --checkoutDate=9/03/01
Código de herramienta: LADW
Tipo de herramienta: Ladder
Marca de herramienta: Werner
Días de alquiler: 100
Echa un vistazo a la fecha: 09/03/01
Fecha de vencimiento: 12/12/01
Cargo de alquiler diario : $1.99
Días de carga: 100
Cargo previo al descuento: $199
Porcentaje de descuento: 1%
Importe de descuento: $1.99
Carga final: $197.01
```

## Financial Arithmetic
Of course, computers use the binary (base-2) number system to represent numbers, including floating point numbers.
Problems arise when representing decimal (base-10) fractions in binary, as these fractions often cannot be 
represented exactly in a finite number of binary digits. These days, most systems use 
the [IEEE-754](https://en.wikipedia.org/wiki/IEEE_754) standard for floating-point arithmetic, which represents
numbers using scientific notation with an exponent and mantissa. This approach still has issues that are 
important to be aware of.

A basic illustration is that the representation of $0.10 is a repeating binary fraction and cannot be exactly 
represented using IEEE 754 without error. The error is very small, but in fields like finance, where quantities 
can be large, this error can accumulate with repeated operations such as multiplication.

For this reason, systems that perform pricing and currency calculations avoid using floating point primitives. 
For example, trading systems on Wall Street often use `BigDecimal` for its arbitrary precision or employ 
proprietary fixed-point arithmetic operations on integers to ensure accuracy and performance.

- Java's `double` is a 64-bit IEEE-754 binary floating point value (binary64)
- Note that [floating point operations changed in Java 17](https://openjdk.org/jeps/306), which removed the default modes.
- For fun we can test 754 with some [online tools](https://www.h-schmidt.net/FloatConverter/IEEE754.html) (see error with $0.10)

## Notes
- Uses rounding mode [HALF_UP](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/RoundingMode.html#HALF_UP) (nearest neighbor, favoring upward rounding)