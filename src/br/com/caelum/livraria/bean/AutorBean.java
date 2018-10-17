package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.tx.Transacional;

@Named
@ViewScoped
public class AutorBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer autorId;

	private Autor autor = new Autor();
	
	@Inject
	private AutorDao dao;

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	@PostConstruct
    void init() {
        System.out.println("AutorBean está nascendo ....");
    }

    @PreDestroy
    void morte() {
        System.out.println("AutorBean está morrendo ....");
    }

    @Transacional
	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		
		if (this.autor.getId() == null) {
	        this.dao.adiciona(this.autor);        
	    } else {
	        this.dao.atualiza(this.autor);
	    }
		
		this.autor = new Autor();
		//return "livro?faces-redirect=true";
	}
	
    @Transacional
	public List<Autor> getAutores() {
		System.out.println("Listando Autores");
		return this.dao.listaTodos();		
	}
	
	public void remover(Autor autor) {
	    System.out.println("Removendo Autor " + autor.getNome());
	    this.dao.remove(autor);
	    //this.livros.remove(livro); 
	}
	
    public void carregar(Autor autor) {
        System.out.println("Carregando livro " + autor.getNome());
        this.autor = autor;
    }
    
    public void carregaPelaId() {
             
        Integer id = this.autor.getId();
        this.autor = this.dao.buscaPorId(id);
        if (this.autor == null) {
                this.autor = new Autor();
        }       
        
    }

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
    
    
}
