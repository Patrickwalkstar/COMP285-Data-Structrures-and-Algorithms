package proj2;

public class DictionaryNode {
private String key;
private int value;
private DictionaryNode next;
public DictionaryNode() {
this(null, -1, null);
}
public DictionaryNode(String key, int value, DictionaryNode next) {
this.key = key;
this.value = value;
this.next = next;
}
public String getKey() {
return key;
}
public void setKey(String new_key) {
key = new_key;
}
public int getValue() {
return value;
}
public void setValue(int new_value) {
value = new_value;
}
public DictionaryNode getNext() {
return next;
}
public void setNext(DictionaryNode new_next) {
next = new_next;
}
}
