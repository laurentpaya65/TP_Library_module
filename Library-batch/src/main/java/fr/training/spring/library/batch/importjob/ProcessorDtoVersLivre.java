package fr.training.spring.library.batch.importjob;

import fr.training.spring.library.batch.exportjob.LivreDto;
import fr.training.spring.library.domain.Livre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ProcessorDtoVersLivre implements ItemProcessor<LivreDto, Livre> {
    private static final Logger logger = LoggerFactory.getLogger(ProcessorDtoVersLivre.class);

    @Override
    public Livre process(LivreDto livreDto) throws Exception {
        return new Livre(null,livreDto.getIsbn(),livreDto.getTitre(),
                livreDto.getAuteur(),livreDto.getPageNumber(),livreDto.getGenre());
    }
}
