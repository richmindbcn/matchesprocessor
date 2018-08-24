package cat.richmind.matchprocessor.listeners;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	private static final Logger LOG = Logger.getLogger(JobCompletionNotificationListener.class);
	
	public void beforeJob(JobExecution jobExecution) {
		LOG.info("A punto de iniciar job " + jobExecution.getJobInstance().getJobName());
	}
	
	public void afterJob(JobExecution jobExecution) {
		LOG.info("Job finalizado. Resultado: " + jobExecution.getExitStatus().toString());
	}
}