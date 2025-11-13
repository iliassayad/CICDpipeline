package net.ayad.firstcicdpipeline.service;

import net.ayad.firstcicdpipeline.dto.CreateProductDTO;
import net.ayad.firstcicdpipeline.dto.ProductResponseDTO;
import net.ayad.firstcicdpipeline.dto.UpdateProductDTO;
import net.ayad.firstcicdpipeline.exception.ProductNotFoundException;
import net.ayad.firstcicdpipeline.mapper.ProductMapper;
import net.ayad.firstcicdpipeline.model.Product;
import net.ayad.firstcicdpipeline.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductResponseDTO productResponseDTO;
    private CreateProductDTO createProductDTO;
    private UpdateProductDTO updateProductDTO;

    @BeforeEach
    void beforeEach(){
        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("This is a test product")
                .price(BigDecimal.valueOf(99.99))
                .createdAt(LocalDateTime.now())
                .build();

        productResponseDTO = ProductResponseDTO.builder()
                            .id(1L)
                            .name("Test Product")
                            .description("This is a test product")
                            .price(BigDecimal.valueOf(99.99))
                            .createdAt(LocalDateTime.now())
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
    void testGetProductById_WhenProductExists_shouldReturnProductResponseDTO() {
        // Arrange
        final Long id = 1L;
        when(productRepository.findById(id))
                .thenReturn(Optional.of(product));
        when(productMapper.toProductResponseDTO(product))
                .thenReturn(productResponseDTO);
        //Act
        ProductResponseDTO result = productService.getProductById(id);
        // Assert
        assertNotNull(result);
        assertEquals(productResponseDTO.getId(), result.getId());
        verify(productRepository).findById(id);
        verify(productMapper).toProductResponseDTO(product);
    }

    @Test
    void testGetProductById_whenProductDoesNotExist_shouldProductNotFoundException() {
        //Arrange
        final Long id = 2L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        //Act & Assert
        RuntimeException exception =
                assertThrows(ProductNotFoundException.class, () ->{
                    productService.getProductById(id);
                });

        //Assert
        assertEquals("Product not found with ID: " + id, exception.getMessage());
        verify(productRepository).findById(id);
        verifyNoInteractions(productMapper);

    }

    @Test
    void testGetAllProducts_whenProductsExist_shouldReturnListOfProductResponseDTO(){
        //Arrange
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toProductResponseDTO(product)).thenReturn(productResponseDTO);

        //Act
        List<ProductResponseDTO> result = productService.getAllProducts();

        //Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productResponseDTO.getId(), result.get(0).getId());
        verify(productRepository).findAll();
        verify(productMapper).toProductResponseDTO(product);
    }

    @Test
    void testCreateProduct_whenValidCreateProductDTO_shouldReturnCreatedProductResponseDTO(){
        //Arrange
        when(productMapper.toProductEntity(createProductDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toProductResponseDTO(product)).thenReturn(productResponseDTO);

        // Act
        ProductResponseDTO result = productService.createProduct(createProductDTO);
        // Assert
        assertNotNull(result);
        assertEquals(productResponseDTO.getId(), result.getId());
        verify(productMapper).toProductEntity(createProductDTO);
        verify(productRepository).save(product);
        verify(productMapper).toProductResponseDTO(product);
    }

    @Test
    void testUpdateProduct_whenProductExists_shouldReturnUpdatedProductResponseDTO(){
        //Arrange
        final Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.updateProductFromDTO(updateProductDTO, product)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toProductResponseDTO(product)).thenReturn(productResponseDTO);
        //Act
        productService.updateProduct(id, updateProductDTO);
        //Assert
        verify(productRepository).findById(id);
        verify(productMapper).updateProductFromDTO(updateProductDTO, product);
        verify(productRepository).save(product);
        verify(productMapper).toProductResponseDTO(product);
    }

    @Test
    void testUpdateProduct_whenProductDoesNotExist_shouldThrowProductNotFoundException(){
        //Arrange
        final Long id = 2L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        //Act & Assert
        RuntimeException exception =
                assertThrows(ProductNotFoundException.class, () ->{
                    productService.updateProduct(id, updateProductDTO);
                });

        //Assert
        assertEquals("Product not found with ID: " + id, exception.getMessage());
        verify(productRepository).findById(id);
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(productMapper);
    }

    @Test
    void testDeleteProduct_whenProductExists_shouldDeleteProduct(){
        //Arrange
        final Long id = 1L;
        when(productRepository.existsById(id)).thenReturn(true);
        //Act
        productService.deleteProduct(id);
        //Assert
        verify(productRepository).existsById(id);
        verify(productRepository).deleteById(id);
    }

    @Test
    void testDeleteProduct_whenProductDoesNotExist_shouldThrowProductNotFoundException() {
        //Arrange
        final Long id = 2L;
        when(productRepository.existsById(id)).thenReturn(false);
        //Act & Assert
        RuntimeException exception =
                assertThrows(ProductNotFoundException.class, () -> {
                    productService.deleteProduct(id);
                });
        //Assert
        assertEquals("Product not found with ID: " + id, exception.getMessage());
        verify(productRepository).existsById(id);
        verify(productRepository, never()).deleteById(id);
    }
}