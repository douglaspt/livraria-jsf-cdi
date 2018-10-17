package br.com.caelum.livraria.teste;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.dao.JPAUtil;
import br.com.caelum.livraria.modelo.Livro;

public class TestaPesquisaDAO {
	
	public static void main(String[] args) {
		
		EntityManager manager = new JPAUtil().getEntityManager();
		
		DAO<Livro> dao = new DAO<Livro>(manager, Livro.class);
		
		List<Livro> livros = dao.listaTodos();
		
		//List<Livro> livros = dao.listaTodosPaginada(0, 5, "titulo", "me");
		
				
		
		for (Livro livro : livros) {
			System.out.println(livro.getTitulo());
			
		}
		
		
		
		
	}

}
