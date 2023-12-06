import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecretKeyGenerator {

    public static void main(String[] args) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            byte[] rawSecretKey = secretKey.getEncoded();
            String base64SecretKey = Base64.getEncoder().encodeToString(rawSecretKey);
            System.out.println("Base64 Encoded Secret Key: " + base64SecretKey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
