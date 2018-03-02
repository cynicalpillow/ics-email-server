public class Message {
    private String text = null;
    private String subject = null;
    private String sender = null;
    private String receiver = null;
    private String dateTime = null;
    private char command;
    private char marker;
    private char eosMarker;
    public Message(){
	text = null;
    }
    public Message(String text){
	setText(text);
    }
    public void readFromMessagesFile(int recordNumber){
	String data = "";
	Record record = new Record();
	do {
	    record.readFromMessagesFile(recordNumber);
	    data += record.getData();
	    recordNumber = record.getNext();
	} while(recordNumber != Globals.END_OF_MESSAGE);
	setMessage(data);
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
	return "Command     : " + command + "\n" +
	       "Sender      : " + sender + "\n" +
	       "Receiver    : " + receiver + "\n" +
	       "Date/Time   : " + dateTime + "\n" +
	       "Marker      : " + marker + "\n" +
	       "Subject     : " + subject + "\n" +
	       "EOS Marker  : " + eosMarker + "\n" +
	       "Message text: " + text;
    }
    public void setText(String text){
	this.text = text;
    }
    
    public void setMessage(String s){
	command = s.charAt(Globals.COMMAND_POS);
	sender = s.substring(Globals.SENDER_POS, Globals.SENDER_POS+Globals.SENDER_LEN);
	receiver = s.substring(Globals.RECEIVER_POS, Globals.RECEIVER_POS+Globals.RECEIVER_LEN);
	dateTime = s.substring(Globals.DATE_TIME_POS, Globals.FIRST_RECORD_MARKER_POS);
	marker = s.charAt(Globals.FIRST_RECORD_MARKER_POS);
	subject = s.substring(Globals.FIRST_RECORD_MARKER_POS+1, s.indexOf(Globals.END_OF_SUBJECT_MARKER));
	eosMarker = s.charAt(s.indexOf(Globals.END_OF_SUBJECT_MARKER));
	text = s.substring(s.indexOf(Globals.END_OF_SUBJECT_MARKER)+1);
    }
    public String getMessage(){
	return command + sender + receiver + dateTime + marker + subject + eosMarker + text;
    }
    public char getCommand(){
	return command;
    }
    public String getSender(){
	return sender;
    }
    public String getReceiver(){
	return receiver;
    }
    public String getDateTime(){
	return dateTime;
    }
    public char getMarker(){
	return marker;
    }
    public String getSubject(){
	return subject;
    }
    public char getEOSMarker(){
	return eosMarker;
    }
    public String getText(){
	return text;
    }
    public String getIdSenderFirst(){
	return sender + receiver + dateTime;
    }
    public String getIdReceiverFirst(){
	return receiver + sender + dateTime;
    }
    
    public static void main(String args[]){
	System.out.print(new TNode("034553", 76, null ,null ,null));
    }
}
