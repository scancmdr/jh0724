package tooltime.model;

import lombok.Builder;

/**
 * Model object representing a Tool for rent
 *
 * @param code globally unique identifier
 * @param type type of tool
 * @param brand Manufacturer name brand
 *
 * @author jay
 */
@Builder
public record Tool(
        /*
        globally unique identifier
         */
        String code,

        /*
        type of tool
         */
        Type type,

        /*
        Manufacturer name brand
         */
        String brand
) {

    @Override
    public String toString() {
        return """
                code: %s
                type: %s
                brand: %s
                """.formatted(code, type, brand);
    }
}
