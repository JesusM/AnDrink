/**
 * @package pychupitos
 * @version   1.0
 
 * @author    Jesús Manzano Camino
 * @copyright (C) 2011 Jesús Manzano Camino (email : 
 * manzanocaminojesus@gmail.com)
 *
 * @license        GNU/GPL, see LICENSE.php
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

import java.util.Calendar;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQliteDB extends SQLiteOpenHelper {
	/** Called when the activity is first created. */
	private static final String DATABASE_NAME = "pychupitos.db";
	public static final String NOMBRE_JUGADOR = "nombreJugador";
	public static final String VECES_JUGADAS = "vecesJugadas";
	public static final String VECES_GANADAS = "vecesGanadas";
	public static final String VECES_KO = "vecesKO";
	public static final String VECES_BEBIDAS = "vecesBebidas";

	public SQliteDB(Context context) {
		super(context, DATABASE_NAME, null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db
				.execSQL("CREATE TABLE estadisticas (nombreJugador TEXT PRIMARY KEY,vecesJugadas TEXT, vecesGanadas TEXT, vecesKO TEXT,vecesBebidas TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		try {
			db.execSQL("DROP TABLE IF EXISTS estadisticas");			
			onCreate(db);
		} catch (Exception e) {
			@SuppressWarnings("unused")
			String a = e.getMessage();
		}
	}

}