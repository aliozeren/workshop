package tr.gov.tuik.activitilib.samples;

import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.utils.TUIKRedmineUtils;

import com.taskadapter.redmineapi.bean.Issue;

public class TestRedmineApp 
{
	private final static Logger logger = Logger.getLogger(TestRedmineApp.class);

    public static void main( String[] args ) throws InterruptedException
    {
    	Issue x = TUIKRedmineUtils.getInstance("tuiktest").getIssueById(861);
    	logger.info(x.getSubject());
    }
}