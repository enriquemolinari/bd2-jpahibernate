package bd2.lock;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Main {

	public static void main(String[] args) {

		// 1. Con la BD en read committed
		// 2. con la BD en repeatable read
		// 3. Con la BD en read committed y lockeo pesismista (select for
		// update)

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpa-pgsql");

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			int anioActual = 2023;

			TypedQuery<NextNumber> query = em.createQuery(
					"from NextNumber where anio = :anioActual",
					NextNumber.class);
			query.setParameter("anioActual", anioActual);

			// select for update
			// https://www.postgresql.org/docs/current/explicit-locking.html
			// select for key update is a bit weaker than select for update
			// query.setLockMode(LockModeType.PESSIMISTIC_WRITE);

			NextNumber l = query.getSingleResult();

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
