//package TestFiles;
package proj2;
import java.io.*;
import java.util.Scanner;

import org.w3c.dom.Text;
public class Decompress{
	public static void main(String [] args) {
	Scanner input = new Scanner(System.in);
	DataInputStream bin_in_stream = null;
	PrintWriter text_out = null;
	boolean valid_file = false;
	boolean get_file_from_user = false;
	String bin_in = "";
	
	//Check arguments
	if(args.length > 0) {
		bin_in = args[0];
	}else {
		get_file_from_user = true;
	}
	while (!valid_file) {
		//Get actual file from user
		if(get_file_from_user) {
			System.out.print("enter a file to decompress:");
			bin_in = input.next();
		}
		
		//Try and open a data output stream
		
		try {
			bin_in_stream = new DataInputStream(new FileInputStream(new File( (bin_in + " .zzz"))));
			valid_file = true;
		} catch (Exception e) {
			System.out.println("could not open file:" + bin_in + " .zzz , re-enter file.");
			get_file_from_user = true;
		}
	}
	
	try {
		text_out = new PrintWriter(new File(bin_in));
	} catch (Exception e) {
		System.out.println("Could not create a new file to output to.");
		System.exit(-1);
	}
	
	//Decompression
	
	double decompression_time = 0;
	int num_table_resizes = 0;
	boolean reading_file = true;
	
	//Initialize the dictionary
	
	Dictionary dictionary = new Dictionary();
	dictionary.initializeDictionary();
	
	//Output the first character
	
	int first_char = -1;
	try {
		first_char = bin_in_stream.readInt();
		text_out.print(dictionary.getKey(first_char));
	}catch (Exception e) {
		System.out.println("Could not print the first character in the file);" + bin_in + " .zzz");
		System.exit(-1);
	}
//Read the file
	String temp_key = null;
	int p, q, i;
	q = p = 0;
	i = 0;
	q = first_char;
	long start_time = System.nanoTime();
	while(reading_file) {
		//read the next two numbers
		try {
			if (i != 0) {
				q = bin_in_stream.readInt();
			} else {
				i = 1;
			}
			p = bin_in_stream.readInt();
		}catch (Exception e) {
			reading_file = false;
			break;
		}
		//Use p and q
		if ((temp_key = dictionary.getKey(p)) != null) {
			
			//if p is in our dictionary
			System.out.println( "Q:" + q  + "P:" + p);
			text_out.print((char)p);
			dictionary.add( new DictionaryNode( dictionary.getKey(q) + dictionary.getKey(p).charAt(0), dictionary.getNextValue(), null));
		}else { 
			//if p is not in our dictionary
			System.out.println( "Q:" + q  + "P:" + p);
			text_out.print(dictionary.getKey(q) + dictionary.getKey(q).charAt(0));
			dictionary.add( new DictionaryNode( dictionary.getKey(q) + dictionary.getKey(p).charAt(0), p, null));
		}
	}
	long compression_time = System.nanoTime() - start_time;
	
	//close the files
	
	try {
		bin_in_stream.close();
		text_out.close();
	} catch (IOException e) {
		System.out.println("Could not close the readers/writers");
		System.exit(-1);
	}
	//Log file
	num_table_resizes = dictionary.getNumberRehashes();
	}
}