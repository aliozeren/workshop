package tr.gov.tuik.activitilib.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKRedmineException;

import com.taskadapter.redmineapi.Include;
import com.taskadapter.redmineapi.IssueManager;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.Attachment;
import com.taskadapter.redmineapi.bean.CustomField;
import com.taskadapter.redmineapi.bean.CustomFieldFactory;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.IssueFactory;
import com.taskadapter.redmineapi.bean.Journal;
import com.taskadapter.redmineapi.bean.JournalDetail;
import com.taskadapter.redmineapi.bean.Project;

public class TUIKRedmineUtils 
{
	
	private final static Logger logger = Logger.getLogger(TUIKRedmineUtils.class);

	private static final String URI_KEY = "uri";
	private static final String API_ACCESS_KEY = "apiAccessKey";
	private static final String PROJECT_KEY = "projectKey";
	private static final String FILE_NAME = "/redmine.properties";
	
	private String uri;
	private String apiAccessKey;
	private String projectKey;
	
	private RedmineManager redmineManager;
	private Project project;
	
	private static TUIKRedmineUtils instance;
	
	private TUIKRedmineUtils()
	{
	}
	
	public static TUIKRedmineUtils getInstance() 
	{
		if (instance == null) {
			instance = new TUIKRedmineUtils();
			try {
				instance.readProperties();
				instance.redmineManager= RedmineManagerFactory.createWithApiKey(instance.uri, instance.apiAccessKey);
				instance.project= instance.redmineManager.getProjectManager().getProjectByKey(instance.projectKey);
			} catch (Exception e) {
				TUIKUtils.getInstance().logError(logger,e);
				throw new TUIKRedmineException("Error in creating TUIKRedmineUtils Object...", e);
			}
		}
		return instance;
	}

	
	/**
	 * Attaches a file to an issue
	 * @param fileName
	 * @param content
	 * @return
	 */
	public Attachment attachFile(String fileName, byte[] content) {
		Attachment attachment = null;
	    try {
	    	attachment = redmineManager.getAttachmentManager().uploadAttachment(fileName, "", content);
	    } catch (RedmineException ex) {
	    	throw new TUIKRedmineException("Can't attach file to a Redmine issue", ex);
	    } catch (IOException ex) {
	    	throw new TUIKRedmineException("Can't attach file to a Redmine issue", ex);
	    }
	    return attachment;
	}
	
	/**
	 * Read readmine connection properties from resource
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws RedmineException
	 */
	private void readProperties() throws IOException,FileNotFoundException, RedmineException
	{
		Properties properties = null;
		InputStream inputStream = null;
		HashMap<String, String> props = new HashMap<String, String>();
		
		properties = new Properties();
		inputStream = getClass().getResourceAsStream(FILE_NAME);
		properties.load(inputStream);
		for(Object keyObject : properties.keySet()){
			String key = keyObject.toString();
			props.put(key,properties.getProperty(key));
		}

		this.uri = props.get(URI_KEY);
		this.apiAccessKey = props.get(API_ACCESS_KEY);
		this.projectKey = props.get(PROJECT_KEY);
	}
	
	
	/**
	 * Sent issue to redmine using issue manager
	 * @param issue
	 * @return
	 */
	public Issue sentIssue(Issue issue) 
	{
		IssueManager issueManager = redmineManager.getIssueManager();
		try {
			return issueManager.createIssue(issue);

		} catch (RedmineException e) {
			TUIKUtils.getInstance().logError(logger,e);
			throw new TUIKRedmineException("Error in sending issue...", e);
		}
	}

	/**
	 * Creates an issue for redmine
	 * @param issue
	 * @return
	 */
	public Issue createIssue(String subject) 
	{
		try {
			Issue issue = IssueFactory.create(project.getId(), subject); 
			return issue;

		} catch (Exception e) {
			TUIKUtils.getInstance().logError(logger,e);
			throw new TUIKRedmineException("Error in creating issue...", e);
		}
	}
	
	/**
	 * Deletes the given issue from redmine
	 * @param issueId
	 */
	public void deleteIssue(String issueId){
		IssueManager issueManager = redmineManager.getIssueManager();
		try{
			issueManager.deleteIssue(new Integer(issueId));
		}catch(RedmineException e){
			TUIKUtils.getInstance().logError(logger,e);
			throw new TUIKRedmineException("Error in deleting issue..", e);
		}
	}

	/**
	 * creates custom fields for redmine issue
	 * @param id
	 * @param name
	 * @param value
	 * @return
	 */
	public CustomField createCustomField(int id,String name,String value)
	{
		
		CustomField field = CustomFieldFactory.create(id, name, value);
		return field;	
	}
	
	public Collection<CustomField> getAllCustomFields(int id)
	{
		Collection<CustomField> list = null;
		try {
			list = redmineManager.getIssueManager().getIssues(projectKey, null,Include.journals, Include.relations, Include.attachments, Include.changesets,Include.watchers).get(id).getCustomFields();
			
			for (CustomField customField : list) {
				customField.setValue("");
			}
		} catch (RedmineException e) {
			TUIKUtils.getInstance().logError(logger,e);
		}
		return list;
	}

	/**
	 * retrieves the issue by ID
	 * @param id
	 * @return
	 */
	public Issue getIssueById(int id) {
		Issue issue = null;
		try {
			issue = redmineManager.getIssueManager().getIssueById(id);
		} catch (RedmineException e) {
			TUIKUtils.getInstance().logError(logger,e);
		}
		return issue;
	}

	/**
	 * gets the journal of an issue by ID (what has happened to the issue)
	 * @param issueId
	 * @return
	 */
	public Collection<Journal> getJournals(int issueId)
	{
		Collection<Journal> list = null;
		try {
			Issue issue = redmineManager.getIssueManager().getIssueById(issueId,Include.journals);
			list = issue.getJournals();
		} catch (RedmineException e) {
			TUIKUtils.getInstance().logError(logger,e);
			throw new TUIKRedmineException(e);
		}
		return list;
	}

	/**
	 * Gets the journal details 
	 * @param issueId
	 * @param journalId
	 * @return
	 */
	public List<JournalDetail> getJournalDetails(int issueId,int journalId) {

		List<JournalDetail> list = null;
		try {
				Issue issue = redmineManager.getIssueManager().getIssueById(issueId,Include.journals);
				for (Journal journal : issue.getJournals()) {
					if(journal.getId() == journalId)
						list = journal.getDetails();
				}
		} catch (RedmineException e) {
			TUIKUtils.getInstance().logError(logger,e);
		}
		return list;
	}
	
	public String getJournalDetailsByIndexId(int index,int control)
	{
		try {
			if(control == 1)
			return redmineManager.getIssueManager().getStatuses().get(index).getName();
			else if(control == 2)
				return redmineManager.getIssueManager().getTrackers().get(index).getName();
			else if(control == 3)
				return redmineManager.getUserManager().getUserById(index+1).getFullName();
			else if(control == 4)
				return redmineManager.getIssueManager().getIssuePriorities().get(index).getName();	
			else return "";
		} catch (RedmineException e) {
			TUIKUtils.getInstance().logError(logger,e);
			throw new TUIKRedmineException(e);
		}
	}
}
