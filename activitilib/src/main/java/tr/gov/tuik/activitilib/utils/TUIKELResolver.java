package tr.gov.tuik.activitilib.utils;

import org.activiti.engine.impl.javax.el.ELContext;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;

public class TUIKELResolver implements TUIKELResolverInterface 
{

	private ELContext context= null;
	private ExpressionFactory expressionFactory= null;
	
	
	public TUIKELResolver(ExpressionFactory expressionFactory, ELContext context) 
	{
		this.expressionFactory = expressionFactory;
		this.context = context;
	}

	public Object resolve(String expression) 
	{
		ValueExpression ex = expressionFactory.createValueExpression(context, expression, Object.class);
        return ex.getValue(context);
	}
	
	
}
