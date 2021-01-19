package fr.training.spring.library.application;

import fr.training.spring.library.domain.Bibliotheque;
import fr.training.spring.library.domain.Livre;
import fr.training.spring.library.domain.LivreRepository;
import fr.training.spring.library.domain.enumeration.GenreLitteraire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;
    @Autowired
    private ServiceBibliotheque serviceBibliotheque;

    public Livre searchlivreByISBN(String isbn) {
        return livreRepository.searchlivreByISBN(isbn);
    }

    public Livre createLivre(Livre livre) {
        return  livreRepository.create(livre);
    }

    public Bibliotheque enregistreLivre(Long bibliothequeId, String isbn, GenreLitteraire genre) {
        Bibliotheque bib = serviceBibliotheque.findById(bibliothequeId);
        Livre livre = searchlivreByISBN(isbn);
        livre.assignGenre(genre);
        bib.addLivre(livre);

//        Livre livreMaj = new Livre(null, livre.getIsbn(), livre.getTitre(),livre.getAuteur(),
//                livre.getPageNumber(),genre);
//        bib.getLivres().add(livreMaj);
        return serviceBibliotheque.update(bib);
    }

    public Livre chercheLivreById(Long id) {
        return livreRepository.findById(id);
    }
}
