package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;

public class Aulas {

	// Definimos un array de Aula llamado coleccionAulas.
	private Aula[] coleccionAulas;
	// Tamaño del array
	private int tamano;
	// Capacidad del array
	private int capacidad;

	// Constructor con parametros(capacidad)
	public Aulas(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}

		this.capacidad = capacidad;
		coleccionAulas = new Aula[capacidad];

	}

	public Aula[] get() {
		return copiaProfundaAulas();
	}

	private Aula[] copiaProfundaAulas() {
		Aula[] copiaAulas = new Aula[capacidad];
		for (int i = 0; !tamanoSuperado(i); i++) {
			copiaAulas[i] = new Aula(coleccionAulas[i]);
		}
		return copiaAulas;
	}

	public int getTamano() {
		return tamano;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void insertar(Aula aula) throws OperationNotSupportedException {

		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		}
		// Comprobamos el tamaño no sea superior a la capacidad
		if (capacidadSuperada(tamano)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más aulas.");
		}
		int indice = buscarIndice(aula);
		if (tamanoSuperado(indice)) {
			coleccionAulas[tamano] = new Aula(aula);
			// Al insertar aumentamos el tamaño
			tamano++;

		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");

		}

	}

	// Devolvemos la posición donde se encuentra el aula
	private int buscarIndice(Aula aula) {
		int indice = 0;
		boolean encontrado = false;
		/*
		 * El boleano nos ayuda a no tener que recorrer todo el array en caso de
		 * encontrar el indice antes de llegar al final del tamaño
		 */

		while (!tamanoSuperado(indice) && !encontrado) {
			if (coleccionAulas[indice].equals(aula)) {
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

	public Aula buscar(Aula aula) {
		int indice = buscarIndice(aula);

		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		}

		if (!tamanoSuperado(indice)) {

			return new Aula(coleccionAulas[indice]);
		} else {

			return null;
		}

	}

	// Accedemos a la posición del aula en el array y la borramos desplazando
	// posición.
	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		}
		int indice = buscarIndice(aula);
		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
			// Una vez desplazada la posicion, reducimos el tamaño
			tamano--;

		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
		}

	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		for (int i = indice; i < coleccionAulas.length - 1; i++) {
			/*
			 * Para desplazar a la izquierda recorrer desde el primero al último en orden
			 * ascendente(+1) (para desplazar a derecha recorrer desde el último al primero
			 * en orden descendente(-1))
			 */
			coleccionAulas[i] = coleccionAulas[i + 1];
		}

	}

	// Recorremos todo el tamaño del array y representamos todos sus elementos
	public String[] representar() {

		String[] representacion = new String[tamano];
		for (int i = 0; !tamanoSuperado(i); i++) {

			representacion[i] = coleccionAulas[i].toString();

		}
		return representacion;
	}
}