package tr.gov.tuik.activitilib.zk;


import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Datebox;

import tr.gov.tuik.activitilib.types.AbstractDateFormType;

public class ZKDateFormType extends AbstractDateFormType 
{
	private static final long serialVersionUID = 3868249214623992114L;

	public DynamicModel renderInput(FormProperty property) 
	{
		Datebox component = new Datebox();
		component.setFormat(this.getDatePattern());
		component= (Datebox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component);

	};


}
