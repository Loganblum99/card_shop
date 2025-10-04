package card.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import card.shop.entity.Purchase;

public interface PurchaseDao extends JpaRepository<Purchase, Long> {

}
