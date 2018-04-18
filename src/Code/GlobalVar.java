package Code;

import java.io.File;
import java.util.ArrayList;

public class GlobalVar {
	public static ArrayList<String> FILE_TO_SEND = new ArrayList<>() ;
	public static String  log = "Waiting.... \n";
	public static ArrayList<String> FILE_NAME = new ArrayList<>();
	public static String standarString(String myString) {
		return "<html>" + myString.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>";
	}
}
