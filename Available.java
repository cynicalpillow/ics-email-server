public class Available {
    private Available next;
    private int recordNumber;
    public Available(){
	next = null;
	recordNumber = -1;
    }
    public Available(int n){
	recordNumber = n;
	next = null;
    }
    public int getRecordNumber(){
	return recordNumber;
    }
    public void setRecordNumber(int n){
	recordNumber = n;
    }
    public Available getNext(){
	return next;
    }
    public void setNext(Available n){
	next = n;
    }
    public String toString(){
	return ""+recordNumber;
    }
}
