package net.ayad.firstcicdpipeline.mapper;

import net.ayad.firstcicdpipeline.dto.CreateProductDTO;
import net.ayad.firstcicdpipeline.dto.ProductResponseDTO;
import net.ayad.firstcicdpipeline.dto.UpdateProductDTO;
import net.ayad.firstcicdpipeline.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO toProductResponseDTO(Product product);

    Product toProductEntity(CreateProductDTO createProductDTO);

    Product updateProductFromDTO(UpdateProductDTO updateProductDTO, @MappingTarget Product product);

}
