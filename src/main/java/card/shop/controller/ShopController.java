package card.shop.controller;

import java.util.List;
import java.util.Map;

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
import card.shop.service.ShopService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/card_shop")
@Slf4j
public class ShopController {
	@Autowired
	private ShopService shopService;
	
	
	
	@PostMapping("/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CustomerData createCustomer(@RequestBody CustomerData customerData) {
			log.info("Creating Customer {", customerData);
			return shopService.saveCustomer(customerData);
	}
	
	@PutMapping("/customer/{customerId}")
	public CustomerData updateCustomer(@PathVariable Long customerId, @RequestBody CustomerData customerData) {
		customerData.setCustomerId(customerId);
		log.info("Updating customer {}", customerData);
		return shopService.saveCustomer(customerData);
	}
	
	
	@GetMapping("/customer/{customerId}")
	public CustomerData retrieveCustomer(@PathVariable Long customerId) {
		log.info("Retrieving customer with ID={}", customerId);
		return shopService.retrieveCustomerById(customerId);
	}
	
	@GetMapping("/customer")
	public List<CustomerData> retrieveAllCustomers(){
		log.info("Retrieving all customers");
		return shopService.retrieveAllCustomers();
	}

	@DeleteMapping("/customer/{customerId}")
	public Map<String, String> deleteCustomer(@PathVariable Long customerId){
		log.info("Deleting Customer with ID=" + customerId + ".");
		shopService.deleteCustomer(customerId);
		
		return Map.of("message", "Customer with ID=" + customerId + " was deleted successfully");
	}
	
	@PostMapping("/customer/{customerId}/purchase")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PurchaseInfo insertPurchase(@PathVariable Long customerId, @RequestBody PurchaseInfo purchaseInfo) {
			log.info("Creating Purchase {} at customer ID={}", purchaseInfo);
			return shopService.savePurchase(customerId, purchaseInfo);
	}
	
	
	
}
