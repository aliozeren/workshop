package tr.gov.tuik.activitilib.types;

import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.FormValue;
import org.activiti.engine.form.AbstractFormType;

public abstract class AbstractCommonFormType extends AbstractFormType implements ActivitiFormTypeInterface 
{
	private static final long serialVersionUID = 9174362796522080281L;
	
	private String variable;
	
	private String value;
	private String id;	
	private String label;
	private String height;
	private String width;
	private String styleClass;
	private String onClick;
	private String onCreate;
	private String onDoubleClick;
	private String onMouseOver;
	private String onFocus;
	private String onBlur;
	private String onRightClick;
	private boolean required;
	private boolean writeable;
	private boolean readable;
	
	public void setVariable(String variable) 
	{
		this.variable= variable;
	}
	
	public String getVariable() 
	{
		return variable;
	}
	
	@Override
	public Object convertFormValueToModelValue(String propertyValue) 
	{
		return propertyValue;
	}

	@Override
	public String convertModelValueToFormValue(Object modelValue) 
	{
		return modelValue != null ? modelValue.toString() : null;
	}
	
	public String getMimeType() 
	{
		return "plain/text";
	}

	public AbstractFormType parseInput(FormProperty property) 
	{
		Map<String,String> map = new HashMap<String,String>();
		for (FormValue fv : property.getFormValues()) {
			map.put(fv.getId(), fv.getName());
		}
		
		this.id= property.getId();
		this.label= property.getName();
		
		this.height= map.get("height");
		this.width= map.get("width");
		this.styleClass= map.get("styleClass");
		this.onClick= map.get("onClick");
		this.onCreate= map.get("onCreate");
		this.onDoubleClick= map.get("onDoubleClick");
		this.onMouseOver= map.get("onMouseOver");
		this.onDoubleClick= map.get("onDoubleClick");
		this.onMouseOver= map.get("onMouseOver");
		this.onFocus= map.get("onFocus");
		this.onBlur= map.get("onBlur");
		this.onRightClick= map.get("onRightClick");
		this.readable = property.isReadable();
		this.writeable = property.isWriteable();
		this.required = property.isRequired();

		return this;
	}

	public String getHeight() {
		return height;
	}

	public String getWidth() {
		return width;
	}

	public String getOnClick() {
		return onClick;
	}

	public String getOnCreate() {
		return onCreate;
	}

	public String getOnDoubleClick() {
		return onDoubleClick;
	}

	public String getOnMouseOver() {
		return onMouseOver;
	}

	public String getOnFocus() {
		return onFocus;
	}

	public String getOnBlur() {
		return onBlur;
	}

	public String getOnRightClick() {
		return onRightClick;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public String getLabel() {
		return label;
	}

	public String getId() {
		return id;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean isWriteable() {
		return writeable;
	}

	public boolean isReadable() {
		return readable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getValue() {
		return value;
	}
	
}
