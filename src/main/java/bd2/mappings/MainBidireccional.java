package bd2.mappings;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class MainBidireccional {

	public static void main(String args[]) {
		EntityManagerFactory emf = Persistence
				// .createEntityManagerFactory("jpa-objectdb");
				.createEntityManagerFactory("jpa-derby-client");
		// .createEntityManagerFactory("jpa-derby-embedded");
		// .createEntityManagerFactory("jpa-pgsql");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			var post = new Post("titulo", "texto...");
			post.addComment("commentario 1");
			post.addComment("commentario 2");

			em.persist(post);

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
