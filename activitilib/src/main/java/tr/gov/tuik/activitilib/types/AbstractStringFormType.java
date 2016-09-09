package tr.gov.tuik.activitilib.types;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.engine.form.AbstractFormType;

public abstract class AbstractStringFormType extends AbstractCommonFormType
{

	private static final long serialVersionUID = 3868249214623992954L;
	public static final String NAME = "string";
	
	public AbstractFormType parseInput(FormProperty property)
	{
		return this;
	}
	
	public String getName() 
	{
		return AbstractStringFormType.NAME;
	}
}
