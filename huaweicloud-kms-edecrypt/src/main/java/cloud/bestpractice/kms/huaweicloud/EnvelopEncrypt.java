package cloud.bestpractice.kms.huaweicloud;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EnvelopEncrypt {
	//create data key
	public static void main(String[] args) throws Exception {
		String ak = System.getenv("ak");
		String sk = System.getenv("sk");
		String endpoint = System.getenv("endpoint");
		String projectId = System.getenv("projectId");
		String keyId = System.getenv("keyid");
		
		//create the data key
		CommonResponse response = KMSUtils.createDataKey(ak, sk, endpoint, projectId, keyId);
		if (response.getStausCode() != 200) {
			System.out.println("create data key failure");
			return;
		}
		
		String data = response.getContent();
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String, String>> typer = new TypeReference<HashMap<String, String>>(){};
		Map<String, String> dataMap = mapper.readValue(data, typer);
		
		String plainKey = dataMap.get("plain_text");
		System.out.println("get plain key: " + plainKey);

//		String plainKey = "A5A17F17B5EF0D525872C59ECEB72948AF85E18427F8BE0D46545C979306C08D";
		
		//cat 1/2 of 512 bit to 256 bit, AES maximum key size is 256 bits.
		plainKey = plainKey.substring(0, plainKey.length()/2); 
		
		//prepare the Initial Vector for AES
		byte[] IV = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(IV);
		
		
		//encrypt text
		String text = "how a beautiful day!";
		
		System.out.println("origin text is: " + text);
		
		String encrypted = AESUtils.encrypt(text.getBytes(), IV, plainKey);
		
		System.out.println("encrypted text is: " + encrypted);
		
		//decrypt text
		
		String decrypted = AESUtils.decrypt(encrypted, IV, plainKey);
		System.out.println("decrypted text is: " + decrypted);
	}
}
