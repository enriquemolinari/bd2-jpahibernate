package bd2.lock;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Main {

	public static void main(String[] args) {

		// 1. select for update (pessimistic)
		// 2. constraints y retrys de la Tx (optimistic)
		// 3. con MVCC y serializable en PG + retrys de la Tx (optimistic)
		// 4. Optimistic vs Pessimistic en long-runnning conversations o
		// application-level
		// transaction

		// solo serializable detecta este problema sin usar algun mecanismo de
		// concurrencia
		// pesimista o optimista

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
