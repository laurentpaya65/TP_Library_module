package fr.training.spring.library.batch.exportjob;

import fr.training.spring.library.application.ServiceBibliotheque;
import fr.training.spring.library.domain.Bibliotheque;
import fr.training.spring.library.domain.Livre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
public class BibliothequeProcessor implements ItemProcessor<Long,BibliothequeDto> {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(BibliothequeProcessor.class);

    @Autowired
    private ServiceBibliotheque serviceBibliotheque;

    @Override
    public BibliothequeDto process(final Long bibid) throws Exception {
        final Bibliotheque bibliotheque = serviceBibliotheque.findById(bibid);
        logger.info("processing Bibliotheque {}", bibliotheque);

        List<Livre> livres = bibliotheque.getLivres();
        List<LivreDto> livreDtos = new ArrayList<>();
        for (Livre livre : livres) {
            livreDtos.add(new LivreDto(livre.getId(),livre.getIsbn(),livre.getTitre(),
                    livre.getAuteur(),livre.getPageNumber(),livre.getGenre(),""));
        }

        final BibliothequeDto bibliothequeDto = new BibliothequeDto(bibliotheque.getId(),
                bibliotheque.getType(),
                bibliotheque.getAdresse().getNumero(),
                bibliotheque.getAdresse().getRue(),
                bibliotheque.getAdresse().getCodePostal(),
                bibliotheque.getAdresse().getVille(),
                bibliotheque.getDirecteur().getPrenom(),
                bibliotheque.getDirecteur().getNom(),
                livreDtos
                );
        return bibliothequeDto;
    }
}
