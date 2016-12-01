package tr.gov.tuik.activitilib.zk;


import java.text.SimpleDateFormat;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Datebox;

import tr.gov.tuik.activitilib.TUIKProcessEngineException;
import tr.gov.tuik.activitilib.types.AbstractDateFormType;

public class ZKDateFormType extends AbstractDateFormType 
{
	private static final long serialVersionUID = 3868249214623992114L;

	public DynamicModel renderInput(FormProperty property, String value) 
	{
		Datebox component = new Datebox();
		component.setFormat(this.getDatePattern());
		component= (Datebox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		if (value != null) {
			SimpleDateFormat sdf= new SimpleDateFormat(this.getDatePattern());
			try {
				component.setValue(sdf.parse(value));
			} catch (Exception e) {
				e.printStackTrace();
				throw new TUIKProcessEngineException("Invalid date format for input "+ property.getName() + ":" + value);
			} 
		}

		return  ZKInputUtils.getInstance().getDynamicModel(this, component);

	};


}
