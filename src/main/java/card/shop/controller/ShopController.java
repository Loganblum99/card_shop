package card.shop.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import card.shop.controller.model.CustomerData;
import card.shop.controller.model.PurchaseInfo;
import card.shop.controller.model.PurchaseInfo.PurchaseInfoCard;
import card.shop.entity.Card;
import card.shop.service.ShopService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/card_shop")
@Slf4j
public class ShopController {
	@Autowired
	private ShopService shopService;
	
	/*
	 *  Create a customer
	 */
	@PostMapping("/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CustomerData createCustomer(@RequestBody CustomerData customerData) {
			log.info("Creating Customer {", customerData);
			return shopService.saveCustomer(customerData);
	}
	/*
	 * Update a customer 
	 */
	
	
	@PutMapping("/customer/{customerId}")
	public CustomerData updateCustomer(@PathVariable Long customerId, @RequestBody CustomerData customerData) {
		customerData.setCustomerId(customerId);
		log.info("Updating customer {}", customerData);
		return shopService.saveCustomer(customerData);
	}
	
	/*
	 * Retrieve all details of the customer with the given ID
	 */
	
	@GetMapping("/customer/{customerId}")
	public CustomerData retrieveCustomer(@PathVariable Long customerId) {
		log.info("Retrieving customer with ID={}", customerId);
		return shopService.retrieveCustomerById(customerId);
	}
	
	/*
	 * Retrieve all customers with details
	 */
	
	@GetMapping("/customer")
	public List<CustomerData> retrieveAllCustomers(){
		log.info("Retrieving all customers");
		return shopService.retrieveAllCustomers();
	}
	
	/*
	 * Delete customer with the entered ID
	 */

	@DeleteMapping("/customer/{customerId}")
	public Map<String, String> deleteCustomer(@PathVariable Long customerId){
		log.info("Deleting Customer with ID=" + customerId + ".");
		shopService.deleteCustomer(customerId);
		
		return Map.of("message", "Customer with ID=" + customerId + " was deleted successfully");
	}
	
	/*
	 * Create a purchase for the customer matching the given customerID
	 */
	
	@PostMapping("/customer/{customerId}/purchase")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PurchaseInfo insertPurchase(@PathVariable Long customerId, @RequestBody PurchaseInfo purchaseInfo) {
			log.info("Creating Purchase {} at customer ID={}", purchaseInfo);
			return shopService.savePurchase(customerId, purchaseInfo);
	}
	/*
	 * Retrieve all cards within the given purchase
	 */
	@GetMapping("/purchase/{purchaseId}/cards")
	public Set<Card> getCardInPurchase(@PathVariable Long purchaseId){
		return shopService.retrieveCardsByPurchaseId(purchaseId);
		
	}
	
	/*
	 * Create a new Card 
	 */
	@PostMapping("/card")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PurchaseInfoCard insertCard(@RequestBody PurchaseInfoCard purchaseInfoCard) {
		log.info("Creating Card {");
		return shopService.saveCard(purchaseInfoCard);
	}
		
	
}
