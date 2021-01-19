package fr.training.spring.library.infrastructure.http;

import fr.training.spring.library.domain.Livre;
import fr.training.spring.library.domain.LivreRepository;
import fr.training.spring.library.domain.exception.ErrorCodes;
import fr.training.spring.library.domain.exception.NotFoundException;
import fr.training.spring.library.domain.exception.OpenLibraryTechnicalException;
import fr.training.spring.library.infrastructure.entiteJpa.LivreMapperJpa;
import fr.training.spring.library.infrastructure.http.dto.AuthorInfo;
import fr.training.spring.library.infrastructure.http.dto.BookInfo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class LivreRepositoryImpl implements LivreRepository {

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(LivreRepositoryImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LivreJpaRepository livreJpaRepository;
    @Autowired
    private LivreMapperJpa livreMapperJpa;

    @Override
    public Livre findById(Long id) {
        return livreMapperJpa.mapToEntity(livreJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livre not found", ErrorCodes.BOOK_NOT_FOUND)));
    }

    @Override
    public Livre create(Livre livre) {
        return livreMapperJpa.mapToEntity(livreJpaRepository.save(livreMapperJpa.mapToJpa(livre)));
    }

    @Override
    public Livre searchlivreByISBN(String isbn) {
        try {
            final ResponseEntity<BookInfo> response = restTemplate.getForEntity("/isbn/" + isbn + ".json", BookInfo.class);
            final BookInfo bookInfo = response.getBody();
            Logger.debug(bookInfo.toString());
            System.out.println("urlAuteur="+bookInfo.getAuthors().get(0).getKey());
            String auteur = searchAuteur(bookInfo.getAuthors().get(0).getKey());
            System.out.println("auteur="+auteur);
            return new Livre(null, isbn, bookInfo.getTitle(), auteur, bookInfo.getNumber_of_pages(), null);
        } catch(HttpClientErrorException | HttpServerErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                throw new NotFoundException("Book isbn"+isbn+" not found in openLibrary.org", ErrorCodes.BOOK_NOT_FOUND);
            }
            throw new OpenLibraryTechnicalException(e);
        }
    }

    @Override
    public String searchAuteur(String urlAuteur) {
        String auteur = "Unknown";
        if (urlAuteur.isEmpty()) {
            return auteur;
        }
        try {
            final ResponseEntity<AuthorInfo> response = restTemplate.getForEntity(urlAuteur + ".json", AuthorInfo.class);
            final AuthorInfo authorInfo = response.getBody();
            auteur = authorInfo.getName();
            Logger.info(authorInfo.toString());
            return auteur;
        } catch(HttpClientErrorException | HttpServerErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                throw new NotFoundException("auteur "+urlAuteur+" not found in openLibrary.org", ErrorCodes.AUTEUR_NOT_FOUND);
            }
        }
        return null;
    }
}
