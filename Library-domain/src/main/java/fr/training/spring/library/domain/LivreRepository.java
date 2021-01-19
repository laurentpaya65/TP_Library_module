package fr.training.spring.library.domain;

public interface LivreRepository {

    Livre create(Livre livre);

    Livre searchlivreByISBN(String isbn);

    String searchAuteur(String idAuteur);

    Livre findById(Long id);
}
