package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.AbstractFormType;

public class TUIKTestFormType extends AbstractFormType {

	private static final long serialVersionUID = 3761287922801320051L;

	public static final String NAME = "tuikTestFormType";

	private final String width;
	private final String height;

	public TUIKTestFormType(String width, String height) {
		this.width = width;
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public String getHeight() {
		return height;
	}
	
	@Override
	public Object convertFormValueToModelValue(String propertyValue) {
		return Integer.valueOf(propertyValue);
	}

	@Override
	public String convertModelValueToFormValue(Object modelValue) {
		return modelValue != null ? modelValue.toString() : null;
	}

	public String getName() {
		return TUIKTestFormType.NAME;
	}
}