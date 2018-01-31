public class AvailableList {

    //This is used for the next available record spot

    private Available head;
    private Available tail;
    public AvailableList(){
	head = null;
	tail = null;
    }
    public AvailableList(Available h, Available t){
	head = h;
	tail = t;
    }
    public void addRecord(int recordNumber){
	if(head == null){
	    head = tail = new Available(recordNumber);
	} else {
	    tail.setNext(new Available(recordNumber));
	    tail = tail.getNext();
	}
    }
    public int getNextRecord(){
	if(head == null)
	    return Globals.EMPTY_AVAILABLE_LIST;
	int ret = head.getRecordNumber();
	head = head.getNext();
	tail = (head == null)?null:tail;
	return ret;
    }
    public Available getHead(){
	return head;
    }
    public Available getTail(){
	return tail;
    }
    public void setHead(Available h){
	head = h;
    }
    public void setTail(Available t){
	tail = t;
    }
    public String toString(){
	String ret = "";
	Available it = head;
	while(it != null){
	    ret += it + "\n";
	    it = it.getNext();
	}
	return ret;
    }
}
