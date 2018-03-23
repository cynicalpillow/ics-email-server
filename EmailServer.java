public class EmailServer{
    public static void main(String args[]){
	int error = Init.initializeSystem();
	if(error == Globals.PROCESS_OK){
	    Message message = new Message();
	    int recordNumber = 0;
	    String identification = Globals.STR_NULL;
	    Globals.senderIndex.printTree();
	    FileIO.closeMessagesFile();
	} else {
	    Error.report(0);
	}
    }
}
