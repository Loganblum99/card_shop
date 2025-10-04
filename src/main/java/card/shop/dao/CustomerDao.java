package card.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import card.shop.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
