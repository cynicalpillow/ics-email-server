public class EmailServer
{
    public static String previousClientIPAddress = Globals.STR_NULL;
    public static long previousArrivalTime = 0;
    public static long currentArrivalTime = 0;

    public static void printMessage (Tree t)
    {
	if (t.getRoot () == null)
	    return;
	printMessage (new Tree (t.getRoot ().getLeft ()));
	Message m = new Message ();
	int rN = t.getRoot ().getRecordNumber ();
	m.readFromMessagesFile (rN);
	System.out.println (m + "\nID: " + t.getRoot ().getId ());
	System.out.println ("--------------");
	printMessage (new Tree (t.getRoot ().getRight ()));
    }


    public static void main (String args[])
    {
	int command = -1;
	int error = -1;

	Tree t = new Tree ();
	TNode s = new TNode ("123", 1);
	t.insertNode (s);
	t.deleteNode (s);

	error = Init.initializeSystem ();

	System.out.println(NetIO.myIPAddress());
	System.out.println ("Currently saved messages in receiverIndex Tree: ");
	printMessage (Globals.receiverIndex);
	System.out.println ();
	System.out.println ("Currently saved messages in senderIndex Tree: ");
	printMessage (Globals.senderIndex);

	if (error == Globals.PROCESS_OK)
	{
	    Message message = new Message ();
	    int recordNumber = 0;
	    TNode p = null;
	    TNode q = null;

	    String identification = Globals.STR_NULL;
	    char serverCommand = 0;
	    do
	    {
		System.out.println ("Waiting...");
		String request = NetIO.receiveRequest ();
		serverCommand = request.charAt (0);
		switch (serverCommand)
		{
		    case Globals.SEND_MESSAGE:
			request = Utils.setReceivingTime (request);
			message = new Message (request);
			recordNumber = message.writeToMessagesFile ();

			identification = message.getIdSenderFirst ();
			p = new TNode (identification, recordNumber, null, null, null);
			Globals.senderIndex.insertNode (p);

			System.out.println ("Correctly inserted in senderIndex: " + Globals.senderIndex.findNode (identification));
			Message m = new Message ();
			m.readFromMessagesFile (Globals.senderIndex.findNode (identification).getRecordNumber ());
			System.out.println ("Correctly stored in messageFile: \n" + m);

			identification = message.getIdReceiverFirst ();
			p = new TNode (identification, recordNumber, null, null, null);
			Globals.receiverIndex.insertNode (p);

			System.out.println ("\n");
			System.out.println ("Correctly inserted in receiverIndex: " + Globals.receiverIndex.findNode (identification));
			m.readFromMessagesFile (Globals.receiverIndex.findNode (identification).getRecordNumber ());
			System.out.println ("Correctly stored in messageFile: \n" + m);

			break;
		    case Globals.IN_BOX:
			identification = request.substring(Globals.CLIENT_POS);
			
			p = Globals.receiverIndex.findNode(identification, 0);
			q = Globals.receiverIndex.findNode(identification, 1);
			
			Globals.receiverIndex.prepareTransmissionString(p, q);
			Globals.transmissionString = Globals.transmissionString + Utils.intToBytesStr(Globals.END_OF_MESSAGES_TRANSMISSION);
			error = NetIO.sendRequest(Globals.transmissionString, Globals.clientIPAddress);
			Globals.transmissionString = Globals.STR_NULL;
		    case Globals.OUT_BOX:
			identification = request.substring(Globals.CLIENT_POS);
			
			p = Globals.senderIndex.findNode(identification, 0);
			q = Globals.senderIndex.findNode(identification, 1);
			
			Globals.senderIndex.prepareTransmissionString(p, q);
			Globals.transmissionString = Globals.transmissionString + Utils.intToBytesStr(Globals.END_OF_MESSAGES_TRANSMISSION);
			error = NetIO.sendRequest(Globals.transmissionString, Globals.clientIPAddress);
			Globals.transmissionString = Globals.STR_NULL;
		    default:
			System.out.println ("Unknown request: " + request);
			break;
		}
	    }
	    while (serverCommand != Globals.SERVER_SHUTDOWN);
	}
	/*  int error = Init.initializeSystem();
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
	  }*/
    }
}
