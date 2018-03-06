public class EmailServer{
    public static void main(String args[]){
	int error = Init.initializeSystem();
	if(error == Globals.PROCESS_OK){
	    Globals.senderIndex.printTree();
	    FileIO.closeMessagesFile();
	} else {
	    Error.report(0);
	}
    }
}
