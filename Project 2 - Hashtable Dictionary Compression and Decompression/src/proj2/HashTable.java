package proj2;

public class HashTable {
private int num_rehashes;
private int hash_size;
private int num_elements;
private DictionaryNode[] hash_table;
private double percent_full;
public HashTable() {
this(130*2);
}
public HashTable(int hash_table_size) {
hash_size = nextPrime(hash_table_size);
num_elements = 0;
num_rehashes = 0;
hash_table = new DictionaryNode[hash_size];
percent_full = 0;
}
public void add(String key, int value) {
int hash_index = hash(value);
System.out.println(value + "hashed to " + hash_index);
if (hash_table[hash_index] == null) {
/*Found an open spot, create new node*/
hash_table[hash_index] = new DictionaryNode(key, value, null);
} else {
DictionaryNode curr = hash_table[hash_index];
hash_table[hash_index] = new DictionaryNode(key, value, curr);
curr = null;
}
num_elements++;
updateFull();
checkRehash();
}
public int getNumRehashes() {
return num_rehashes;
}
public int getHashSize() {
return hash_size;
}

}
