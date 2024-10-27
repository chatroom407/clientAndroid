import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.Security
import org.bouncycastle.jce.provider.BouncyCastleProvider

object KeyGenerator {
    fun generateKeyPair(): KeyPair {
        // Dodanie Bouncy Castle jako dostawcy
        Security.addProvider(BouncyCastleProvider())

        val keyPairGenerator: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        return keyPairGenerator.generateKeyPair()
    }
}