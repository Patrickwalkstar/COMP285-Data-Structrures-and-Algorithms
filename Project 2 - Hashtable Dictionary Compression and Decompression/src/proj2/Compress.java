//package TestFiles;
package proj2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class Compress extends Decompress {
public static void main(String args[]) {
int tableSize = 11;
HashTable hashTable = new HashTable(tableSize);
hashTable.initTable();
String fileName = args[0];
String line = null;
String compressedContent = "";
try {
FileReader fileReader = new FileReader(fileName);
BufferedReader bufferedReader = new BufferedReader (fileReader);
while((line = bufferedReader.readLine()) != null) {
int p = 0;
int q = 1;
while (q <=line.length()) {
while (q != line.length() && hashTable.searchByKey(line.substring(p, q)) != -1) {
q++;
}
short code = hashTable.searchByKey(line.substring(p, q - 1));
p = q -1;
compressedContent += Short.toString(code) + " ";
String newEntry = Short.toString(code) + line.charAt(q-1);
hashTable.insert(newEntry);
q++;
}
short code = hashTable.searchByKey(line.substring(p,q - 1));
compressedContent += Short.toString(code) + " ";
String newEntry = Short.toString(code) + line.charAt(line.length() - 1);
hashTable.insert(newEntry);
q++ ;
}
bufferedReader.close();
}
catch(FileNotFoundException ex) {
System.out.println("Cannot open file!" + fileName);
}
catch(IOException ex) {
System.out.println("Error!" + fileName);
}

 final File (fileName){
	 File newFile = new File(fileName);
	
	System.out.println("Compression of " + fileName);
	System.out.println("Compressed from " + newFile.length() + "Kilobytes to" + + "Kilobytes" );
	


	
}
}
}
