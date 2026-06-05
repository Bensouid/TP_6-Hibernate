package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tache")
@NamedQueries({
        @NamedQuery(name = "Tache.findByPrixSup", query = "FROM Tache t WHERE t.prix > :prixSeuil")
})
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    private double prix;

    // Association Many to One m3a Projet
    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    // Association m3a la table intermédiaire EmployeTache
    @OneToMany(mappedBy = "tache", fetch = FetchType.LAZY)
    private List<EmployeTache> affectations;

    // Constructeur vide obligatoire
    public Tache() {}

    // Constructeur pour initialiser les données facilement
    public Tache(String nom, Date dateDebut, Date dateFin, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.projet = projet;
    }

    // Getters et Setters standard
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public Projet getProjet() { return projet; }
    public void setProjet(Projet projet) { this.projet = projet; }
    public List<EmployeTache> getAffectations() { return affectations; }
    public void setAffectations(List<EmployeTache> affectations) { this.affectations = affectations; }
}