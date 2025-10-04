package card.shop.service;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import card.shop.controller.model.CustomerData;
import card.shop.controller.model.PurchaseInfo;
import card.shop.controller.model.PurchaseInfo.PurchaseInfoCard;
import card.shop.dao.CardDao;
import card.shop.dao.CustomerDao;
import card.shop.dao.PurchaseDao;
import card.shop.entity.Card;
import card.shop.entity.Customer;
import card.shop.entity.Purchase;

@Service
public class ShopService {
	
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private PurchaseDao purchaseDao;
	@Autowired
	private CardDao cardDao;
	
	@Transactional(readOnly = false)
	public CustomerData saveCustomer(CustomerData customerData) {
		Customer customer = customerData.toCustomer();
		
		Customer dbCustomer = customerDao.save(customer);
		
		
		return new CustomerData(dbCustomer);
	
	}

	
	@Transactional(readOnly = true)
	public CustomerData retrieveCustomerById(Long customerId) {
		Customer customer = findCustomerById(customerId);
		return new CustomerData(customer);
	}


	private Customer findCustomerById(Long customerId) {
		return customerDao.findById(customerId).orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found"));
	}

	@Transactional(readOnly = true)
	public List<CustomerData> retrieveAllCustomers() {
		List<Customer> customerEntities = customerDao.findAll();
		List<CustomerData> customerDtos = new LinkedList<>();
		
		customerEntities.sort((loc1,loc2) -> loc1.getFirstName().compareTo(loc2.getFirstName()));
		
		for(Customer customer : customerEntities) {
			CustomerData customerData = new CustomerData(customer);
			customerDtos.add(customerData);
		}
		
		return customerDtos;
	}

	@Transactional(readOnly = false)
	public void deleteCustomer(Long customerId) {
		Customer customer = findCustomerById(customerId);
		customerDao.delete(customer);
		
	}

	@Transactional(readOnly = false)
	public PurchaseInfo savePurchase(Long customerId, PurchaseInfo purchaseInfo) {
		Customer customer = findCustomerById(customerId);
		Set<String> cardNames = extractCardNames(purchaseInfo);
		Set<Card> cards = cardDao.findByCardNameIn(cardNames);
		Long purchaseId = purchaseInfo.getPurchaseId();
		Purchase purchase = findOrCreatePurchase(purchaseId);
		
		copyPurchaseFields(purchase, purchaseInfo);
		
		purchase.setCards(cards);;
		customer.getPurchases().add(purchase);
		purchase.setCustomer(customer);
		
		return new PurchaseInfo(purchaseDao.save(purchase));
		
		
	}


	private Purchase findOrCreatePurchase(Long purchaseId) {
		return Objects.nonNull(purchaseId) ? findPurchaseById(purchaseId) : new Purchase();
	}


	private Purchase findPurchaseById(Long purchaseId) {
		return purchaseDao.findById(purchaseId).orElseThrow(() -> new NoSuchElementException("Purchase with ID=" + purchaseId + "was not found"));
	}


	private void copyPurchaseFields(Purchase purchase, PurchaseInfo purchaseInfo) {
		purchase.setTotalPrice(purchaseInfo.getTotalPrice());
		purchase.setQuantity(purchaseInfo.getQuantity());
		purchase.setPurchaseDate(purchaseInfo.getPurchaseDate());
	}


	private Set<String> extractCardNames(PurchaseInfo purchaseInfo) {
		Set<String> names = new HashSet<>();
		for(PurchaseInfoCard card : purchaseInfo.getCards()) {
			names.add(card.getCardName());
		}
		return names;
		
	}


	

}
