package fr.training.spring.library.batch.exportjob;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.training.spring.library.domain.enumeration.GenreLitteraire;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

public class LivreDto implements Serializable {

    private Long id;

    private String isbn;
    private String titre;
    private String auteur;
    private int pageNumber;
    private GenreLitteraire genre;
    private String biblioIds;

    public LivreDto() {
    }

    public LivreDto(Long id, String isbn, String titre, String auteur, int pageNumber, GenreLitteraire genre, String biblioIds) {
        this.id = id;
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.pageNumber = pageNumber;
        this.genre = genre;
        this.biblioIds = biblioIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public GenreLitteraire getGenre() {
        return genre;
    }

    public void setGenre(GenreLitteraire genre) {
        this.genre = genre;
    }

    public String getBiblioIds() {
        return biblioIds;
    }

    public void setBiblioIds(String biblioIds) {
        this.biblioIds = biblioIds;
    }
}
