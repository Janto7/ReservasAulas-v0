package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;

public class Profesores {

	// -----> coleccionProfesores (0...*)
	private Profesor[] coleccionProfesores;
	// Tamaño del array
	private int tamano;
	// Capacidad del array
	private int capacidad;

	// Constructor con parametros(capacidad)
	public Profesores(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}

		this.capacidad = capacidad;
		coleccionProfesores = new Profesor[capacidad];

	}

	public Profesor[] get() {
		return copiaProfundaProfesores();
	}

	private Profesor[] copiaProfundaProfesores() {
		Profesor[] copiaProfesores = new Profesor[capacidad];
		for (int i = 0; !tamanoSuperado(i); i++) {
			copiaProfesores[i] = new Profesor(coleccionProfesores[i]);
		}
		return copiaProfesores;
	}

	public int getTamano() {
		return tamano;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void insertar(Profesor profesor) throws OperationNotSupportedException {

		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		// Comprobamos el tamaño no sea superior a la capacidad
		if (capacidadSuperada(tamano)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más profesores.");
		}
		int indice = buscarIndice(profesor);
		if (tamanoSuperado(indice)) {
			coleccionProfesores[tamano] = new Profesor(profesor);
			// Al insertar aumentamos el tamaño
			tamano++;

		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una profesor con ese nombre.");

		}

	}

	// Devolvemos la posición donde se encuentra el profesor
	private int buscarIndice(Profesor profesor) {
		int indice = 0;
		boolean encontrado = false;
		/*
		 * El boleano nos ayuda a no tener que recorrer todo el array en caso de
		 * encontrar el indice antes de llegar al final del tamaño
		 */

		while (!tamanoSuperado(indice) && !encontrado) {
			if (coleccionProfesores[indice].equals(profesor)) {
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

	public Profesor buscar(Profesor profesor) {
		int indice = buscarIndice(profesor);

		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		}

		if (!tamanoSuperado(indice)) {

			return new Profesor(coleccionProfesores[indice]);
		} else {

			return null;
		}

	}

	// Accedemos a la posición del profesor en el array y la borramos desplazando
	// posición.
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		}
		int indice = buscarIndice(profesor);
		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
			// Una vez desplazada la posicion, reducimos el tamaño
			tamano--;

		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese nombre.");
		}

	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		for (int i = indice; i < coleccionProfesores.length - 1; i++) {
			/*
			 * Para desplazar a la izquierda recorrer desde el primero al último en orden
			 * ascendente(+1) (para desplazar a derecha recorrer desde el último al primero
			 * en orden descendente(-1))
			 */
			coleccionProfesores[i] = coleccionProfesores[i + 1];
		}

	}

	// Recorremos todo el tamaño del array y representamos todos sus elementos
	public String[] representar() {

		String[] representacion = new String[tamano];
		for (int i = 0; !tamanoSuperado(i); i++) {
			representacion[i] = coleccionProfesores[i].toString();
		}
		return representacion;
	}
}