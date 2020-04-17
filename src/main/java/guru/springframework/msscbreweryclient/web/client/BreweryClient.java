package guru.springframework.msscbreweryclient.web.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;

@ConfigurationProperties(value="sfg.brewery", ignoreUnknownFields = false)
@Component

@Slf4j
public class BreweryClient {

	public final String BEER_PATH_V1 = "/api/v1/beer/";
	private String apihost;

	public void setApihost(String apihost) {
		this.apihost = apihost;
	}

	public  String getApihost() {
		return apihost;
	}

	private final RestTemplate restTemplate;
	
	public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public BeerDto getBeerById(UUID uuid) {
		log.debug(apihost + BEER_PATH_V1 + uuid);
		return restTemplate.getForObject(apihost + BEER_PATH_V1 + uuid, BeerDto.class);
	}
	
	public URI saveNewBeer(BeerDto beerDto) {
		System.out.println("URL  : "+apihost + BEER_PATH_V1);
		return restTemplate.postForLocation(apihost + BEER_PATH_V1, beerDto);
	}
	
	public void updateBeer(UUID beerId, BeerDto beerto) {
		restTemplate.put(apihost+BEER_PATH_V1  + beerId.toString(), beerto);
	}
	
	public void deleteBeer(UUID beerId) {
		restTemplate.delete(apihost+BEER_PATH_V1  + beerId.toString());
	}
}
