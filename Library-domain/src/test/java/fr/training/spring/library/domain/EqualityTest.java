package fr.training.spring.library.domain;

import fr.training.spring.library.domain.enumeration.GenreLitteraire;
import fr.training.spring.library.domain.enumeration.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class EqualityTest {

//    @Autowired
//    private TestRestTemplate testRestTemplate;
//    @Autowired
//    private BibliothequeMapper bibliothequeMapper;
public static final Livre LIVRE_PHILO = new Livre(0L,"ISBN-0001",
        "Les robots","Asimov",100, GenreLitteraire.PHILOSOPHIE);
    public static final Livre LIVRE_FICTION = new Livre(0L,"ISBN-0002",
            "Les plantes","Petit",120, GenreLitteraire.ETUDES);
    public static final Livre LIVRE_HISTOIRE = new Livre(0L,"ISBN-0003",
            "Histoire de France","Jean Favier",500, GenreLitteraire.HISTOIRE);

    public static final Bibliotheque NATIONAL_LIBRARY_MONTREUIL1 = new Bibliotheque(1L, Type.SCOLAIRE,
            new Adresse(93, "Rue des Montreuil", 93100, "Montreuil"), new Directeur("Romain", "NOEL"),
            Arrays.asList(LIVRE_PHILO,LIVRE_FICTION,LIVRE_HISTOIRE)) ;
    public static final Bibliotheque NATIONAL_LIBRARY_MONTREUIL2 = new Bibliotheque(2L, Type.SCOLAIRE,
            new Adresse(93, "Rue des Montreuil", 93100, "Montreuil"), new Directeur("Romain", "NOEL"),
            Arrays.asList(LIVRE_PHILO,LIVRE_FICTION,LIVRE_HISTOIRE)) ;
    @Test
    @DisplayName("Egalité sur la classe Bibliotheque")
    void test_egalite() {
        //---------- Given ------------------
        //---------- Then ------------------
//        ResponseEntity<BibliothequeDto> responseEntity1= testRestTemplate
//                .postForEntity("/create", LIBRARY_EGAL1,BibliothequeDto.class);
//        Bibliotheque bib_EGAL1 = bibliothequeMapper.mapToEntity(responseEntity1.getBody());
//
//        ResponseEntity<BibliothequeDto> responseEntity2= testRestTemplate
//                .postForEntity("/create", LIBRARY_EGAL2, BibliothequeDto.class);
//        Bibliotheque bib_EGAL2 = bibliothequeMapper.mapToEntity(responseEntity2.getBody());
        //---------- Result------------------
//        System.out.println("bib_EGAL1.id="+bib_EGAL1.getId()+"//"+"bib_EGAL2.id="+bib_EGAL2.getId());
//
        assertThat(NATIONAL_LIBRARY_MONTREUIL1).isNotEqualTo(NATIONAL_LIBRARY_MONTREUIL2);
        assertThat(NATIONAL_LIBRARY_MONTREUIL1.equalsValue(NATIONAL_LIBRARY_MONTREUIL2)).isTrue();
    }
}
