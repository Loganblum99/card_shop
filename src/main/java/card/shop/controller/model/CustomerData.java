package card.shop.controller.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import card.shop.entity.Card;
import card.shop.entity.Customer;
import card.shop.entity.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerData {
	private Long customerId;
	private String firstName;
	private String lastName;
	private String customerAddress;
	private String phoneNumber;
	private Set<PurchaseData> purchases = new HashSet<>();

	public CustomerData(Customer customer) {
		this.customerId = customer.getCustomerId();
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
		this.customerAddress = customer.getCustomerAddress();
		this.phoneNumber = customer.getPhoneNumber();
		
		
		for(Purchase purchase : customer.getPurchases()) {
			this.purchases.add(new PurchaseData(purchase));
		}
	}
	
	
	public CustomerData(Long customerId, String firstName, String lastName, String customerAddress, String phoneNumber) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.customerAddress = customerAddress;
		this.phoneNumber = phoneNumber;
	}
	
	public Customer toCustomer() {
		Customer customer = new Customer();
		
		customer.setCustomerId(customerId);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setCustomerAddress(customerAddress);
		customer.setPhoneNumber(phoneNumber);
		
		for(PurchaseData purchaseData : purchases) {
			customer.getPurchases().add(purchaseData.toPurchase());
		}
		
		return customer;
	}
	
	@Data
	@NoArgsConstructor
	public class PurchaseData {
		private Long purchaseId;
		private BigDecimal totalPrice;
		private Integer quantity;
		private LocalDate purchaseDate;
		private Set<CardData> cards = new HashSet<>();
		
		public PurchaseData(Purchase purchase) {
			this.purchaseId = purchase.getPurchaseId();
			this.totalPrice = purchase.getTotalPrice();
			this.quantity = purchase.getQuantity();
			this.purchaseDate = purchase.getPurchaseDate();
			
			for(Card card : purchase.getCards()) {
				this.cards.add(new CardData(card));
			}
		}
		
		public Purchase toPurchase() {
			Purchase purchase = new Purchase();
			
			purchase.setPurchaseId(purchaseId);
			purchase.setTotalPrice(totalPrice);
			purchase.setQuantity(quantity);
			purchase.setPurchaseDate(purchaseDate);
			
			for(CardData cardData : cards) {
				purchase.getCards().add(cardData.toCard());
			}
			return purchase;
		}
		
		
	}
	@Data
	@NoArgsConstructor
	 public class CardData{
		private Long cardId;
		private String cardName;
		private String setName;
		private String rarity;
		private BigDecimal price;
		
		public CardData(Card card) {
			this.cardId = card.getCardId();
			this.cardName = card.getCardName();
			this.setName = card.getSetName();
			this.rarity = card.getRarity();
			this.price = card.getPrice();
		}
		public Card toCard() {
			Card card = new Card();
			
			card.setCardId(cardId);
			card.setCardName(cardName);
			card.setSetName(setName);
			card.setRarity(rarity);
			card.setPrice(price);
			
			return card;
		}
	 }
}
