package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public class Reservas {
	// -----> coleccionReservas (0...*)
	private Reserva[] coleccionReservas;
	// Tamaño del array
	private int tamano;
	// Capacidad del array
	private int capacidad;

	// Constructor con parametros(capacidad)
	public Reservas(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}

		this.capacidad = capacidad;
		coleccionReservas = new Reserva[capacidad];

	}

	public Reserva[] get() {
		return copiaProfundaReservas();
	}

	private Reserva[] copiaProfundaReservas() {
		Reserva[] copiaReservas = new Reserva[capacidad];
		for (int i = 0; !tamanoSuperado(i); i++) {
			copiaReservas[i] = new Reserva(coleccionReservas[i]);
		}
		return copiaReservas;
	}

	public int getTamano() {
		return tamano;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void insertar(Reserva reserva) throws OperationNotSupportedException {

		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
		}
		// Comprobamos el tamaño no sea superior a la capacidad
		if (capacidadSuperada(tamano)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más reservas.");
		}
		int indice = buscarIndice(reserva);
		if (tamanoSuperado(indice)) {
			coleccionReservas[tamano] = new Reserva(reserva);
			// Al insertar aumentamos el tamaño
			tamano++;

		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un reserva con ese nombre.");

		}

	}

	// Devolvemos la posición donde se encuentra la reserva
	private int buscarIndice(Reserva reserva) {
		int indice = 0;
		boolean encontrado = false;
		/*
		 * El boleano nos ayuda a no tener que recorrer todo el array en caso de
		 * encontrar el indice antes de llegar al final del tamaño
		 */

		while (!tamanoSuperado(indice) && !encontrado) {
			if (coleccionReservas[indice].equals(reserva)) {
				encontrado = true;
			} else {
				indice++;
			}
		}
		return indice;
	}

	private boolean tamanoSuperado(int indice) {
		// Devuelce true cuando indice es superior o igual a tamano
		return indice >= tamano;
	}

	private boolean capacidadSuperada(int indice) {
		// Devuelce true cuando indice es superior o igual a la capacidad
		return indice >= capacidad;
	}

	public Reserva buscar(Reserva reserva) {
		int indice = buscarIndice(reserva);

		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
		}

		if (!tamanoSuperado(indice)) {

			return new Reserva(coleccionReservas[indice]);
		} else {

			return null;
		}

	}

	// Accedemos a la posición de la reserva en el array y la borramos desplazando
	// posición.
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
		}
		int indice = buscarIndice(reserva);
		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
			// Una vez desplazada la posicion, reducimos el tamaño
			tamano--;

		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna reserva con ese nombre.");
		}

	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		for (int i = indice; i < coleccionReservas.length - 1; i++) {
			/*
			 * Para desplazar a la izquierda recorrer desde el primero al último en orden
			 * ascendente(+1) (para desplazar a derecha recorrer desde el último al primero
			 * en orden descendente(-1))
			 */
			coleccionReservas[i] = coleccionReservas[i + 1];
		}

	}

	// Recorremos todo el tamaño del array y representamos todos sus elementos
	public String[] representar() {

		String[] representacion = new String[tamano];
		for (int i = 0; !tamanoSuperado(i); i++) {
			representacion[i] = coleccionReservas[i].toString();
		}
		return representacion;
	}

	public Reserva[] getReservasProfesor(Profesor profesor) {

		Reserva[] reserva = new Reserva[capacidad];

		int j = 0;
		for (int i = 0; !tamanoSuperado(i); i++) {
			if (coleccionReservas[i].getProfesor().equals(profesor)) {
				reserva[j++] = coleccionReservas[i];
			}
		}
		return reserva;

	}

	public Reserva[] getReservasAula(Aula aula) {
		Reserva[] reserva = new Reserva[capacidad];

		int j = 0;
		for (int i = 0; !tamanoSuperado(i); i++) {
			if (coleccionReservas[i].getAula().equals(aula)) {
				reserva[j++] = coleccionReservas[i];
			}
		}
		return reserva;

	}

	public Reserva[] getReservasPermanencia(Permanencia permanencia) {

		Reserva[] reserva = new Reserva[capacidad];

		int j = 0;
		for (int i = 0; !tamanoSuperado(i); i++) {
			if (coleccionReservas[i].getPermanencia().equals(permanencia)) {
				reserva[j++] = coleccionReservas[i];
			}
		}
		return reserva;

	}

	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {

		boolean disponible = true;

		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		}

		if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}

		for (int i = 0; !tamanoSuperado(i); i++) {
			if (aula.equals(coleccionReservas[i].getAula())
					&& permanencia.equals(coleccionReservas[i].getPermanencia())) {
				disponible = false;
			}

		}

		return disponible;
	}
}
