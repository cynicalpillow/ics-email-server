import java.io.*;
public class Globals {

    public static final String MESSAGES_FILE = "_messages.dat";
    public static final String AVAILABLE_LIST_FILE = "_available.dat";
    public static RandomAccessFile msg = null;
    public static final int PROCESS_ERROR = -1;
    public static final int PROCESS_OK = 0;
    public static int totalRecordsInMessageFile = -1;
    
    public static final int COMMAND_POS = 0;
    public static final int COMMAND_LEN = 1;
    public static final int CLIENT_ID_LEN = 9;
    public static final int SENDER_POS = COMMAND_POS+COMMAND_LEN;
    public static final int SENDER_LEN = CLIENT_ID_LEN;
    public static final int RECEIVER_POS = SENDER_POS + SENDER_LEN;
    public static final int RECEIVER_LEN = CLIENT_ID_LEN;
    public static final int DATE_TIME_POS = RECEIVER_POS + RECEIVER_LEN;
    public static final int DATE_TIME_LEN = 8;
    public static final int IDENTIFICATION_LEN = SENDER_LEN + RECEIVER_LEN + DATE_TIME_LEN;
    public static final int FIRST_RECORD_MARKER_POS = DATE_TIME_POS + DATE_TIME_LEN;
    public static final int FIRST_RECORD_MARKER_LEN = 1;
    public static final int END_OF_SUBJECT_MARKER_LEN = 1;
    
    public static final int RECORD_DATA_LEN = 80;
    public static final int TEXT_LEN = RECORD_DATA_LEN-(COMMAND_LEN+SENDER_LEN+RECEIVER_LEN+DATE_TIME_LEN+FIRST_RECORD_MARKER_LEN);

    public static final int NEXT_RECORD_LEN = 4;
    public static final int RECORD_LEN = RECORD_DATA_LEN + NEXT_RECORD_LEN;
    
    public static final int END_OF_MESSAGE = -1;
    public static final char BLANK = (char)32;
    public static final int APPEND = 1;
    public static final int MODIFY = 2;
    
    public static final char DELETED = '*';
    public static AvailableList availableList = null;
    public static final int EMPTY_AVAILABLE_LIST = -1;
    
    public static final boolean DEBUG_ON = true;
    public static final int NULL = 0;
    public static final String STR_NULL = "";
    public static final char CR = '\n';
    public static final char FIRST_RECORD_MARKER = '+';
    public static final char END_OF_SUBJECT_MARKER = '@';
    public static final char SEND_MESSAGE = 'S';
    public static final char DELETE_MESSAGE = 'D';
    public static final char IN_BOX = 'I';
    public static final char OUT_BOX = 'O';
    public static final char SERVER_SHUTDOWN = 'Q';
    
    public static final int INT_LEN = 4;
    
    public static Tree senderIndex = null;
    public static Tree receiverIndex = null;
    
    public static final int SENDER_ID = 0;
    public static final int RECEIVER_ID = 1;
    
    //Network Constants
    public static final String SERVER_IP_ADDRESS = "192.168.2.125";
    public static String clientIPAddress = STR_NULL;
    public static final int PORT_NUMBER = 5000;
    public static final int TIME_OUT = 10000;
    public static final int NET_OK = 0;
    public static final int NET_SEND_ERROR = -1;
    public static final int NET_RECEIVE_ERROR = -2;
    
    public static final int SENDING_ATTEMPTS_LIMIT = 1000;
    public static final int END_OF_MESSAGES_TRANSMISSION = -2;
    
    public static final int MINIMUM_TIME_BETWEEN_REQUESTS_OF_SAME_MACHINE = 50;
    
    public static String transmissionString = STR_NULL;
}
