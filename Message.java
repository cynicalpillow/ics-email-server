public class Message {
    private String text = null;
    public Message(){
	text = null;
    }
    public void readFromMessagesFile(int recordNumber){
	String data = "";
	Record record = new Record();
	do {
	    record.readFromMessagesFile(recordNumber);
	    data += record.getData();
	    recordNumber = record.getNext();
	} while(recordNumber != Globals.END_OF_MESSAGE);
	text = data;
    }
    public void deleteFromMessagesFile(int recordNumber){
	Record record = new Record();
	while(recordNumber != Globals.END_OF_MESSAGE){
	    record.deleteFromMessagesFile(recordNumber);
	    recordNumber = record.getNext();
	}
    }
    public int writeToMessagesFile(){
	String text = this.text;
	int startRecordSpot = -1;
	int nextRecordNumber = -1;
	int recordNumber = -1;
	Record record;
	
	while(text != ""){
	    if(Globals.availableList.getHead() == null){
		recordNumber = Globals.totalRecordsInMessageFile;
		if(startRecordSpot == -1)startRecordSpot = recordNumber;
		if(text.length() <= Globals.RECORD_DATA_LEN){
		    record = new Record(text, Globals.END_OF_MESSAGE);
		    record.writeToMessagesFile(recordNumber, Globals.APPEND);
		    text = "";
		} else {
		    nextRecordNumber = recordNumber + 1;
		    record = new Record(text.substring(0, Globals.RECORD_DATA_LEN), nextRecordNumber);
		    record.writeToMessagesFile(recordNumber, Globals.APPEND);
		    text = text.substring(Globals.RECORD_DATA_LEN);
		}
	    } else {
		recordNumber = Globals.availableList.getNextRecord();
		if(startRecordSpot == -1)startRecordSpot = recordNumber;
		if(text.length() <= Globals.RECORD_DATA_LEN){
		    record = new Record(text, Globals.END_OF_MESSAGE);
		    record.writeToMessagesFile(recordNumber, Globals.MODIFY);
		    text = "";
		} else {
		    nextRecordNumber = (Globals.availableList.getHead() == null)?Globals.totalRecordsInMessageFile:recordNumber+1;
		    record = new Record(text.substring(0, Globals.RECORD_DATA_LEN), nextRecordNumber);
		    record.writeToMessagesFile(recordNumber, Globals.MODIFY);
		    text = text.substring(Globals.RECORD_DATA_LEN);
		}
	    }
	}
	return startRecordSpot;
    }
    public String toString(){
	return "Message text: " + text;
    }
    public void setText(String text){
	this.text = text;
    }
}
