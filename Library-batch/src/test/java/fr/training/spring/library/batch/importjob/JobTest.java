package fr.training.spring.library.batch.importjob;

import fr.training.spring.library.batch.exportjob.ExportLibraryJobConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import fr.training.spring.library.LibraryBatchApplication;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBatchTest
@ContextConfiguration(classes = {ExportLibraryJobConfig.class, LibraryBatchApplication.class})
public class JobTest {

//	@Autowired
//	private JobLauncherTestUtils jobLauncherTestUtils;
//
//	@Autowired
//	private JobRepositoryTestUtils jobRepositoryTestUtils;
//
//
//	@Before
//	public void clearMetadata() {
//		jobRepositoryTestUtils.removeJobExecutions();
//	}
//
	@Test
	public void testJob() throws Exception {
//		// given
//		final JobParameters jobParameters =
//				jobLauncherTestUtils.getUniqueJobParameters();
//
//		// when
//		final JobExecution jobExecution =
//				jobLauncherTestUtils.launchJob(jobParameters);
//
//		// then
//		Assert.assertEquals(ExitStatus.COMPLETED,
//				jobExecution.getExitStatus());
	}

}