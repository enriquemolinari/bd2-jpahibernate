package bd2.mappings;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;

@Entity
@Inheritance
@DiscriminatorColumn(name = "tipo_cuenta")
public class CuentaBancaria {

	@Id
	@GeneratedValue
	private long id;

	private float saldo;

	protected CuentaBancaria() {
	}

	protected CuentaBancaria(float monto) {
		this.saldo = monto;
	}

	private long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	private float getSaldo() {
		return saldo;
	}

	private void setSaldo(float saldo) {
		this.saldo = saldo;
	}
}
