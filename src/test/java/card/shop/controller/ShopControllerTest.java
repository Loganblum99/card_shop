package card.shop.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import card.shop.CardShopApplication;
import card.shop.controller.model.CustomerData;


@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = CardShopApplication.class)

@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@SqlConfig(encoding = "utf-8")
class ShopControllerTest extends ShopServiceTestSupport{

	@Test
	void testInsertCustomer() {
		//Given: a customer request
		
		CustomerData request = buildInsertCustomer(1);
		CustomerData expected = buildInsertCustomer(1);
		
		//When: the customer is added to the customer table
		
		CustomerData actual = insertCustomer(request);
		
		//Then: The customer returned is what is expected
		
		assertThat(actual).isEqualTo(expected);
		
		//And: there is one row in the customer table
		
		assertThat(rowsInCustomerTable()).isOne();
		
		
	}

	@Test
	void testRetrieveCustomer() {
		//Given: a Customer
		CustomerData customer = insertCustomer(buildInsertCustomer(1));
		CustomerData expected = buildInsertCustomer(1);
		
		//When: the customer is retrieved by customer ID
		
		CustomerData actual = retrieveCustomer(customer.getCustomerId());
		//Then: the actual customer is equal to the expected customer
		
		assertThat(actual).isEqualTo(expected);
		
		
		
	}

	@Test
	void testRetrieveAllCustomers() {
		//Given: two customers
		List<CustomerData> expected = insertTwoCustomers();
		//When: all customers are retrieved
		List<CustomerData> actual = retrieveAllCustomers();
		//Then: the retrieved customers are the same as expected
		
		assertThat(sorted(actual)).isEqualTo(sorted(expected));
	}

	@Test
	void testUpdateCustomer() {
		//Given: a Customer and an update request
		
		insertCustomer(buildInsertCustomer(1));
		CustomerData expected = buildUpdateCustomer();
		
		//When: the customer is updated
		
		CustomerData actual = updateCustomer(expected);
		//Then: the customer is return as expected
		
		assertThat(actual).isEqualTo(expected);
		
		//And: there is one row in the customer table
		
		assertThat(rowsInCustomerTable()).isOne();
		
	}

	@Test
	void testDeleteCustomerWithPurchases() {
		//Given: a customer and 2 purchases
		CustomerData customer = insertCustomer(buildInsertCustomer(1));
		Long customerId = customer.getCustomerId();
		
		insertPurchase(1);
		insertPurchase(2);
		
		assertThat(rowsInCustomerTable()).isOne();
		assertThat(rowsInPurchaseTable()).isEqualTo(2);
		assertThat(rowsInPurchaseCardTable()).isEqualTo(4);
		int cardRows = rowsInCardTable();
		
		//When:the customer is deleted
		deleteCustomer(customerId);
		
		//Then there are no customer, purchase, and purchase_card rows
		assertThat(rowsInCustomerTable()).isZero();
		assertThat(rowsInPurchaseTable()).isZero();
		assertThat(rowsInPurchaseCardTable()).isZero(); 
		
		//And: The number of card rows have not changed
		assertThat(rowsInCardTable()).isEqualTo(cardRows);
		
	}

	

	

	

	

	

	
	

}

