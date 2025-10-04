package card.shop.controller.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import card.shop.entity.Card;
import card.shop.entity.Customer;
import card.shop.entity.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PurchaseInfo {
  private Long purchaseId;
  private BigDecimal totalPrice;
  private Integer quantity;
  private LocalDate purchaseDate;
  private PurchaseInfoCustomer customer;
  private Set<PurchaseInfoCard> cards = new HashSet<>();

  
  public PurchaseInfo(Purchase purchase) {
    this.purchaseId = purchase.getPurchaseId();
    this.totalPrice = purchase.getTotalPrice();
    this.quantity = purchase.getQuantity();
    this.purchaseDate = purchase.getPurchaseDate();

    if(Objects.nonNull(purchase.getCustomer())) {
      this.customer = new PurchaseInfoCustomer(purchase.getCustomer());
    }

    for(Card card : purchase.getCards()) {
      this.cards.add(new PurchaseInfoCard(card));
    }
  }

  
  public PurchaseInfo(Long purchaseId, BigDecimal totalPrice, Integer quantity, LocalDate purchaseDate, Set<PurchaseInfoCard> cards) {
    this.purchaseId = purchaseId;
    this.totalPrice = totalPrice;
    this.quantity = quantity;
    this.purchaseDate = purchaseDate;
    this.cards = cards;
  }

  
  public Purchase toPurchase() {
    Purchase purchase = new Purchase();

    purchase.setTotalPrice(totalPrice);
    purchase.setQuantity(quantity);
    purchase.setPurchaseId(purchaseId);
    purchase.setPurchaseDate(purchaseDate);

    for(PurchaseInfoCard card : cards) {
      purchase.getCards().add(card.toCard());
    }

    return purchase;
  }

  
  @Data
  @NoArgsConstructor
  public static class PurchaseInfoCustomer {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String customerAddress;
    private String phoneNumber;

    
    public PurchaseInfoCustomer(Customer customer) {
      this.customerId = customer.getCustomerId();
      this.firstName = customer.getFirstName();
      this.lastName = customer.getLastName();
      this.customerAddress = customer.getCustomerAddress();
      this.phoneNumber = customer.getPhoneNumber();
    }
  }

  
  @Data
  @NoArgsConstructor
  public static class PurchaseInfoCard {
    private Long cardId;
    private String cardName;
    private String setName;
    private String rarity;
    private BigDecimal price;

   
    public PurchaseInfoCard(Card card) {
      this.cardId = card.getCardId();
      this.cardName = card.getCardName();
      this.setName = card.getSetName();
      this.rarity = card.getRarity();
      this.price = card.getPrice();
    }

    
    public PurchaseInfoCard(Long cardId, String cardName, String setName, String rarity, BigDecimal price) {
      this.cardId = cardId;
      this.cardName = cardName;
      this.setName = setName;
      this.rarity = rarity;
      this.price = price;
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