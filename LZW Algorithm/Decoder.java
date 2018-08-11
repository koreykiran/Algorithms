import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;

/***
 * Program to decode the the file using the LZW decode algorithm 
 * 
 * @author Kiran Korey
 * Student ID: 801045301
 * Email:kkorey@uncc.edu
 *
 */
public class Decoder {

	/*Arraylist of Strings is used to keep the track of the table 
	 * As in decoding we access the string stored in the table
	 * based on the Integer(code) value here it will be the index of the array*/
	ArrayList<String> table;
	int MAX_TABLE_SIZE;
	String str = "";
	String newStr = "";

	/***
	 * Constructor
	 * @param bitLength
	 */
	public Decoder(int bitLength) {
		this.table = new ArrayList<String>();
		
		MAX_TABLE_SIZE = (int) Math.pow(2, bitLength); // Maximum size of the table is 2^bitLength passed by the user
		
		//initiating the arraylist with all the 256 characters
		for (int i = 0; i < 256; i++) {
			table.add(Character.toString((char) i));
		}
	}

	/***
	 * This method has main logic of LZW Decoding.
	 * @param code (Character to be decoded)
	 * @return Returns the decoded form of the passed character.
	 */
	private String decode(char code) {
		String toRet = null;
		if (code >= table.size()) {
			newStr = str + str.charAt(0);
		} else {

			newStr = table.get(code);
		}
		toRet = newStr;
		if (table.size() < MAX_TABLE_SIZE) {
			table.add(str + newStr.charAt(0));
		}
		str = newStr;
		return toRet;
	}

	/***
	 * This method reads each character of a file and calls decode method,
	 * receives the decoded character and writes it to a _decoded.txt file.
	 * @param filename
	 */
	private void readAndDecode(String fileName) {
		int i;
		String toWrite;
		Writer writer = null;
		BufferedReader reader = null;
		try {
			//the file read handle is opened in UTF16BE encode format
			reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(fileName), Charset.forName("UTF-16BE")));
			writer = new OutputStreamWriter(
					new FileOutputStream(fileName.substring(0, fileName.lastIndexOf(".")) + "_decoded.txt"));
			
			if((i = reader.read()) != -1) {
				str = table.get(i);
				writer.write(str);
			}
			/*Read each character and pass it to decode method, receive the decoded form of the character
			and write it to the file*/
			while ((i = reader.read()) != -1) {
				toWrite = this.decode((char) i);
				if (toWrite != null) {
					writer.write(toWrite);
				}
			}
		} catch (IOException e) {
			System.out.println("Exception occured while decoding. Msg :"+e.getMessage() );
			//e.printStackTrace();
		} finally {
			try {
				//Close the open file handlers
				reader.close();
				writer.close();
			} catch (IOException e) {
				System.out.println("Exception while closing the file hadlers. Msg: " + e.getMessage());
				//e.printStackTrace();
			}
		}
	}

	/***
	 * Main method of the Decoder class.
	 * Accepts 2arguments : Filename and Bit length
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println(
					"Invalid arguments. The first argument should be the path of the "
					+ "file which has to be decoded and the 2nd argument should be the N-bit representation");
		} else if (Integer.parseInt(args[1]) > 16) {
			System.out.println("Invalid 2nd argument. The maximum allowed N bit allowed is 16");
		} else {
			/*
			 * If the correct arguments are passed then the instance of the Decoder class is
			 * created by passing the file name with path and the N bit representation
			 */
			Decoder decoder = new Decoder(Integer.parseInt(args[1]));
			decoder.readAndDecode(args[0]);
			System.out.println("File is Decoded Successfully please check "+args[0]+"_decoded.txt file");
		}
	}

}
