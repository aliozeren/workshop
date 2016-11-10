package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Div;

import tr.gov.tuik.activitilib.TUIKConstants;
import tr.gov.tuik.activitilib.types.AbstractFileFormType;

public class ZKFileFormType extends AbstractFileFormType
{
	private static final long serialVersionUID = 3868249214623992954L;

	public DynamicModel renderInput(FormProperty property) 
	{
		
		Div component = new Div();
		component= (Div) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		component.setAttribute(TUIKConstants.LABEL, super.getLabel());
		component.setAttribute(AbstractFileFormType.UPLOAD, super.getUpload());
		component.setAttribute(AbstractFileFormType.DOWNLOAD, super.getDownload());		
		component.setAttribute(AbstractFileFormType.IDENTIFIER, super.getIdentifier());
		component.setAttribute(AbstractFileFormType.MAXSIZE, super.getMaxsize());
		component.setAttribute(AbstractFileFormType.FILE_PATTERN, super.getFilePattern());
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component, false);

	}
	
}
