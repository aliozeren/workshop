package tr.gov.tuik.activitilib.zk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;

public class DynamicModel {
	private Label label;
	private Component component;

	
	/**
	 * Dynamic Model const
	 * @param label
	 * @param component
	 */
	public DynamicModel(Label label, Component component) {
		this.label = label;
		this.component = component;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

}
