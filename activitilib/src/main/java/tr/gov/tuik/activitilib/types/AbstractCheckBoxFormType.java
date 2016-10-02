package tr.gov.tuik.activitilib.types;

import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.FormValue;
import org.activiti.engine.form.AbstractFormType;


public abstract class AbstractCheckBoxFormType extends AbstractCommonFormType
{
	private static final long serialVersionUID = 3868249214123992954L;
	public static final String NAME = "checkbox";
	
	public AbstractFormType parseInput(FormProperty property)
	{
		Map<String,String> map = new HashMap<String,String>();
		for (FormValue fv : property.getFormValues()) {
			map.put(fv.getId(), fv.getName());
		}
		
		return this;
	}

	public String getName() 
	{
		return AbstractCheckBoxFormType.NAME;
	}
}
