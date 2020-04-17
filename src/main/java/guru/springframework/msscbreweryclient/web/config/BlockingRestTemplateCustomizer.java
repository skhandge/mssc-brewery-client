package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer{

	private final Integer maxTotalConnections;
	private final Integer defaultMaxTotalConnections;
	private final Integer connectionRequestTimout;
	private final Integer socketTimout;
	
	public BlockingRestTemplateCustomizer(@Value("${sfg.maxtotalconnections}") Integer maxTotalConnections, 
			@Value("${sfg.defaultmaxtotalconnections}")Integer defaultMaxTotalConnections,
			@Value("${sfg.connectionreuesttimeout}")Integer connectionRequestTimout, 
			@Value("${sfg.sockettimeout}")Integer socketTimout) {

		this.maxTotalConnections = maxTotalConnections;
		this.defaultMaxTotalConnections = defaultMaxTotalConnections;
		this.connectionRequestTimout = connectionRequestTimout;
		this.socketTimout = socketTimout;
	}
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		PoolingHttpClientConnectionManager connectionManger = new PoolingHttpClientConnectionManager();
		connectionManger.setMaxTotal(maxTotalConnections);
		connectionManger.setDefaultMaxPerRoute(defaultMaxTotalConnections);
		
		RequestConfig requestConfig = RequestConfig
				.custom()
				.setConnectionRequestTimeout(connectionRequestTimout)
				.setSocketTimeout(socketTimout)
				.build();
		
		CloseableHttpClient httpClient = HttpClients
				.custom()
				.setConnectionManager(connectionManger)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setDefaultRequestConfig(requestConfig)
				.build();
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}
	@Override
	public void customize(RestTemplate restTemplate) {
		// TODO Auto-generated method stub
		restTemplate.setRequestFactory(this.clientHttpRequestFactory());
		
	}

}
