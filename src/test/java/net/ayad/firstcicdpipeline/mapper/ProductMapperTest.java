package net.ayad.firstcicdpipeline.mapper;

import net.ayad.firstcicdpipeline.dto.CreateProductDTO;
import net.ayad.firstcicdpipeline.dto.ProductResponseDTO;
import net.ayad.firstcicdpipeline.dto.UpdateProductDTO;
import net.ayad.firstcicdpipeline.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProductMapper Test")
class ProductMapperTest {

    ProductMapper productMapper = new ProductMapperImpl();

    Product product;
    CreateProductDTO createProductDTO;
    UpdateProductDTO updateProductDTO;

    @BeforeEach
    void beforeEach(){
        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("This is a test product")
                .price(java.math.BigDecimal.valueOf(99.99))
                .createdAt(java.time.LocalDateTime.now())
                .build();

        createProductDTO = CreateProductDTO.builder()
                .name("Test Product")
                .description("This is a test product")
                .price(BigDecimal.valueOf(99.99))
                .build();

        updateProductDTO = UpdateProductDTO.builder()
                .name("Updated Product")
                .description("This is an updated test product")
                .price(BigDecimal.valueOf(149.99))
                .build();

    }
    @Test
    void testToProductResponseDTO_whenValidProduct_shouldReturnProductResponseDTO(){
        //Arrange

        //Act
        ProductResponseDTO productResponseDTO = productMapper.toProductResponseDTO(product);
        //Assert
        assertNotNull(productResponseDTO);
        assertEquals(product.getId(), productResponseDTO.getId());
        assertEquals(product.getName(), productResponseDTO.getName());
        assertEquals(product.getDescription(), productResponseDTO.getDescription());
        assertEquals(product.getPrice(), productResponseDTO.getPrice());
        assertEquals(product.getCreatedAt(), productResponseDTO.getCreatedAt());
    }

    @Test
    void testToProductEntity_whenValidCreateProductDTO_shouldReturnProductEntity() {
        //Arrange
        //Act
        Product productToCreate = productMapper.toProductEntity(createProductDTO);
        //Assert
        assertNotNull(productToCreate);
        assertNull(productToCreate.getId());
        assertEquals(createProductDTO.getName(), productToCreate.getName());
        assertEquals(createProductDTO.getDescription(), productToCreate.getDescription());
        assertEquals(createProductDTO.getPrice(), productToCreate.getPrice());

    }


    @Test
    void testUpdateProductFromDTO_whenValidUpdateProductDTOAndExistingProduct_shouldReturnUpdatedProduct() {
        //Arrange

        //Act
        Product updatedProduct = productMapper.updateProductFromDTO(updateProductDTO, product);

        //Assert
        assertNotNull(updatedProduct);
        assertEquals(product.getId(), updatedProduct.getId());
        assertEquals(product.getCreatedAt(), updatedProduct.getCreatedAt());
        assertEquals(updateProductDTO.getName(), updatedProduct.getName());
        assertEquals(updateProductDTO.getDescription(), updatedProduct.getDescription());
        assertEquals(updateProductDTO.getPrice(), updatedProduct.getPrice());
    }


}