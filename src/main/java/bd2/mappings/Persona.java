package bd2.mappings;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Persona {

	@Id
	private Long id;
	private String nombre;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona")
	private List<Telefono> telefonos = new ArrayList<>();

	private LocalDate fechaNac;

	@Embedded
	private Address direccion;

	// @OneToOne(cascade = CascadeType.PERSIST)
	// OneToone: pone unique la columna id_dni en la tabla persona
	// many to one: no pone ningun constraint
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Dni dni;

	public Persona(Long id, String nombre, String direccion,
			LocalDate fechaNacimiento, Dni dni) {
		// validate all !
		this.id = id;
		this.nombre = nombre;
		this.direccion = new Address(direccion);
		this.fechaNac = fechaNacimiento;
		this.dni = dni;
	}

	public void printTelefonos() {
		for (Telefono telefono : telefonos) {
			System.out.println(telefono.toString());
		}
	}

	protected Persona() {

	}

	public void direccion(String dir) {
		this.direccion = new Address(dir);
	}

	public void fechaNacimiento(LocalDate fecha) {
		this.fechaNac = fecha;
	}

	public void addTelefono(Telefono t) {
		this.telefonos.add(t);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private List<Telefono> getTelefonos() {
		return telefonos;
	}

	private void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}

	private long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	private LocalDate getFecha() {
		return fechaNac;
	}

	private void setFecha(LocalDate fecha) {
		this.fechaNac = fecha;
	}

	private Address getDireccion() {
		return direccion;
	}

	private void setDireccion(Address direccion) {
		this.direccion = direccion;
	}

	public boolean seLlama(String unNombre) {
		return this.nombre.equals(unNombre);
	}

	public boolean viveEn(String unaDireccion) {
		return this.direccion.is(unaDireccion);
	}

	public boolean suTelefonoEs(String unTelefono) {
		return this.telefonos.stream().anyMatch(t -> t.is(unTelefono));
	}
}
