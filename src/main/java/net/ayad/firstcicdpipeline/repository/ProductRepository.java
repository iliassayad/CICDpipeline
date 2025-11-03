package net.ayad.firstcicdpipeline.repository;

import net.ayad.firstcicdpipeline.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
