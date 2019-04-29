package cloud.bestpractice.kms.huaweicloud;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
	
	// convert 128 hex characters to 64 byte (512 bit)
	public static byte[] HexString2Byte(String key) {
		int keyLen = key.length() / 2;
		byte[] keyBytes = new byte[keyLen];
		for (int i = 0; i < key.length(); i += 2) {
			keyBytes[i/2] = (byte) ((Character.digit(key.charAt(i), 16) << 4) + Character.digit(key.charAt(i + 1), 16));
		}

		return keyBytes;
	}

	public static String Byte2HexString(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
} 
	
	public static String encrypt(byte[] plainText, byte[] IV, String key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		// Get Cipher Instance
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		// Create SecretKeySpec
		SecretKeySpec keySpec = new SecretKeySpec(HexString2Byte(key), "AES");

		// Create IvParameterSpec
		IvParameterSpec ivSpec = new IvParameterSpec(IV);

		// Initialize Cipher for ENCRYPT_MODE
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

		// Perform Encryption
		byte[] cipherText = cipher.doFinal(plainText);

		return Byte2HexString(cipherText);
	}

	public static String decrypt(String encryptedText, byte[] IV, String key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		// Get Cipher Instance
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		// Create SecretKeySpec
		SecretKeySpec keySpec = new SecretKeySpec(HexString2Byte(key), "AES");

		// Create IvParameterSpec
		IvParameterSpec ivSpec = new IvParameterSpec(IV);

		// Initialize Cipher for DECRYPT_MODE
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		// Perform Decryption
		byte[] decryptedText = cipher.doFinal(HexString2Byte(encryptedText));

		return new String(decryptedText);
	}
}
