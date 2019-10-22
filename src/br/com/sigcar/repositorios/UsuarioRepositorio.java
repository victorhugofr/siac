package br.com.sigcar.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import br.com.sigcar.dominio.Usuario;
import br.com.sigcar.util.HibernateUtil;
@Stateless
public class UsuarioRepositorio {
	@PersistenceContext
	private static EntityManager entityManager = HibernateUtil.getEntityManager();
	public static List<Usuario> usuarios;

	
	@SuppressWarnings("unchecked")
	public  Usuario getUsuario(String login) {
		EntityTransaction transaction = entityManager.getTransaction();
		if(!transaction.isActive())
			transaction.begin();
		List<Usuario> retorno = entityManager.createQuery("from Usuario u where u.login='" + login + "'")
				.getResultList();

		System.out.println(retorno.size());
		for (Usuario u : retorno) {
			if (u.getLogin().equals(login)) {
				return u;
			}
		}
		return null;
	}

	public void salvar(Usuario entidade) {
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
	public List<Usuario> listarUsuarios(){
		List<Usuario> retorno = entityManager.createQuery("from Usuario").getResultList();
		usuarios=retorno;
		return usuarios;
	}

	public void remover(Usuario usuarioRemovido) {
		Object id = HibernateUtil.getPrimaryKey(usuarioRemovido);
		
		EntityTransaction transaction = entityManager.getTransaction();
		if (!transaction.isActive())
			transaction.begin();
		
		entityManager.createNativeQuery("delete from usuario where id ="+id).executeUpdate();
		transaction.commit();
	}
}
