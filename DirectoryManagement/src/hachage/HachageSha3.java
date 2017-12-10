package hachage;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

public class HachageSha3 {

	/**
	 * Hachage d'une chaine de carractère en utilisant Sha3 
	 * @param input la chaine à hacher
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return le haché du paramètre au format String
	 */
	public static String digest(String input) {
		SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
	    byte[] digest = digestSHA3.digest(input.getBytes());
	    return Hex.toHexString(digest);
	}
}
