package fr.training.spring.library.batch.exportjob;

import fr.training.spring.library.application.LivreService;
import fr.training.spring.library.application.ServiceBibliotheque;
import fr.training.spring.library.domain.Bibliotheque;
import fr.training.spring.library.domain.Livre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class LivreProcessor implements ItemProcessor<Long,LivreDto> {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(LivreProcessor.class);

    @Autowired
    private LivreService livreService;
    @Autowired
    private ServiceBibliotheque serviceBibliotheque;

    @Override
    public LivreDto process(Long livreId) throws Exception {
        final Livre livre = livreService.chercheLivreById(livreId);
        List<Bibliotheque> bibliothequeList = serviceBibliotheque.findAllByLivreId(livreId);
        String bibId = "( ";
        if (bibliothequeList != null) {
            for (Bibliotheque bibliotheque : bibliothequeList) {
                bibId += bibliotheque.getId() + " ; ";
            }
        }
        bibId += " )";

        logger.info("Livre processing {} ",livre);
        return new LivreDto(livre.getId(),livre.getIsbn(),livre.getTitre(),
                livre.getAuteur(),livre.getPageNumber(),livre.getGenre(),bibId);
    }
}
