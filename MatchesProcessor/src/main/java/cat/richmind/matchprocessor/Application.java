package cat.richmind.matchprocessor;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ideltoro
 *
 */
@SpringBootApplication
public class Application {	
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);
	/**
	 * @param args
	 * @throws JobParametersInvalidException 
	 * @throws JobInstanceAlreadyCompleteException 
	 * @throws JobRestartException 
	 * @throws JobExecutionAlreadyRunningException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, InterruptedException {
		LOG.info("Starting Matches Processor");
		SpringApplication app = new SpringApplication(Application.class);
		
		ConfigurableApplicationContext ctx = app.run(args);
		// config beans and checks
		JobLauncher launcher = ctx.getBean(JobLauncher.class);
		Job retrieveMatches = ctx.getBean("retrieveMatchesJob", Job.class);
		JobParameters params = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
		
		JobExecution je = launcher.run(retrieveMatches, params);
		while (je.getStatus().isRunning()) {
			LOG.debug("Still working...");
			Thread.sleep(1000);
		}
		LOG.info("EXIT STATUS: " + je.getExitStatus().getExitCode());
		
		System.exit(0);
	}
}