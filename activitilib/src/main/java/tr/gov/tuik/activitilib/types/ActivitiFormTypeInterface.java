package tr.gov.tuik.activitilib.types;

import org.activiti.engine.form.AbstractFormType;

public interface ActivitiFormTypeInterface 
{
	public AbstractFormType parseInput(org.activiti.bpmn.model.FormProperty property);
	public Object renderInput(org.activiti.engine.form.FormProperty property);
	public void setVariable(String variable);
	public String getVariable();
}