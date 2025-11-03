package net.ayad.firstcicdpipeline.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.ayad.firstcicdpipeline.dto.CreateProductDTO;
import net.ayad.firstcicdpipeline.dto.ProductResponseDTO;
import net.ayad.firstcicdpipeline.dto.UpdateProductDTO;
import net.ayad.firstcicdpipeline.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "APIs for managing products")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get product by ID", description = "Retrieve a product by its ID")
    @ApiResponse(responseCode = "200", description = "Product retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @Operation(summary = "Create a new product", description = "Create a new product with the provided details")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        ProductResponseDTO product = productService.createProduct(createProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Operation(summary = "Update a product", description = "Update the details of an existing product")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody UpdateProductDTO updateProductDTO) {
        System.out.println("Updating product with ID: " + id);
        ProductResponseDTO productUpdated = productService.updateProduct(id, updateProductDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productUpdated);
    }

    @Operation(summary = "Delete a product", description = "Delete a product by its ID")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
