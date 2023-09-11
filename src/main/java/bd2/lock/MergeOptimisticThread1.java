package bd2.lock;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;

public class MergeOptimisticThread1 {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpa-derby-client");

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			// var post1 = new BlogPost("primer post",
			// "Este es el post número 1...");
			//
			// var post2 = new BlogPost("primer post",
			// "Este es el post número 1...");
			//
			// em.persist(post1);
			// em.persist(post2);

			// var p1 = new BlogPost(1, "primer post cambiado - thread 1",
			// "cambio", 1);
			// em.merge(p1);

			// comparando manualmente
			int versionReadInPreviousTx = 25;
			var p1 = em.find(BlogPost.class, 1L);
			if (!p1.sameVersion(versionReadInPreviousTx)) {
				throw new OptimisticLockException();
			}
			p1.nuevoTitulo("113");

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
			if (emf != null)
				emf.close();
		}
	}

}
