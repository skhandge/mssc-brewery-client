package guru.springframework.msscbreweryclient.web.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.msscbreweryclient.web.model.BeerDto;

@SpringBootTest
class BreweryClientTest {

	@Autowired
	BreweryClient breweryClient;
	
	@Test
	void getBeerById() {
		BeerDto dto = breweryClient.getBeerById(UUID.randomUUID());
		assertNotNull(dto);
	}

	@Test
	void testSaveNewBeer() {
		BeerDto beerDto = BeerDto.builder().beerName("New Beer").build();
		System.out.println("  :  " + breweryClient.getApihost() );
		System.out.println(beerDto.getBeerName() ); 
		
		URI uri = breweryClient.saveNewBeer(beerDto);
		assertNotNull(uri);
		System.out.println("URI  : " + uri);
	}
	
	@Test
	void testUpdateBeer() {
		BeerDto beerDto = BeerDto.builder().beerName("New Beer").build();
		breweryClient.updateBeer(UUID.randomUUID(), beerDto);
	}
	
	@Test
	void testDeleteBeer() {
		breweryClient.deleteBeer(UUID.randomUUID());
	}
}
