package tr.gov.tuik.activitilib.types;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.engine.form.AbstractFormType;


public abstract class AbstractButtonFormType extends AbstractCommonFormType
{
	private static final long serialVersionUID = 3868249214123992954L;
	public static final String NAME = "button";
	
	public AbstractFormType parseInput(FormProperty property)
	{
		super.parseInput(property);		
		return this;
	}

	public String getName() 
	{
		return AbstractButtonFormType.NAME;
	}
}