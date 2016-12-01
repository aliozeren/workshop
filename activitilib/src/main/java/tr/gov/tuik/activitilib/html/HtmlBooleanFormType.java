package tr.gov.tuik.activitilib.html;

import org.activiti.engine.form.FormProperty;

import tr.gov.tuik.activitilib.types.AbstractPasswordFormType;

public class HtmlBooleanFormType extends AbstractPasswordFormType
{
	private static final long serialVersionUID = 3868249214623992954L;

	public Object renderInput(FormProperty property, String value) {
		return "<input type='boolean'/>";
	}
}
