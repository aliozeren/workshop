package tr.gov.tuik.activitilib.zk;


import java.text.SimpleDateFormat;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Datebox;

import tr.gov.tuik.activitilib.TUIKProcessEngineException;
import tr.gov.tuik.activitilib.types.AbstractDateFormType;

public class ZKDateFormType extends AbstractDateFormType 
{
	private static final long serialVersionUID = 3868249214623992114L;

	public DynamicModel renderInputForType(FormProperty property) 
	{
		Datebox component = new Datebox();
		component.setFormat(this.getDatePattern());
		component= (Datebox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		if (property.getValue() != null) {
			SimpleDateFormat sdf= new SimpleDateFormat(this.getDatePattern());
			try {
				component.setValue(sdf.parse(property.getValue()));
			} catch (Exception e) {
				throw new TUIKProcessEngineException("Invalid date format for input "+ property.getName() + ":" + property.getValue(), e);
			} 
		}

		return  ZKInputUtils.getInstance().getDynamicModel(this, component);

	};


}
