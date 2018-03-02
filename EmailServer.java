public class EmailServer{
    public static void main(String args[]){
	int error = Init.initializeSystem();
	if(error == Globals.PROCESS_OK){
	    Tree t = new Tree();
	    t.buildFromMessagesFile(Globals.SENDER_ID);
	    t.printTree();
	    FileIO.closeMessagesFile();
	} else {
	    Error.report(0);
	}
    }
}
