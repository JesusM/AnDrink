/**
 * @package pychupitos
 * @version   1.0
 
 * @author    Jesús Manzano Camino
 * @copyright (C) 2011 Jesús Manzano Camino (email : 
 * manzanocaminojesus@gmail.com)
 *
 * @license        GNU/GPL, see gpl.txt
 * This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 */

package me.jesus.AndroidDrink;

import android.graphics.Bitmap;

public class Jugador implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4180513538583033464L;
	private String nombre;
	private Bitmap image;
	private int nbebe;
	private boolean KO;
	private boolean ganador;
	public Jugador(String nombre,Bitmap image){
		this.setNombre(nombre);
		this.setImage(image);
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setNbebe(int nbebe) {
		this.nbebe = nbebe;
	}
	public int getNbebe() {
		return nbebe;
	}
	public void setKO(boolean kO) {
		KO = kO;
	}
	public boolean isKO() {
		return KO;
	}
	public void setGanador(boolean ganador) {
		this.ganador = ganador;
	}
	public boolean isGanador() {
		return ganador;
	}
}
