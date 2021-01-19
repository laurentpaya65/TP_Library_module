package fr.training.spring.library.domain;

public interface LivreRepository {

    Livre searchlivreByISBN(String isbn);

    String searchAuteur(String idAuteur);
}
