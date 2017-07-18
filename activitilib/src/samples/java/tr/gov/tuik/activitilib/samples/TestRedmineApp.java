package tr.gov.tuik.activitilib.samples;

import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.utils.TUIKRedmineUtils;

import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.TrackerFactory;

public class TestRedmineApp 
{
	private final static Logger logger = Logger.getLogger(TestRedmineApp.class);

    public static void main( String[] args ) throws InterruptedException
    {
    	
//    	Issue x = TUIKRedmineUtils.getInstance("tuiktest").createIssue("test 1 2");
//    	x.setTracker(TrackerFactory.create(1));
    	
//    	Collection<CustomField> x = TUIKRedmineUtils.getInstance("tuiktest").getAllCustomFields(0);
//    	logger.info(x);
    	TUIKRedmineUtils.getInstance("rcop-mis").getIssueById(761);
    	    	
    
    }
}