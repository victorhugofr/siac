package br.com.sigcar.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import br.com.sigcar.dominio.Funcionario;
import br.com.sigcar.util.HibernateUtil;

@Stateless
public class FuncionarioRepositorio {
	@PersistenceContext
	private static EntityManager entityManager = HibernateUtil.getEntityManager();
	public static List<Funcionario> funcionarios;

	
	@SuppressWarnings("unchecked")
	public  Funcionario getFuncionario(String login) {
		EntityTransaction transaction = entityManager.getTransaction();
		if(!transaction.isActive())
			transaction.begin();
		List<Funcionario> retorno = entityManager.createQuery("from Funcionario u where u.login='" + login + "'")
				.getResultList();

		System.out.println(retorno.size());
		for (Funcionario u : retorno) {
			if (u.getLogin().equals(login)) {
				return u;
			}
		}
		return null;
	}

	public void salvar(Funcionario entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		if (!transaction.isActive())
			transaction.begin();
		if(entidade.getId()==0)
			entityManager.persist(entidade);
		else 
			entityManager.merge(entidade);
		transaction.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarUsuarios(){
		List<Funcionario> retorno = entityManager.createQuery("from Funcionario").getResultList();
		funcionarios=retorno;
		return funcionarios;
	}

	public void remover(Funcionario usuarioRemovido) {
		Object id = HibernateUtil.getPrimaryKey(usuarioRemovido);
		
		EntityTransaction transaction = entityManager.getTransaction();
		if (!transaction.isActive())
			transaction.begin();
		
		entityManager.createNativeQuery("delete from funcionario where id ="+id).executeUpdate();
		transaction.commit();
	}
}
