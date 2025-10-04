package card.shop.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import card.shop.controller.model.CustomerData;
import card.shop.entity.Customer;

	public class ShopServiceTestSupport {
		
		private static final String PURCHASE_TABLE = "purchase";

		private static final String PURCHASE_CARD_TABLE = "purchase_card";

		private static final String CARD_TABLE = "card";

		private static final String CUSTOMER_TABLE = "customer";

		private static final String INSERT_PURCHASE_1_SQL = """
				INSERT INTO purchase 
				(total_price, quantity, purchase_date, customer_id)
				VALUES (15.00, 7, '2025-10-04', 1)
				""";

		private static final String INSERT_PURCHASE_2_SQL = """
				INSERT INTO purchase 
				(total_price, quantity, purchase_date, customer_id) 
				VALUES (15.00, 7, '2025-10-04', 1)
				""";

		private static final String INSERT_CARDS_1_SQL = """
				INSERT INTO purchase_card 
				(purchase_id, card_id) 
				VALUES (1, 3), (1, 13)
				""";

		private static final String INSERT_CARDS_2_SQL = """
				INSERT INTO purchase_card 
				(purchase_id, card_id) 
				VALUES (2, 5), (2, 16)
				""";

		//@formatter:off
		private CustomerData insertCustomer1 = new CustomerData(
				1L,
				"Logan",
				"Blum",
				"123 Main st",
				"(123) 456-7890"
		);
		
		private CustomerData insertCustomer2 = new CustomerData(
				2L,
				"John",
				"Smith",
				"876 Market st",
				"(098) 765-4321"
		);
		
		private CustomerData updateCustomer1 = new CustomerData(
				
				1L,
				"Mary",
				"Sue",
				"123 Main st",
				"(123) 456-7890"
				);
				
				
				
				
		
		//@formatter:on
		
		
		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		private ShopController shopController;
		
		protected CustomerData buildInsertCustomer(int which) {
			
			return which == 1 ? insertCustomer1 : insertCustomer2;
	}
		
	protected CustomerData insertCustomer(CustomerData customerData) {
			Customer customer = customerData.toCustomer();
			CustomerData clone = new CustomerData(customer);
			
			clone.setCustomerId(null);
			
			return shopController.createCustomer(clone);
	}

	protected int rowsInCustomerTable() {
			
			return JdbcTestUtils.countRowsInTable(jdbcTemplate, CUSTOMER_TABLE);
	}
	
	protected CustomerData retrieveCustomer(Long customerId) {
		
		return shopController.retrieveCustomer(customerId);
	}
	
	
	protected List<CustomerData> insertTwoCustomers() {
		CustomerData customer1 = insertCustomer(buildInsertCustomer(1));
		CustomerData customer2 = insertCustomer(buildInsertCustomer(2));
		return List.of(customer1, customer2);
	}
	
	protected List<CustomerData> retrieveAllCustomers() {
		return shopController.retrieveAllCustomers();
	}
	
	protected List<CustomerData> sorted(List<CustomerData> list) {
		List<CustomerData> data= new LinkedList<>(list);
		
		data.sort((loc1,loc2) -> (int)(loc1.getCustomerId() - loc2.getCustomerId()));
		return data;
	}
	
	protected CustomerData updateCustomer(CustomerData customerData) {
		return shopController.updateCustomer(customerData.getCustomerId(), customerData);
	}

	protected CustomerData buildUpdateCustomer() {
		return updateCustomer1;
	}
	
	protected void insertPurchase(int which) {
		String purchaseSql = which == 1 ? INSERT_PURCHASE_1_SQL : INSERT_PURCHASE_2_SQL;
		String cardSql = which == 1 ? INSERT_CARDS_1_SQL : INSERT_CARDS_2_SQL;
		
		jdbcTemplate.update(purchaseSql);
		jdbcTemplate.update(cardSql);
		
	}
	
	protected int rowsInCardTable() {
		// TODO Auto-generated method stub
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, CARD_TABLE);
	}

	protected int rowsInPurchaseCardTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, PURCHASE_CARD_TABLE);
	}

	protected int rowsInPurchaseTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, PURCHASE_TABLE);
	}
	
	protected void deleteCustomer(Long customerId) {
		shopController.deleteCustomer(customerId);
		
	}
}
