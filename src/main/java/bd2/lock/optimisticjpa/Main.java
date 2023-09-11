package bd2.lock.optimisticjpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Main {

	public static void main(String[] args) {

		// 4. Con la BD en read committed y lockeo optimista (version)

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpa-pgsql");

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			int anioActual = 2023;

			TypedQuery<NextNumber2> query = em.createQuery(
					"from NextNumber2 where anio = :anioActual",
					NextNumber2.class);
			query.setParameter("anioActual", anioActual);

			NextNumber2 l = query.getSingleResult();

			System.out.println(l.recuperarSiguiente());

			System.out.println(l.actual());
			System.out.println(l.anio());

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
