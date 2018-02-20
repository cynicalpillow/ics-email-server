public class Utils {
    public static void delay(int ms){
	try {
	    Thread.sleep(ms);
	} catch (InterruptedException e){
	    Thread.currentThread().interrupt();
	}
    }
    public static boolean isANumber(String s){
	boolean itIsANumber = true;
	for(int i = 0; i < s.length() && itIsANumber; i++)
	    itIsANumber = Character.isDigit(s.charAt(i));
	return (itIsANumber && s.length() > 0);
    }
    public static String leftPad(String text, int desiredLength, char paddingItem){
	String padding = new String(Globals.STR_NULL);
	for(int i = 0; i < desiredLength - text.length(); i++)
	    padding = padding + paddingItem;
	return padding + text;
    }
    public static String removeChars(String text, char c){
	String result = new String(Globals.STR_NULL);
	for(int i = 0; i < text.length(); i++){
	    if(text.charAt(i)!=c)
		result = result + text.charAt(i);
	}
	return result;
    }
    public static String longToBytesStr(long num){
	return "" + (char)(num >> 56) +
		    (char)((num & 0xFF000000000000L) >> 48) + 
		    (char)((num & 0xFF0000000000L) >> 40) +
		    (char)
    }
}
