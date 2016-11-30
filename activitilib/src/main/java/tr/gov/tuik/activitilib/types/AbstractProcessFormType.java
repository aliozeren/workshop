package tr.gov.tuik.activitilib.types;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.engine.form.AbstractFormType;

public abstract class AbstractProcessFormType extends AbstractCommonFormType
{
	private static final long serialVersionUID = 2262742328986137324L;
	
	public static final String NAME = "process_name";
	
	public AbstractFormType parseInput(FormProperty property)
	{
		super.parseInput(property);
		
		return this;
	}
	
	public String getName() 
	{
		return AbstractProcessFormType.NAME;
	}
}
