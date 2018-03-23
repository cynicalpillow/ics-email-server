public class EmailServer{
    public static void printMessage(Tree t){
	if(t.getRoot() == null)return;
	printMessage(new Tree(t.getRoot().getLeft()));
	Message m = new Message();
	int rN = t.getRoot().getRecordNumber();
	m.readFromMessagesFile(rN);
	System.out.println(m);
	System.out.println("--------------");
	printMessage(new Tree(t.getRoot().getRight()));
    }
    public static void main(String args[]){
	int error = Init.initializeSystem();
	if(error == Globals.PROCESS_OK){
	    Message message = new Message();
	    //Test 1
	    Globals.senderIndex.printTree();
	    int recordNumber = Globals.senderIndex.findNode("JB", 1).getRecordNumber();
	    message.readFromMessagesFile(recordNumber);
	    System.out.println(message);
	    System.out.println("--------------");
	    System.out.println("--------------");
	    
	    //Test 2
	    printMessage(Globals.senderIndex);
	    
	    //Test 3
	    TNode n = Globals.senderIndex.findNode("bJ", 1);
	    if(n == null)System.out.println("Not found");
	    else {
		recordNumber = n.getRecordNumber();
		message.readFromMessagesFile(recordNumber);
		System.out.println(message);
	    }
	    
	    FileIO.closeMessagesFile();
	} else {
	    Error.report(0);
	}
    }
}
