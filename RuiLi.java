public class RuiLi {
    public static void main(String args[]){
	Globals.availableList = new AvailableList();
	//1
	int error = FileIO.openMessagesFile(Globals.MESSAGES_FILE);
	Message message = new Message();
	if(error == Globals.PROCESS_OK){
	    //2
	    String text = "";
	    for(int i = 0; i < Globals.RECORD_DATA_LEN; i++)text+=1;
	    message.setText(text);
	    int firstMessageLoc = message.writeToMessagesFile();
	    text = "";
	    for(int i = 0; i < Globals.RECORD_DATA_LEN*2; i++)text+=2;
	    message.setText(text);
	    message.writeToMessagesFile();
	    
	    message.deleteFromMessagesFile(firstMessageLoc);
	    
	    //3
	    text = "";
	    for(int i = 0; i < 90; i++)text+=3;
	    message.setText(text);
	    message.writeToMessagesFile();
	    
	    FileIO.closeMessagesFile();
	}
    }
}
