package tr.gov.tuik.activitilib.samples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKProcessEngine;

public class GetDiagramForProcessApp 
{

	private final static Logger logger = Logger.getLogger(GetDiagramForProcessApp.class);

    public static void main( String[] args ) throws IOException
    {
    	
    	FileOutputStream f = new FileOutputStream(new File("/home/alio/test.png"));
    	TUIKProcessEngine.getInstance().getProcessDiagram("eventTestProcess",f);

    	f.close();
    	
    	TUIKProcessEngine.destroy();
    }

}
