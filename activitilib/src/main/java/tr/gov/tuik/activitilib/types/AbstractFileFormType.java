package tr.gov.tuik.activitilib.types;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.engine.form.AbstractFormType;

import tr.gov.tuik.activitilib.TUIKConstants;


public abstract class AbstractFileFormType extends AbstractCommonFormType
{
	private static final long serialVersionUID = 3868249214123992954L;
	
	public static final String NAME = "file";
	
	public static final String UPLOAD = "upload";
	public static final String DOWNLOAD = "download";
	public static final String IDENTIFIER = "identifier";
	public static final String MAXSIZE = "maxsize";
	public static final String FILE_PATTERN = "filePattern";
	
	
	private String upload;
	private String download;
	private String identifier;
	private String maxsize;
	private String filePattern;
	
	public AbstractFormType parseInput(FormProperty property)
	{
		super.parseInput(property);
		this.upload= super.getMap().get(AbstractFileFormType.UPLOAD) != null ? TUIKConstants.Answer.YES : TUIKConstants.Answer.NO;
		this.download= super.getMap().get(AbstractFileFormType.DOWNLOAD) != null ? TUIKConstants.Answer.YES : TUIKConstants.Answer.NO;
		this.identifier= super.getMap().get(AbstractFileFormType.IDENTIFIER);
		this.maxsize= super.getMap().get(AbstractFileFormType.MAXSIZE);
		this.filePattern= super.getMap().get(AbstractFileFormType.FILE_PATTERN);

		return this;
	}
	
	
	public String getName() 
	{
		return AbstractFileFormType.NAME;
	}

	public String getUpload() {
		return upload;
	}

	public String getDownload() {
		return download;
	}

	public String getIdentifier() {
		return identifier;
	}


	public String getMaxsize() {
		return maxsize;
	}


	public String getFilePattern() {
		return filePattern;
	}
	
	
}
