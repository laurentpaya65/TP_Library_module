package fr.training.spring.library.batch.importjob;

import fr.training.spring.library.application.LivreService;
import fr.training.spring.library.batch.common.FullReportListener;
import fr.training.spring.library.batch.exportjob.LivreDto;
import fr.training.spring.library.domain.Livre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;

@Configuration
public class ImportLivreJobConfig {

    private static final Logger LOGGER  = LoggerFactory.getLogger(ImportLivreJobConfig.class);
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private FullReportListener listener;
//    @Autowired
//    private DeleteTasklet deleteTasklet;
//    @Autowired
//    public DataSource dataSource;
//    @Autowired
//    private ItemRepository itemRepository;
    @Autowired
    private LivreService livreService;
//    @Autowired
//    public DeleteTasklet deleteTasklet;
    @Autowired
    public ProcessorDtoVersLivre processorDtoVersLivre;


//    @Bean
//    public Step deleteStep() {
//
//    }

    @Bean
    public Job importJob() {
        return jobBuilderFactory.get("import-job") //
                .incrementer(new RunIdIncrementer()) //
                .start(importStep())
//                .start(deleteStep()) //
//                .next(importStep()) //
                .listener(listener) //
                .build();
    }
//    @Bean
//    public Step deleteStep() {
//        return stepBuilderFactory.get("delete-step")
//                .tasklet(deleteTasklet)
//                .build();
//    }
    @Bean
    public Step importStep() {

//        try {
            return stepBuilderFactory.get("import-step").<LivreDto, Livre>chunk(2) //
                    .reader(importReader(null)) //
                    .processor(processorDtoVersLivre)
                    .writer(importWriter()) //
                    .faultTolerant()
                    .skip(IllegalArgumentException.class)
//                    .listener(new MySkipListener())
                    .skip(Exception.class)
                    .skipLimit(2)
                    .build();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

//    @Bean
//    public ItemProcessor<LivreDto,Livre> importProcessor() {
//        return new ItemProcessor<LivreDto,Livre>() {
//
//            @Override
//            public Item process(final LivreDto livreDto) throws Exception {
//                final Item item = Item.builder()
//                        .description(itemDto.getDescription())
//                        .price(itemDto.getPrice())
//                        .build();
//                LOGGER.info(itemDto.toString());
//                return item;
//            }
//        };
//    }

    @StepScope // Mandatory for using jobParameters
    @Bean
    public FlatFileItemReader<LivreDto> importReader(@Value("#{jobParameters['input-file']}") final String inputFile) {
        final FlatFileItemReader<LivreDto> reader = new FlatFileItemReader<>();
        final DefaultLineMapper<LivreDto> lineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setNames(new String[] {"id", "isbn", "titre","auteur","pageNumber","genre"});
        lineMapper.setLineTokenizer(lineTokenizer);

        final BeanWrapperFieldSetMapper<LivreDto> fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(LivreDto.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setResource(new FileSystemResource(inputFile));
        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);

        return reader;
    }
    @Bean
    public ItemWriterAdapter<Livre> importWriter() {
        final ItemWriterAdapter<Livre> writer = new ItemWriterAdapter<Livre>();

        writer.setTargetObject(livreService);
        writer.setTargetMethod("createLivre");

        return writer;
    }

}
