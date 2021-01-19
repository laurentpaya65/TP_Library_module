package fr.training.spring.library.batch.exportjob;

import fr.training.spring.library.batch.common.FullReportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Writer;

@Configuration
public class ExportLibraryJobConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportLibraryJobConfig.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private FullReportListener listener;
    @Autowired
    public DataSource dataSource;
    @Autowired
    private BibliothequeProcessor bibliothequeProcessor;
    @Autowired
    private LivreProcessor livreProcessor;

    @Bean(name = "exportJob")
    public Job exportBookJob(final Step exportStep,final Step exportStepLivre) {
        return jobBuilderFactory.get("export-job") //
                .incrementer(new RunIdIncrementer()) //
                .listener(listener)
                .flow(exportStep) //
                .next(exportStepLivre) //
                .end() //
                .build();
    }

    @Bean
    public Step exportStep(final FlatFileItemWriter<BibliothequeDto> exportWriter) {
        return stepBuilderFactory.get("export-step").<Long, BibliothequeDto>chunk(10) //
                .reader(exportReader()) //
                .processor(bibliothequeProcessor) //
                .writer(exportWriter) //
                .build();
    }
    @Bean
    public Step exportStepLivre(final FlatFileItemWriter<LivreDto> exportWriterLivre) {
        return stepBuilderFactory.get("export-livre").<Long, LivreDto>chunk(10) //
                .reader(exportReaderLivre()) //
                .processor(livreProcessor) //
                .writer(exportWriterLivre) //
                .build();
    }

    /**
     * ItemReader is an abstract representation of how data is provided as input to
     * a Step. When the inputs are exhausted, the ItemReader returns null.
     */
    @Bean
    public JdbcCursorItemReader<Long> exportReader() {
        final JdbcCursorItemReader<Long> reader = new JdbcCursorItemReader<Long>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id  FROM BIBLIOTHEQUE");
        reader.setRowMapper(new SingleColumnRowMapper<>());

        return reader;
    }

    /**
     * ItemWriter is the output of a Step. The writer writes one batch or chunk of
     * items at a time to the target system. ItemWriter has no knowledge of the
     * input it will receive next, only the item that was passed in its current
     * invocation.
     */
    @StepScope // Mandatory for using jobParameters
    @Bean
    public FlatFileItemWriter<BibliothequeDto> exportWriter(@Value("#{jobParameters['output-file']}") final String outputFile) {
        final FlatFileItemWriter<BibliothequeDto> writer = new FlatFileItemWriter<BibliothequeDto>();
        writer.setResource(new FileSystemResource(outputFile));
        final DelimitedLineAggregator<BibliothequeDto> lineAggregator = new DelimitedLineAggregator<BibliothequeDto>();
        final BeanWrapperFieldExtractor<BibliothequeDto> fieldExtractor = new BeanWrapperFieldExtractor<BibliothequeDto>();
        fieldExtractor.setNames(new String[] { "id", "type", "numero","rue","codePostal","ville","prenom","nom"});
        lineAggregator.setFieldExtractor(fieldExtractor);
        lineAggregator.setDelimiter(";");
        writer.setLineAggregator(lineAggregator);
        writer.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(final Writer writer) throws IOException {
                writer.write("id;type;numero;rue;codePostal;ville;prenom;nom");
            }
        });
        return writer;
    }

    @Bean
    public JdbcCursorItemReader<Long> exportReaderLivre() {
        final JdbcCursorItemReader<Long> reader = new JdbcCursorItemReader<Long>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id  FROM LIVRE");
        reader.setRowMapper(new SingleColumnRowMapper<>());

        return reader;
    }

    @StepScope // Mandatory for using jobParameters
    @Bean
    public FlatFileItemWriter<LivreDto> exportWriterLivre(@Value("#{jobParameters['output-livre']}") final String outputLivre) {
        final FlatFileItemWriter<LivreDto> writer = new FlatFileItemWriter<LivreDto>();
        writer.setResource(new FileSystemResource(outputLivre));
        final DelimitedLineAggregator<LivreDto> lineAggregator = new DelimitedLineAggregator<LivreDto>();
        final BeanWrapperFieldExtractor<LivreDto> fieldExtractorLivre = new BeanWrapperFieldExtractor<LivreDto>();
        fieldExtractorLivre.setNames(new String[] { "id", "isbn", "titre","auteur","pageNumber","genre","biblioIds" });
        lineAggregator.setFieldExtractor(fieldExtractorLivre);
        lineAggregator.setDelimiter(";");
        writer.setLineAggregator(lineAggregator);
        writer.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(final Writer writer) throws IOException {
                writer.write("id;isbn;titre;auteur;pageNumber;genre;biblioIds");
            }
        });
        return writer;
    }

    // écriture dans un json
//    @StepScope // Mandatory for using jobParameters
//    @Bean
//    public JsonFileItemWriter<LivreDto> exportWriterLivre(@Value("#{jobParameters['output-livre']}") final String outputLivre) {
//        final JsonFileItemWriter<LivreDto> writer = new JsonFileItemWriter<LivreDto>();
}
