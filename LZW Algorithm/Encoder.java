import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Hashtable;

/***
 * Program to encode the the file using the LZW encode algorithm 
 * 
 * @author Kiran Korey
 * Student ID: 801045301
 * Email:kkorey@uncc.edu
 *
 */
public class Encoder {

	/*Hashtable is used to keep the track of the table with String being the 
	key and Integer being the value as in encoding we access the integer value (code)
	based on the string*/
	
	Hashtable<String, Integer> table;
	int MAX_TABLE_SIZE; 
	String str = "";
	String symbol = "";

	public Encoder(int bitLength) {
		this.table = new Hashtable<String, Integer>();
		MAX_TABLE_SIZE = (int) Math.pow(2, bitLength); // Maximum size of the table is 2^bitLength passed by the user

		//initiating the hash table with all the 256 characters with their ascii values
		for (int i = 0; i < 256; i++) {
			table.put(Character.toString((char) i), i);
		}
	}

	/***
	 * This method has main logic of LZW Encoding.
	 * @param ch (Character to be encoded)
	 * @return Returns the encoded form of the passed character.
	 *
	 */
	private int encode(char ch){
		symbol = ch + "";
		int toRet = -1;
		if (table.containsKey(str + symbol)) {
			str = str + symbol;
		} else {
			if(str!= null && str.length()>0) {
				toRet = table.get(str);
			}

			if (table.size() < MAX_TABLE_SIZE) {
				table.put(str + symbol, table.size());
			}

			str = symbol;
		}

		return toRet;
	}

	/***
	 * This method reads each character of a file and calls encode method,
	 * receives the encoded character and writes it to a .lzw file.
	 * @param filename
	 */
	private void readAndEncode(String filename) {
		int i;
		int toWrite;
		FileReader fr = null;
		Writer writer = null;
		try {
			fr = new FileReader(filename);
			//the file write handle is opened in UTF16BE encode format
			writer = new OutputStreamWriter(
					new FileOutputStream(filename.substring(0, filename.lastIndexOf(".")) + ".lzw"), "UTF-16BE");
			
			/*Read each character and pass it to encode method, receive the encoded form of the character
			and write it to the file*/
			while ((i = fr.read()) != -1) {
				toWrite = this.encode((char) i);
				if (toWrite != -1) {
					writer.write(toWrite);
				}
			}
			/*Write the last character of the input file in its encoded format to output file
			This case occurs if the last character of the input file is already in the table*/
			if(str != null && str.length()>0) {
				writer.write(table.get(str));
			}
		} catch (IOException e) {
			System.out.println("Exception occured while encoding. Msg :"+e.getMessage() );
			//e.printStackTrace();
		} finally {
			try {
				//Close the open file handlers
				fr.close();
				writer.close();
			} catch (IOException e) {
				System.out.println("Exception while closing the file hadlers. Msg: " + e.getMessage());
				//e.printStackTrace();
			}
		}
	}

	/***
	 * Main method of the Encoder class.
	 * Accepts 2arguments : Filename and Bit length
	 * @param args
	 */
	public static void main(String args[]) {

		if (args.length != 2) {
			System.out.println("Invalid arguments. The first argument should be the path of the"
					+ " file which has to be encoded and the 2nd argument should be the N-bit representation");
		} else if (Integer.parseInt(args[1]) > 16) {
			System.out.println("Invalid 2nd argument. The maximum allowed N bit allowed is 16");
		} else {
			/*
			 * If the correct arguments are passed then the instance of the Encoder class is
			 * created by passing the file name with path and the N bit representation
			 */
			Encoder encoder = new Encoder(Integer.parseInt(args[1]));
			encoder.readAndEncode(args[0]);
			System.out.println("File is Encoded Successfully please Use the "+args[0]+".lzw for decoding");
		}
	}
}
