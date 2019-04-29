package cloud.bestpractice.kms.huaweicloud;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cloud.apigateway.sdk.utils.Client;
import com.cloud.apigateway.sdk.utils.Request;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KMSUtils {
	public static CommonResponse createDataKey(String ak, String sk, String endpoint, String projectId, String keyId) throws Exception {
		String url = endpoint + "/v1.0/" + projectId + "/kms/create-datakey";
		Map<String, String> data = new HashMap<String, String>();
		data.put("key_id", keyId);
		data.put("datakey_length", "512");  //Why only 512 bits allowed? AES dont support key length large than 256 bits, bug?
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(data);
		CommonResponse response = sendRequest(ak, sk, url, "POST", body);
		return response;
		
	}
	
	public static CommonResponse encryptDataKey(String ak, String sk, String endpoint, String projectId, String keyId, String plainKey) throws Exception {
		String url = endpoint + "/v1.0/" + projectId + "/kms/encrypt-datakey";
		Map<String, String> data = new HashMap<String, String>();
		data.put("key_id", keyId);
		data.put("plain_text", plainKey);
		data.put("datakey_plain_length", "64");
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(data);
		CommonResponse response = sendRequest(ak, sk, url, "POST", body);
		return response;
	}
	
	public static CommonResponse decryptDataKey(String ak, String sk, String endpoint, String projectId, String keyId, String encryptedKey) throws Exception {
		String url = endpoint + "/v1.0/" + projectId + "/kms/decrypt-datakey";
		Map<String, String> data = new HashMap<String, String>();
		data.put("key_id", keyId);
		data.put("cipher_text", encryptedKey);
		data.put("datakey_cipher_length", "64");
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(data);
		CommonResponse response = sendRequest(ak, sk, url, "POST", body);	
		return response;
}
	
	
    public static CommonResponse sendRequest(String ak, String sk, String url, String method, String bodyJson) throws Exception {
        //Create a new request.
        Request request = new Request();
        //Set the request parameters.
        //AppKey, AppSecrect, Method and Url are required parameters.
        request.setAppKey(ak);
        request.setAppSecrect(sk);
        request.setMethod(method);
        request.setUrl(url);
//            request.addQueryStringParam("name", "value");
        request.addHeader("Content-Type", "application/json");
        //if it was published in other envs(except for Release),you need to add the information x-stage and the value is env's name
        //request.addHeader("x-stage", "publish_env_name");
        request.setBody(bodyJson);

        
        CloseableHttpClient client = null;
        try {
            //Sign the request.
            HttpRequestBase signedRequest = Client.sign(request);
            for (Header h : signedRequest.getAllHeaders()) {
                System.out.println(h.getName() + ":" + h.getValue());
            }
            System.out.println("------------");
            //Send the request.
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).useTLS().build();
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, new AllowAllHostnameVerifier());
            client = HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build();
            HttpResponse response = client.execute(signedRequest);
            CommonResponse response2 = new CommonResponse();
            response2.setStausCode(response.getStatusLine().getStatusCode());
            response2.setContent(EntityUtils.toString(response.getEntity(), "UTF-8"));
            return response2;
            
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
