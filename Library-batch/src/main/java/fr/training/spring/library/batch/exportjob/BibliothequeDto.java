package fr.training.spring.library.batch.exportjob;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.training.spring.library.domain.enumeration.Type;
import fr.training.spring.library.domain.exception.ErrorCodes;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BibliothequeDto implements Serializable {

    private Long id;

    private Type type;

    private int numero;
    private String rue;
    private int codePostal;
    private String ville;

    private String prenom;
    private String nom;

    private List<LivreDto> livreDtos = new ArrayList<>();

    public BibliothequeDto() {}

    public BibliothequeDto(Long id, Type type, int numero, String rue, int codePostal, String ville, String prenom, String nom, List<LivreDto> livreDtos) {
        this.id = id;
        this.type = type;
        this.numero = numero;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.prenom = prenom;
        this.nom = nom;
        this.livreDtos = livreDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<LivreDto> getLivreDtos() {
        return livreDtos;
    }

    public void setLivreDtos(List<LivreDto> livreDtos) {
        this.livreDtos = livreDtos;
    }

}
