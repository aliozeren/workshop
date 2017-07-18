package tr.gov.tuik.activitilib.samples;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.SignalCmd;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.runtime.Execution;

import tr.gov.tuik.activitilib.TUIKProcessEngine;
import tr.gov.tuik.activitilib.utils.TUIKSetActivitiIdCmd;

public class CommandExecute {

	public static void main(String[] args) {
		
		Execution execution = TUIKProcessEngine.getInstance().getProcessEngine().getRuntimeService().createExecutionQuery().
				processInstanceId("xyz").singleResult();			
		
		CommandExecutor commandExecutor = ((ProcessEngineConfigurationImpl)TUIKProcessEngine.getInstance().getProcessEngine().getProcessEngineConfiguration()).getCommandExecutor();
		
		commandExecutor.execute(new TUIKSetActivitiIdCmd(execution.getId(), "firstTask"));
		commandExecutor.execute(new SignalCmd(execution.getId(), "compensationDone", null, null));
		
	}

}
