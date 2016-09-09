package tr.gov.tuik.activitilib.types;


import org.activiti.bpmn.model.FormProperty;
import org.activiti.engine.form.AbstractFormType;

public abstract class AbstractDateFormType extends AbstractCommonFormType
{
	private static final long serialVersionUID = 1664028556950591530L;
	
	public static final String NAME = "date";
	
	private String datePattern;
	
	public AbstractFormType parseInput(FormProperty property)
	{
		this.datePattern= property.getDatePattern();
		return this;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) 
	{
		this.datePattern = datePattern;
	}
	
	public String getName() 
	{
		return  AbstractDateFormType.NAME;
	}


}
