package software.DataBase;

import java.math.BigInteger;
import java.util.Random;

public class Security {

    public static Object[] generateElGamalKey() {
        return ElGamal.generateKeys(1991);
    }

    public static Object[] elGamalEncrypt(String message, Object[] keys) {
        return ElGamal.encrypt(message.getBytes(), (Object[]) keys[0]);
    }

    public static String elGamalDecrypt(Object[] encryptedMessage, Object[] keys) {
        byte[] decryptedMessage = ElGamal.decrypt(encryptedMessage, (Object[]) keys[0], (BigInteger) keys[1]);
        return new String(decryptedMessage);
    }

    public static String expoModEncrypt(String message) {
        BigInteger n = BigInteger.valueOf(3233);
        BigInteger publicKey = BigInteger.valueOf(17);
        return ExpoModulaire.encrypt(message, publicKey, n);
    }

    public static String expoModDecrypt(String encryptedMessage) {
        BigInteger n = BigInteger.valueOf(3233);
        BigInteger privateKey = BigInteger.valueOf(2753);
        return ExpoModulaire.decrypt(encryptedMessage, privateKey, n);
    }

    public static class ElGamal {

        /**
         * Generates public and private keys for the ElGamal encryption scheme.
         *
         * @param keyLength The desired bit length of the prime number 'p'.
         * @return An array containing public and private keys.
         */
        public static Object[] generateKeys(int keyLength) {
            Random random = new Random();
            BigInteger p = BigInteger.probablePrime(keyLength, random);
            BigInteger g = new BigInteger(keyLength, random).mod(p.subtract(BigInteger.valueOf(2))).add(BigInteger.valueOf(2));
            BigInteger a = new BigInteger(keyLength - 1, random);   // Generate a random private key 'a'
            BigInteger A = g.modPow(a, p);  // Calculate the corresponding public key 'A'
            Object[] publicKey = {p, g, A};

            return new Object[]{publicKey, a};
        }

        /**
         * Encrypts a message using the ElGamal encryption scheme.
         *
         * @param message   The message to be encrypted as an array of bytes.
         * @param publicKey An array containing the public key components {p, g, A}.
         * @return An array containing the encrypted message components {B, c}.
         */
        public static Object[] encrypt(byte[] message, Object[] publicKey) {
            BigInteger p = (BigInteger) publicKey[0];
            BigInteger g = (BigInteger) publicKey[1];
            BigInteger A = (BigInteger) publicKey[2];
            BigInteger b = new BigInteger(p.bitLength() - 1, new Random());
            while (b.compareTo(BigInteger.ONE) <= 0 || b.compareTo(p.subtract(BigInteger.ONE)) >= 0) {
                b = new BigInteger(p.bitLength() - 1, new Random());
            }

            BigInteger B = g.modPow(b, p);
            BigInteger c = A.modPow(b, p).multiply(new BigInteger(message)).mod(p);
            return new Object[]{B, c};
        }

        /**
         * Decrypts an ElGamal cryptogram to retrieve the original message.
         *
         * @param cryptogram  An array containing the components of the encrypted message {B, c}.
         * @param publicKey   An array containing the public key components {p, g, A}.
         * @param privateKey  The private key 'a'.
         * @return The decrypted message as an array of bytes.
         */
        public static byte[] decrypt(Object[] cryptogram, Object[] publicKey, BigInteger privateKey) {
            BigInteger p = (BigInteger) publicKey[0];
            BigInteger B = (BigInteger) cryptogram[0];
            BigInteger c = (BigInteger) cryptogram[1];
            BigInteger A = B.modPow(privateKey, p);
            BigInteger inverseA = A.modInverse(p);  // Calculate the multiplicative inverse of 'A' modulo 'p'

            return c.multiply(inverseA).mod(p).toByteArray();   // Retrieve the original message by multiplying 'c' with the inverse of 'A' and taking the result modulo 'p'
        }
    }

    public static class ExpoModulaire {

        /**
         * Encrypts a message using the Exponential Modular Encryption scheme.
         *
         * @param message    The message to be encrypted as a string.
         * @param publicKey  The public key component used for encryption.
         * @param n          The modulus value used for encryption.
         * @return The encrypted message as a string.
         */
        public static String encrypt(String message, BigInteger publicKey, BigInteger n) {
            StringBuilder cryptogram = new StringBuilder(); // Initialize a StringBuilder to store the encrypted message
            for (int i = 0; i < message.length(); i++) {
                char c = message.charAt(i);
                BigInteger m = BigInteger.valueOf((int) c); // Convert the character to its ASCII value and create a BigInteger
                BigInteger cryptedChar = m.modPow(publicKey, n);
                cryptogram.append(cryptedChar).append(" ");
            }
            return cryptogram.toString();
        }

        /**
         * Decrypts a message encrypted using the Exponential Modular Encryption scheme.
         *
         * @param cryptogram  The encrypted message as a string.
         * @param privateKey  The private key used for decryption.
         * @param n           The modulus value used for encryption/decryption.
         * @return The decrypted message as a string.
         */
        public static String decrypt(String cryptogram, BigInteger privateKey, BigInteger n) {
            StringBuilder message = new StringBuilder();
            String[] cryptedChars = cryptogram.split(" ");
            for (String cryptedChar : cryptedChars) {
                BigInteger c = new BigInteger(cryptedChar);
                BigInteger decryptedChar = c.modPow(privateKey, n);
                int asciiCode = decryptedChar.intValue();   // Convert the decrypted ASCII code to a character
                char c1 = (char) asciiCode;
                message.append(c1);
            }
            return message.toString();
        }
    }
}
