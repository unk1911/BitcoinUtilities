package com.edeliverables.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Casascius {
    private static Logger logger = LoggerFactory.getLogger(Casascius.class);

    /**
     * Convert from the mini private key format to the wallet import format (wif), as described in:
     *
     * https://en.bitcoin.it/wiki/Mini_private_key_format
     * and
     * https://en.bitcoin.it/wiki/Wallet_import_format
     *
     * Once the wif key is generated, it can be imported into the Bitcoin Core client by opening the debug window, and entering the command:
     *
     * walletpassphrase "mypassword" 120
     * importprivkey "5JPy8Zg7z4P7RSLsiqcqyeAF1935zjNUdMxcDeVrtU1oarrgnB7" "fromCasascius"
     *
     *
     * @param miniPrivateKey  30-character mini private key, starting with letter 'S'. Example:  S6c56bnXQiBjk9mqSYE7ykVQ7NzrRy
     * @return  51-character private key in wallet import format, starting with '5'.  Example: 5JPy8Zg7z4P7RSLsiqcqyeAF1935zjNUdMxcDeVrtU1oarrgnB7
     * @throws Exception
     */
    public static String miniToWif(String miniPrivateKey) throws Exception {
        byte[] s256 = Sha256Hash.hash(miniPrivateKey.getBytes("UTF-8"));
        byte[] prefix80 = hexStringToByteArray("80");
        byte[] combined = new byte[s256.length + prefix80.length];
        System.arraycopy(prefix80, 0, combined, 0, prefix80.length);
        System.arraycopy(s256, 0, combined, prefix80.length, s256.length);
        byte[] checksum = Sha256Hash.hashTwice(combined);
        byte[] privateKey = new byte[combined.length + 4];
        System.arraycopy(combined, 0, privateKey, 0, combined.length);
        System.arraycopy(checksum, 0, privateKey, combined.length, 4);
        String wif = Base58.encode(privateKey);
        return wif;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
