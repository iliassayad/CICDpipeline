package net.ayad.firstcicdpipeline.service;

import lombok.RequiredArgsConstructor;
import net.ayad.firstcicdpipeline.dto.CreateProductDTO;
import net.ayad.firstcicdpipeline.dto.ProductResponseDTO;
import net.ayad.firstcicdpipeline.dto.UpdateProductDTO;
import net.ayad.firstcicdpipeline.exception.ProductNotFoundException;
import net.ayad.firstcicdpipeline.mapper.ProductMapper;
import net.ayad.firstcicdpipeline.model.Product;
import net.ayad.firstcicdpipeline.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return productMapper.toProductResponseDTO(product);
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }

    public ProductResponseDTO createProduct(CreateProductDTO createProductDTO) {
        Product product = productMapper.toProductEntity(createProductDTO);
        Product savedProduct = productRepository.save(product);

        return productMapper.toProductResponseDTO(savedProduct);
    }


    public ProductResponseDTO updateProduct(Long id, UpdateProductDTO updateProductDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Product updatedProduct = productMapper.updateProductFromDTO(updateProductDTO, existingProduct);
        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.toProductResponseDTO(savedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }


}
