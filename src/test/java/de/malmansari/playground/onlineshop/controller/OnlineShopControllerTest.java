package de.malmansari.playground.onlineshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OnlineShopControllerTest {
	private static final String HTTP_LOCALHOST_PREFIX = "http://localhost:";

	@LocalServerPort
	private int randomPort;

	private List<JSONObject> expectedProducts;

	@Test
	public void getProducts_allProductsAreRetrieved_Ok() throws URISyntaxException, JSONException {
		// assume
		prepareProductList();
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = HTTP_LOCALHOST_PREFIX + randomPort + "/api/products/";
		URI uri = new URI(baseUrl);

		// act
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// assert
		assertEquals(200, result.getStatusCodeValue());
		JSONAssert.assertEquals(result.getBody(), expectedProducts.toString(), true);
	}

	@Test
	public void order_requestIsValid_returnsUpdatedProduct() throws URISyntaxException, JSONException {
		// assume
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = HTTP_LOCALHOST_PREFIX + randomPort + "/api/order/products/1/5/";

		URI uri = new URI(baseUrl);
		JSONObject expectedResult = new JSONObject().put("id", 1).put("name", "Men Jeans").put("stock", 95);

		// act
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// assert
		assertEquals(200, result.getStatusCodeValue());
		JSONAssert.assertEquals(result.getBody(), expectedResult.toString(), true);
	}

	@Test
	public void order_requestWithInvalidProductId_returnsProductNotFoundError() throws URISyntaxException, JSONException {
		// assume
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = HTTP_LOCALHOST_PREFIX + randomPort + "/api/order/products/17/5/";
		URI uri = new URI(baseUrl);

		// act
		HttpClientErrorException.NotFound thrown =
		        assertThrows(HttpClientErrorException.NotFound.class,
		           () -> restTemplate.getForEntity(uri, String.class),
		           "Expected order() to throw exception, but it didn't");
		// assert
		assertTrue(thrown.getMessage().contains("Product not found"));
	}
	
	@Test
	public void order_requestWithQuantityLargerThanStock_returnsProductHasNotEnoughStockError() throws URISyntaxException, JSONException {
		// assume
		RestTemplate restTemplate = new RestTemplate();
		
		final String baseUrl = HTTP_LOCALHOST_PREFIX + randomPort + "/api/order/products/2/500";
		URI uri = new URI(baseUrl);
		
		// act
		HttpClientErrorException.BadRequest thrown =
				assertThrows(HttpClientErrorException.BadRequest.class,
						() -> restTemplate.getForEntity(uri, String.class),
						"Expected order to throw exception, but it didn't");
		// assert
		assertTrue(thrown.getMessage().contains("Product has not enough stock for your order"));
	}
	
	@Test
	public void order_requestWithNonStrictlyPositiveQuantity_returnsConstraintsViolationError() throws URISyntaxException, JSONException {
		// assume
		RestTemplate restTemplate = new RestTemplate();
		
		final String baseUrl = HTTP_LOCALHOST_PREFIX + randomPort + "/api/order/products/2/-5";
		URI uri = new URI(baseUrl);
		
		// act
		HttpClientErrorException.BadRequest thrown =
				assertThrows(HttpClientErrorException.BadRequest.class,
						() -> restTemplate.getForEntity(uri, String.class),
						"Expected order to throw exception, but it didn't");
		// assert
		assertTrue(thrown.getMessage().contains("order.quantity: must be greater than 0"));
	}
	
	@Test
	public void order_requestWithNonStrictlyPositiveProductId_returnsConstraintsViolationError() throws URISyntaxException, JSONException {
		// assume
		RestTemplate restTemplate = new RestTemplate();
		
		final String baseUrl = HTTP_LOCALHOST_PREFIX + randomPort + "/api/order/products/-2/5";
		URI uri = new URI(baseUrl);
		
		// act
		HttpClientErrorException.BadRequest thrown =
				assertThrows(HttpClientErrorException.BadRequest.class,
						() -> restTemplate.getForEntity(uri, String.class),
						"Expected order to throw exception, but it didn't");
		// assert
		assertTrue(thrown.getMessage().contains("order.id: must be greater than 0"));
	}
	
	
	@Test
	public void addToStock_requestIsValid_returnsUpdatedProduct() throws URISyntaxException, JSONException {
		// assume
		RestTemplate restTemplate = new RestTemplate();
		
		final String baseUrl = HTTP_LOCALHOST_PREFIX + randomPort + "/api/add/products/1/5/";
		
		URI uri = new URI(baseUrl);
		JSONObject expectedResult = new JSONObject().put("id", 1).put("name", "Men Jeans").put("stock", 105);
		
		// act
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		
		// assert
		assertEquals(200, result.getStatusCodeValue());
		JSONAssert.assertEquals(result.getBody(), expectedResult.toString(), true);
	}
	
	@Test
	public void addToStock_requestWithInvalidProductId_returnsProductNotFoundError() throws URISyntaxException, JSONException {
		// assume
		RestTemplate restTemplate = new RestTemplate();
		
		final String baseUrl = HTTP_LOCALHOST_PREFIX + randomPort + "/api/add/products/17/5/";
		URI uri = new URI(baseUrl);
		
		// act
		HttpClientErrorException.NotFound thrown =
				assertThrows(HttpClientErrorException.NotFound.class,
						() -> restTemplate.getForEntity(uri, String.class),
						"Expected order() to throw exception, but it didn't");
		// assert
		assertTrue(thrown.getMessage().contains("Product not found"));
	}
	
	@Test
	public void addToStock_requestWithNonStrictlyPositiveQuantity_returnsConstraintsViolationError() throws URISyntaxException, JSONException {
		// assume
		RestTemplate restTemplate = new RestTemplate();
		
		final String baseUrl = HTTP_LOCALHOST_PREFIX + randomPort + "/api/add/products/2/-5";
		URI uri = new URI(baseUrl);
		
		// act
		HttpClientErrorException.BadRequest thrown =
				assertThrows(HttpClientErrorException.BadRequest.class,
						() -> restTemplate.getForEntity(uri, String.class),
						"Expected order to throw exception, but it didn't");
		// assert
		assertTrue(thrown.getMessage().contains("addToStock.quantity: must be greater than 0"));
	}
	
	@Test
	public void addToStock_requestWithNonStrictlyPositiveProductId_returnsConstraintsViolationError() throws URISyntaxException, JSONException {
		// assume
		RestTemplate restTemplate = new RestTemplate();
		
		final String baseUrl = HTTP_LOCALHOST_PREFIX + randomPort + "/api/add/products/-2/5";
		URI uri = new URI(baseUrl);
		
		// act
		HttpClientErrorException.BadRequest thrown =
				assertThrows(HttpClientErrorException.BadRequest.class,
						() -> restTemplate.getForEntity(uri, String.class),
						"Expected order to throw exception, but it didn't");
		// assert
		assertTrue(thrown.getMessage().contains("addToStock.id: must be greater than 0"));
	}
	
	private void prepareProductList() throws JSONException {

		expectedProducts = new ArrayList<>();
		
		expectedProducts.add(new JSONObject().put("id", 1).put("name", "Men Jeans").put("stock", 100));
		expectedProducts.add(new JSONObject().put("id", 2).put("name", "Men Jacket").put("stock", 100));
		expectedProducts.add(new JSONObject().put("id", 3).put("name", "Women Shoe").put("stock", 100));
		expectedProducts.add(new JSONObject().put("id", 4).put("name", "Women Shirt").put("stock", 100));
		expectedProducts.add(new JSONObject().put("id", 5).put("name", "Children Short").put("stock", 100));
	}
}