
import java.math.*;
import java.util.Random;
import java.util.Base64;

public class Encrypcyion implements Encryption{

	/**
	 * Encrpyt will take in a message and return the message encrypted 
	 */
	
	public String encrypt (String message) {
		//Generate a random 5 digit number to act as an auto-generated key
		int randomSeed = (int)(10000 + Math.random()*90000);
		//instantiate a new 'Random' using that number as its seed
		Random r = new Random(randomSeed);
		//Generate an int from the pseudorandom 'Random' instance 'r'
		int shift = r.nextInt(64) + 1;
		
		//System.out.println("Word is " + message + " and Shift is " + shift);
		//make a String from a base64 conversion of 'message' //removed after debug complete, creates straight to char[]
		//String encoded = Base64.getEncoder().encodeToString(message.getBytes());
		//System.out.println("First encoding is " + encoded);
		//create a char[] of the characters from a base64 conversion of 'message'
		char[] chars = Base64.getEncoder().encodeToString(message.getBytes()).toCharArray();
		
		//System.out.println("Original char array was:");
		//System.out.println(chars);
		
		//loop through all characters
		for (int i=0; i<chars.length; i++) {
			//System.out.println(chars[i]);
			//check if character is a padding character (base64 uses padding stuff)
			if (i<chars.length -2 || chars[i] != '=') {
				//if shifting the character up the random 'shift' value would push it past the last
				//printable, non-whitspace character, loop the character to the other side of the alphabet instead
				if (chars[i] + shift > '~') {
					chars[i] = (char)('!' + (chars[i] + shift - '~'));
					//System.out.println("Looped on this shift");
				}
				//otherwise, just shift it up
				else {
					chars[i] = (char)(chars[i] + shift);
				}
			}
			//System.out.println(chars[i]);
		}

		//System.out.println("New char array:");
		//System.out.println(chars);
		//make a new String (!! not a .toString(), that screws things up) with the shifted characters //removed after debug complete, creates straight to a base64 String
		//String shifted = new String(chars);
		//System.out.println(test);
		//String reEncoded = Base64.getEncoder().encodeToString(shifted.getBytes());
		
		//create a String 'reEncoded' that encodes the character array again as base64, to provide a layer of abstraction and print using the base64 charset
		String reEncoded = Base64.getEncoder().encodeToString((new String(chars)).getBytes());
		//create a string 'withKey' that adds the 5 digit randomSeed to the beginning of the final encoded string
		String withKey = randomSeed + reEncoded;
		
		//System.out.println("Final encoding is " + reEncoded);
		
		//spit it out! We did it, it's very obfuscated
		return withKey;
	}

	/**
	 * Decrypt will take in an encrypted message and return the message decoded
	 */
	
	public String decrypt (String encryptedMessage) {
		//parse the first 5 digits from 'encryptedMessage', aka the key to decoding it
		int seed = 0;
		//try-catch to make sure that the 'encryptedMessage' is in a format that could have been output by 'encrypt()'
		try { seed = Integer.parseInt(String.valueOf(encryptedMessage.substring(0, 5))); }
		catch (NumberFormatException e) { return "You can't decrypt that string, silly, it isn't formatted correctly"; }
		
		//chop those digits from the 'encryptedMessage' so they don't get in the way of decrpyting
		encryptedMessage = encryptedMessage.substring(5);
		
		//instantiate a new 'Random'
		Random r = new Random(seed);
		int shift = r.nextInt(64) + 1;
		//get the same random number that was used to encrypt, works because of pseudorandomness
		
		//System.out.println("Word is " + encryptedMessage + " and Shift is " + shift);
		
		//turn the 'encryptedMessage' into a byte array, and then into a char array
		//byte[] decode = Base64.getDecoder().decode(encryptedMessage.getBytes());
		//String trueDecode = new String(decode);
		//char[] chars = (new String(decode)).toCharArray();
		char[] chars = (new String(Base64.getDecoder().decode(encryptedMessage.getBytes())).toCharArray());
		
		//System.out.println("First decoding is " + trueDecode);
		//System.out.println(chars);
		
		//loop through all characters
		for (int i=0; i<chars.length; i++) {
			//check to not override padding
			if (i<chars.length -2 || chars[i] != '=') {
				//if reverse shifting by 'shift' pushes the character below '!', the first printable, non-whitespace
				//character, loop around from the top of the alphabet instead, aka from '~'
				if (chars[i] - shift <= '!') {
					chars[i] = (char)('~' - ('!' - (chars[i] - shift)));
				}
				//otherwise, just shift it down
				else {
					chars[i] = (char)(chars[i] - shift);
				}
			}
		}
		
		
		//block below removed after debug in favor of one-line creation
		
		//a string of the back-shifted characters
		//String back = new String(chars);
		//System.out.println("Back-shifted chars is:");
		//System.out.println(back);
		
		//decode the bytes of 'back' through base64 decoding
		//byte[] decoded = Base64.getDecoder().decode(back.getBytes());
		//turn those bytes into a string (!! Again, not a .toString(), that just kills it)
		//String out = new String(decoded);
		//System.out.println("Ends at " + last);
		
		
		String decoded = new String(Base64.getDecoder().decode((new String(chars)).getBytes()));
		return decoded;
	}

	/**
	 * This will return some info of the encryption method - it should include your names and if you want a hint or teaser
	 */
	public String encryptionInfo() {
		return "Good luck\n(the secret is in the digits)";
	}
	
	
	public static void main(String[] args) {

		// TODO Auto-generated method stub
		Encryption theEncryption=new Encrypcyion();
		System.out.println(theEncryption.encryptionInfo());    
		String originalWord="foo blah AAA 12345678900 Yeah";
		String encrypt=theEncryption.encrypt(originalWord);
		System.out.println("Encrypting "+originalWord + " is " + encrypt);
		String decrypt=theEncryption.decrypt(encrypt);
		System.out.println("Decrypted is " + decrypt);
		if (!originalWord.equals(decrypt))
			System.out.println("THIS ENCRYPTION DOESNT WORK");
		else
			System.out.println("WORKS");
		System.out.println();
		String randomPhrases[]={"sand","confuse","brown123","The bitter stretch preserves the danger.","Cathey Perkins","423 Bombs","There are 219 submarines","207 200 1551"};
		for (int i=0;i<randomPhrases.length;i++) 
		    {           
		        String text=randomPhrases[i];
		        String en=theEncryption.encrypt(text);
		        String dc=theEncryption.decrypt(en);
		        if (!dc.equals(text))
		            dc="ERROR:"+dc;
		        System.out.println(dc +"    :   "+ en);
		    }
		
	}
}
