package tr.gov.tuik.activitilib.types;

import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.FormValue;
import org.activiti.engine.form.AbstractFormType;

public abstract class AbstractTestFormType extends AbstractCommonFormType 
{

	private static final long serialVersionUID = 3761287922801320051L;

	public static final String NAME = "tuikTestFormType";

	private String width;
	private String height;
	
	public String getName() {
		return AbstractTestFormType.NAME;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getWidth() {
		return width;
	}

	public String getHeight() {
		return height;
	}
	
	public AbstractFormType parseInput(FormProperty property) 
	{
		Map<String,String> map = new HashMap<String,String>();
		for (FormValue fv : property.getFormValues()) {
			map.put(fv.getId(), fv.getName());
		}
		this.height= map.get("height");
		this.width= map.get("width");
		
		return this;
	}
	
}