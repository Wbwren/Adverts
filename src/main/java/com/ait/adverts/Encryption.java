package com.ait.adverts;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;  
import java.security.SecureRandom;  
import java.security.spec.InvalidKeySpecException;  
import java.util.Arrays;  
import java.util.Base64;  
import java.util.Random;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.PBEKeySpec;
/*
 * Encryption algorithm for users' passwords
 * Creates a random salt value, and based upon that salt value, creates a hash value (encrypted password)
 * Both generated salt and hash value need to stored in the user database
 * Both values are needed to verify and match a user's password when logging in 
 */
public class Encryption implements Serializable {
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Random RANDOM = new SecureRandom();  
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
	private static final int ITERATIONS = 10000;  
    private static final int KEYLENGTH = 512;  
    
    private String encryptionAlgorithm = "PBKDF2WithHmacSHA1";
    
	Encryption () {
		
	}
	
	public String createEncryptedPassword(String userPassword) {
		
		String saltValue = "gY'4=*J[X?C}p_'}yelMA80+k[}wPd";
		return generateEncryptedPassword(userPassword, saltValue);
	}
    
    public String generateSaltValue(int length) {  
        
    	StringBuilder saltValue = new StringBuilder(length);  
  
        for (int i = 0; i < length; i++) {  
            saltValue.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));  
        }  

        return new String(saltValue);  
    }     
 
    public String generateEncryptedPassword(String password, String saltValue) {  
        
    	String encryptedPassword = null;
  
        byte[] securePassword = generateHashValue(password.toCharArray(), saltValue.getBytes());  
   
        encryptedPassword = Base64.getEncoder().encodeToString(securePassword);  
   
        return encryptedPassword;  
    }  
    
    public byte[] generateHashValue(char[] password, byte[] saltValue) {  
        
    	PBEKeySpec pBEKeySpec = new PBEKeySpec(password, saltValue, ITERATIONS, KEYLENGTH);  
        Arrays.fill(password, Character.MIN_VALUE);
        
        try {
        	SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(encryptionAlgorithm);  
        	return secretKeyFactory.generateSecret(pBEKeySpec).getEncoded();
        }   
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        	throw new AssertionError("Error hashing password: " + e.getMessage(), e);
        }   
        finally {  
        	pBEKeySpec.clearPassword();  
        }  
    } 
    
    public boolean verifyUserPassword(String password, String encryptedPassword, String saltValue) {  
        
    	boolean passwordMatch = false;
    	
        String newSecurePassword = generateEncryptedPassword(password, saltValue);  
        passwordMatch = newSecurePassword.equalsIgnoreCase(encryptedPassword);  
        
        if (Boolean.TRUE.equals(passwordMatch)) {
        	return true;
        }
        else {
        	return false;
        }
    } 
    
    public String getEncryptionAlgorithm() {
		return encryptionAlgorithm;
	}

	public void setEncryptionAlgorithm(String encryptionAlgorithm) {
		this.encryptionAlgorithm = encryptionAlgorithm;
	}

	public String getCharacters() {
		return CHARACTERS;
	}

	public static int getIterations() {
		return ITERATIONS;
	}

	public static int getKeylength() {
		return KEYLENGTH;
	}
}