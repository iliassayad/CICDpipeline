package net.ayad.firstcicdpipeline.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for updating an existing Product")
public class UpdateProductDTO {
    @Schema(description = "Name of the product", example = "Laptop")
    private String name;
    @Schema(description = "Description of the product", example = "A high-performance laptop")
    private String description;
    @Schema(description = "Price of the product", example = "999.99")
    private BigDecimal price;
}
