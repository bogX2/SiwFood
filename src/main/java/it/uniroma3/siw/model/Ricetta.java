package it.uniroma3.siw.model;

import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ricetta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long  id;
	
	@NotBlank
	private String nome;
	
	
	@Column(length = 2000)
	private String descrizione;
	

	@Lob
	@Column(columnDefinition ="TEXT")
	@NotBlank
	private String immagine;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Cuoco cuoco;
    
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "ricetta_id")
    private List<LineaIngrediente> lineeIngredienti;

    public Ricetta() {
    	this.lineeIngredienti=new LinkedList<>();
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public Cuoco getCuoco() {
		return cuoco;
	}

	public void setCuoco(Cuoco cuoco) {
		this.cuoco = cuoco;
	}

	public List<LineaIngrediente> getLineeIngredienti() {
		return lineeIngredienti;
	}

	public void setLineeIngredenti(List<LineaIngrediente> lineeIngredienti) {
		this.lineeIngredienti = lineeIngredienti;
	}
    
    
    

}
