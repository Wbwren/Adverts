package com.ait.adverts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
*	Password encrption test
* 
*/
class EncryptionTest {
	/**
	* 	Access for encryption class
	*/
	private static Encryption encryption;
	/**
	* 	Constant for password
	*/
	private static final String PASSWORD = "scrumbledoresarmy";
	/**
	* 	Constant for salt value
	*/
	private static final String SALTVALUE = "gY'4=*J[X?C}p_'}yelMA80+k[}wPd";
	/**
	* 	Constant for hash value
	*/
	private static final String HASHVALUE = "UD2ISqPh92ooMMFvVPKITvDKYWiOEtc9I/XcDSjGR8vQovq0oEzCbj6T910pVS+mvMcXe4RHWnlXg7zo7jKHIg==";
	/**
	* 	Constant for encryption algorithm used
	*/
	private static final String ENCRYPTION_ALGORITHM = "NoSuchAlgorithm";
	
	/**
	* 	Common code for each test
	*/
	@BeforeEach
	public void setUp() {
		encryption = new Encryption();
	}
	
	@Test
	/* default */ void testClassConstantsCharacterSet() {
		assertEquals("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~", encryption.getCharacters(), "Character set not equal to encrption character set");
	}
	
	@Test
	/* default */ void testClassConstantsIterations() {
		assertEquals(10_000, Encryption.getIterations(), "Number of iterations matches");
	}
	
	@Test
	/* default */ void testClassConstantsKeyLength() {
		assertEquals(512, Encryption.getKeylength(), "Key length not the same");
	}

	@Test
	/* default */ void testCreateEncryptedPassword() {
		final String encryptedPassword = encryption.createEncryptedPassword(PASSWORD);
		final String databaseEncryptedPassword = HASHVALUE;
		assertEquals(databaseEncryptedPassword, encryptedPassword, "Password not the same");
	}

	@Test
	/* default */ void testGenerateSaltValue() {
		final String salt = encryption.generateSaltValue(30);
		assertEquals(30, salt.length(), "Salt length not set");
	}
	
	@Test
	/* default */ void testUniqueSaltValue() {
		final String salt01 = encryption.generateSaltValue(30);
		final String salt02 = encryption.generateSaltValue(30);
		final boolean bUniqueSaltValue = salt01.equals(salt02);
		assertFalse(bUniqueSaltValue, "Not Unique salt value");
	}
	
	@Test
	/* default */ void testGenerateHashValue() {
		final String encryptedPassword = encryption.generateEncryptedPassword(PASSWORD, SALTVALUE);
		final String databaseEncryptedPassword = HASHVALUE;
		assertEquals(databaseEncryptedPassword, encryptedPassword, "Password not the same from hash value");
	}
	
	@Test
	/* default */ void testVerifyuserPassword() {
		final String encryptedPassword = encryption.generateEncryptedPassword(PASSWORD, SALTVALUE);
		final boolean bPasswordMatch = encryption.verifyUserPassword(PASSWORD, encryptedPassword, SALTVALUE);
		assertTrue(bPasswordMatch, "Password do not match");
	}
	
	@Test
	/* default */ void testNotVerifyuserPassword() {
		final String newPassword = PASSWORD + "wrongpassword";
		final String encryptedPassword = encryption.generateEncryptedPassword(newPassword, SALTVALUE);
		final boolean bPasswordMatch = encryption.verifyUserPassword(PASSWORD, encryptedPassword, SALTVALUE);
		assertFalse(bPasswordMatch, "Password does match");
	}
	
	@Test
	/* default */ void testGetSetEncryptionAlgorithm() {
		final String newEncryptionAlgorithm = ENCRYPTION_ALGORITHM;
		encryption.setEncryptionAlgorithm(newEncryptionAlgorithm);
		final String encryptionAlgorithm = encryption.getEncryptionAlgorithm();
		assertEquals(newEncryptionAlgorithm, encryptionAlgorithm, "Encryption algorithm not set");
	}
	
	@Test
	/* default */ void testAssertionError() {
		encryption.setEncryptionAlgorithm("NoSuchAlgorithm");
		final AssertionError thrown = Assertions.assertThrows(AssertionError.class, ()-> {
			encryption.generateEncryptedPassword(PASSWORD, SALTVALUE);
		});
		
		assertEquals("Error hashing password: NoSuchAlgorithm SecretKeyFactory not available", thrown.getMessage(), "Error Message Thrown");
	}
}

