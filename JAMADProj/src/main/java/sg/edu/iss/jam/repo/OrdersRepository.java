package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
