package card.shop.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import card.shop.entity.Card;

public interface CardDao extends JpaRepository<Card, Long> {

	Set<Card> findByCardNameIn(Set<String> cardNames);

}
