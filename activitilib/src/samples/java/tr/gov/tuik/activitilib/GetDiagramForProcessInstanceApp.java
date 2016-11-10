package tr.gov.tuik.activitilib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class GetDiagramForProcessInstanceApp 
{

	private final static Logger logger = Logger.getLogger(GetDiagramForProcessInstanceApp.class);

    public static void main( String[] args ) throws IOException
    {
    	
    	FileOutputStream f = new FileOutputStream(new File("/home/alio/test2.png"));
    	
    	List<String> list = new ArrayList<String>();
    	list.add("107508");
    	
    	TUIKProcessEngine.getInstance().getProcessDiagramForInstanceWithMapAll("105001",f, "evet", null);

    	f.close();
    	
    	TUIKProcessEngine.destroy();
    }

}
