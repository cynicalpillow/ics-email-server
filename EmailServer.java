public class EmailServer{
    public static void main(String args[]){
	int error = Init.initializeSystem();
	if(error == Globals.PROCESS_OK){
	    FileIO.closeMessagesFile();
	} else {
	    ErrorReport.report(0);
	}
    }
}
