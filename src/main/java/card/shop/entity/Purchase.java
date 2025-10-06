package card.shop.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * Entity Class for Purchase
 */

@Entity
@Data
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long purchaseId;
	
	@EqualsAndHashCode.Exclude
	private BigDecimal totalPrice;
	
	@EqualsAndHashCode.Exclude
	private Integer quantity;
	
	@EqualsAndHashCode.Exclude
	private LocalDate purchaseDate;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	
	/*
	 * Join table for Purchase and Card
	 */
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "purchase_card",
			joinColumns = @JoinColumn(name = "purchase_id"),
			inverseJoinColumns = @JoinColumn(name = "card_id")
	)
	private Set<Card> cards = new HashSet<>();
}
