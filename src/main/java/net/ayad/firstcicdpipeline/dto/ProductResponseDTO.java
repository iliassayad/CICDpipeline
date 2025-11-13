package net.ayad.firstcicdpipeline.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for responding with Product details")
public class ProductResponseDTO {
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;
    @Schema(description = "Name of the product", example = "Laptop")
    private String name;
    @Schema(description = "Description of the product", example = "A high-performance laptop")
    private String description;
    @Schema(description = "Price of the product", example = "999.99")
    private BigDecimal price;
    @Schema(description = "Creation timestamp of the product", example = "2024-01-01T12:00:00")
    private LocalDateTime createdAt;
}
