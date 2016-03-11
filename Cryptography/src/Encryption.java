public interface Encryption {
	/**
	 * Encrpyt will take in a message and return the message encrypted 
	 */
	public String encrypt (String message);
	
	/**
	 * Decrypt will take in an encrypted message and return the message decoded
	 */
	public String decrypt (String encryptedMessage);

	/**
	 * This will return some info of the encryption method - it should include your names and if you want a hint or teaser
	 */
	public String encryptionInfo();
}