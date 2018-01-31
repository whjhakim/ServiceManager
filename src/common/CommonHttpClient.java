package common;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class CommonHttpClient {
	private URI uri;
	private CloseableHttpClient httpClient = HttpClients.custom().build();
	
	public CommonHttpClient(String url) {
		try {
			this.uri = new URI(new String(url).trim());
		}catch(URISyntaxException e) {			
		}
	}
	
	public String doGet() {
		try {
			HttpUriRequest httpRequest = RequestBuilder.get().setUri(this.uri)
					.addHeader("Content-Type", "application/json").build();
			CloseableHttpResponse response = httpClient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			byte[] data = EntityUtils.toByteArray(entity);
			String dataResponse = new String(data);
			return dataResponse;
		}catch(IOException e) {
			return "error";
		}
	}

	public String doPost(String body) {
		try {
			HttpUriRequest httpRequest = RequestBuilder.post().setUri(this.uri)
					.addHeader("Content-Type", "application/json")
					.setEntity(new StringEntity(body,ContentType.APPLICATION_JSON)).build();
			CloseableHttpResponse response = httpClient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			byte[] data = EntityUtils.toByteArray(entity);
			String dataResponse = new String(data);
			return dataResponse;
		}catch(IOException e) {
			return "error";
		}
	}
}
