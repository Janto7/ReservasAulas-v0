package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.Vista;

public class Controlador {

	private Vista vista;
	private Modelo modelo;

	public Controlador(Modelo modelo, Vista vista) {
		if (modelo == null) {
			throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
		}
		if (vista == null) {
			throw new NullPointerException("ERROR: La vista no puede ser nula.");
		}
		this.modelo = modelo;
		this.vista = vista;
		this.vista.setControlador(this);
	}

	public void comenzar() {
		vista.comenzar();
	}

	public void terminar() {
		System.out.println("Hasta luego!!!");
	}

	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		modelo.insertar(aula);
	}

	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		modelo.insertar(profesor);
	}

	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		modelo.borrar(aula);
	}

	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		modelo.borrar(profesor);
	}

	public Aula buscarAula(Aula aula) {
		return modelo.buscar(aula);
	}

	public Profesor buscarProfesor(Profesor profesor) {
		return modelo.buscar(profesor);
	}

	public String[] representarAulas() {
		return modelo.representarAulas();
	}

	public String[] representarProfesores() {
		return modelo.representarProfesores();
	}

	public String[] representarReservas() {
		return modelo.representarReservas();
	}

	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.realizarReserva(reserva);
	}

	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.anularReserva(reserva);
	}

	public Reserva[] getReservasAula(Aula aula) {
		return modelo.getReservasAulas(aula);
	}

	public Reserva[] getReservasProfesor(Profesor profesor) {
		return modelo.getReservasProfesor(profesor);
	}

	public Reserva[] getReservasPermanencia(Permanencia permanencia) {
		return modelo.getReservasPermanencia(permanencia);
	}

	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		return modelo.consultarDisponibilidad(aula, permanencia);
	}
}
