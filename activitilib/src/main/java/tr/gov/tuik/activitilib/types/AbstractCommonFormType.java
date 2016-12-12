package tr.gov.tuik.activitilib.types;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.FormValue;
import org.activiti.engine.form.AbstractFormType;

import tr.gov.tuik.activitilib.utils.TUIKELResolverInterface;

public abstract class AbstractCommonFormType extends AbstractFormType implements ActivitiFormTypeInterface 
{
	private static final long serialVersionUID = 9174362796522080281L;
	
	Map<String,String> map;
	
	private String variable;
	
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
		map = new HashMap<String,String>();
		
		for (FormValue fv : property.getFormValues()) {
			map.put(fv.getId(), fv.getName());
		}
		
		this.id= property.getId();
		this.label= property.getName();
		
		this.height = map.get("height") != null ? map.get("height") : "100%";
		this.width = map.get("width") != null ? map.get("width") : "100%";
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

	public Map<String, String> getMap() {
		return map;
	}

	/* (non-Javadoc)
	 * @see tr.gov.tuik.activitilib.types.ActivitiFormTypeInterface#renderInput(org.activiti.engine.form.FormProperty, tr.gov.tuik.activitilib.utils.TUIKELResolverInterface)
	 */
	public Object renderInput(org.activiti.engine.form.FormProperty property, TUIKELResolverInterface elResolver) 
	{
		if (elResolver != null) {
			Map<String, String> resolvedMap = new HashMap<String, String>();
			for (Entry<String, String> entry : map.entrySet()) {
				resolvedMap.put(elResolver.resolve(entry.getKey()).toString(), elResolver.resolve(entry.getValue()).toString());
			}
			map= resolvedMap;
		}
		
		return this.renderInputForType(property);
	}
	
	public abstract Object renderInputForType(org.activiti.engine.form.FormProperty property);
	
}
