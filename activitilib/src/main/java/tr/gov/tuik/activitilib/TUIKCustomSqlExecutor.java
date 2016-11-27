package tr.gov.tuik.activitilib;

import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;
import org.activiti.engine.impl.cmd.CustomSqlExecution;

public class TUIKCustomSqlExecutor 
{
	private static TUIKCustomSqlExecutor instance;
	
	private TUIKCustomSqlExecutor() {
		
	}
	
	public static TUIKCustomSqlExecutor getInstance() 
	{
		if (TUIKCustomSqlExecutor.instance == null) {
			TUIKCustomSqlExecutor.instance = new TUIKCustomSqlExecutor();
		}
		
		return TUIKCustomSqlExecutor.instance;
		
	}
	
	public List<Map<String, Object>> selectUserRelatedTasks(final String username) 
	{
		CustomSqlExecution<TUIKSqlMapper, List<Map<String, Object>>> customSqls =
				new AbstractCustomSqlExecution<TUIKSqlMapper, List<Map<String, Object>>>(TUIKSqlMapper.class) 
				{
					public List<Map<String, Object>> execute(TUIKSqlMapper customMapper) {
						return customMapper.selectUserRelatedTasks(username);
					}
				};

		return TUIKProcessEngine.getInstance().getProcessEngine().getManagementService().executeCustomSql(customSqls);
	}
	
	public List<Map<String, Object>> selectGroupRelatedTasks(final String group) 
	{
		CustomSqlExecution<TUIKSqlMapper, List<Map<String, Object>>> customSqls =
				new AbstractCustomSqlExecution<TUIKSqlMapper, List<Map<String, Object>>>(TUIKSqlMapper.class) 
				{
					public List<Map<String, Object>> execute(TUIKSqlMapper customMapper) {
						return customMapper.selectGroupRelatedTasks(group);
					}
				};

		return TUIKProcessEngine.getInstance().getProcessEngine().getManagementService().executeCustomSql(customSqls);
	}


}
