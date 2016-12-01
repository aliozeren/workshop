package tr.gov.tuik.activitilib;

import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.log4j.Logger;

public class ListTaskIdentityApp 
{

	private final static Logger logger = Logger.getLogger(ListTaskIdentityApp.class);

	public static void main( String[] args )
	{


		TUIKProcessEngine p = TUIKProcessEngine.getInstance();

//		p.involveUser("132505", "alio", "koordinator");

		for (IdentityLink i : p.getTaskIdentityLinks("160015")) {

			System.out.println(i.getUserId() + "---" + i.getType());
		}

		for (Map<String, Object> t : TUIKCustomSqlExecutor.getInstance().selectUserRelatedTasks("alio")){
			for ( String k : t.keySet()) {
				System.out.println( k + " = " + t.get(k));
			}
		}

		for (Task t : p.getUserRelatedTasks("alio")) {
			System.out.println(t.getId() + " -- " );
		}
		
		for (Comment e: p.getTaskComments("160015", "uyari")) {
			System.out.println(e.getFullMessage());
		}
		TUIKProcessEngine.destroy();
	}



}