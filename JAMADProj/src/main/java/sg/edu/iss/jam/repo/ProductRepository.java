package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
