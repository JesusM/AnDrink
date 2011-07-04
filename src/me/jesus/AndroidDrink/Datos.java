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

import java.util.List;

public class Datos {
	public static Jugador jugadorModificando;
	public static Jugador turno;
	public static List<Jugador> jugadores;
	public static int getRestantes(){
		int restantes = 0;
		for(int i = 0;i<jugadores.size();i++){
			if(jugadores.get(i).isKO()==false){
				restantes++;
			}
		}
		return restantes;
	}
	public static int getGanador() {
		// TODO Auto-generated method stub
		for(int i = 0;i<jugadores.size();i++){
			if(jugadores.get(i).isKO()==false){
				return i;
			}
		}
		return -1;
	}
	public static void limpiarJugador(Jugador jugador) {
		// TODO Auto-generated method stub
		jugador.setGanador(false);
		jugador.setKO(false);
		jugador.setNbebe(0);
	}
}
