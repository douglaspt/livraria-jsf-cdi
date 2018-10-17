package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;
import br.com.caelum.livraria.tx.Log;
import br.com.caelum.livraria.tx.Transacional;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	private Integer autorId;

	private List<Livro> livros;
	// https://cursos.alura.com.br/forum/topico-nao-consigo-identificar-o-erro-no-jsf-47627
	// -explicaçao erro
	@Inject
	private LivroDataModel livroDataModel;// = new LivroDataModel();

	@Inject
	private LivroDao livroDao;

	@Inject
	private AutorDao autorDao;

	@Inject
	FacesContext context;

	@PostConstruct
	void init() {
		System.out.println("LivroBean está nascendo ....");
	}

	@PreDestroy
	void morte() {
		System.out.println("LivroBean está morrendo ....");
	}

	public Livro getLivro() {
		return livro;
	}

	public String formAutor() {
		return "autor?faces-redirect=true";
	}

	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			context.addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor"));
			return;
		}

		if (this.livro.getId() == null) {
			this.livroDao.adiciona(this.livro);
			this.livros = livroDao.listaTodos();
		} else {
			this.livroDao.atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	public void gravarAutor() {
		Autor autor = this.autorDao.buscaPorId(this.autorId);
		System.out.println("Gravando autor: " + autor.getNome());
		this.livro.adicionaAutor(autor);
	}

	@Transacional
	public void remover(Livro livro) {
		System.out.println("Removendo livro " + livro.getTitulo());
		this.livroDao.remove(livro);
		// this.livros.remove(livro);
	}

	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	public void carregar(Livro livro) {
		//this.livro = livro; antes
		this.livro = this.livroDao.buscaPorId(livro.getId());
	}
	
	/* --nao utilizada
	public void carregaPelaId() {
		this.livro = this.livroDao.buscaPorId(this.livro.getId());
		if (this.livro == null) {
			this.livro = new Livro();
		}
	}
	*/

	@Log
	public List<Livro> getLivros() {

		if (this.livros == null) {
			this.livros = this.livroDao.listaTodos();
		}

		return livros;
	}

	public List<Autor> getAutores() {
		return this.autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	@Log
	public LivroDataModel getLivroDataModel() {
		return livroDataModel;
	}

}
