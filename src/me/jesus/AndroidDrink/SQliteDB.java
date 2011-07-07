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

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class SQliteDB {
	/** Called when the activity is first created. */
	private static final String DATABASE_NAME = "pychupitos";
	public final static String NOMBRE_JUGADOR = "nombreJugador";
	public final static String VECES_JUGADAS = "vecesJugadas";
	public static final String VECES_GANADAS = "vecesGanadas";
	public static final String VECES_KO = "vecesKO";
	public static final String VECES_BEBIDAS = "vecesBebidas";
	public final String TABLE_NAME = "estadisticas";
	private final BBDD mDatabase;

	/**
	 * Constructor
	 * 
	 * @param context
	 *            The Context within which to work, used to create the DB
	 */
	public SQliteDB(Context context) {
		mDatabase = new BBDD(context);
	}

	/**
	 * Returns a Cursor positioned at the word specified by rowId
	 * 
	 * @param rowId
	 *            id of word to retrieve
	 * @param columns
	 *            The columns to include, if null then all are included
	 * @return Cursor positioned to matching word, or null if not found.
	 */
	public Cursor getJugador(String nombre, String[] columns) {
		String selection = NOMBRE_JUGADOR + " = ?";
		String[] selectionArgs = new String[] { nombre };

		return query(selection, selectionArgs, columns);

		/*
		 * This builds a query that looks like: SELECT <columns> FROM <table>
		 * WHERE rowid = <rowId>
		 */
	}

	public Cursor getJugador(String nombre) {
		return getJugador(nombre, new String[] { NOMBRE_JUGADOR, VECES_JUGADAS,
				VECES_GANADAS, VECES_KO, VECES_BEBIDAS });
	}

	public long addJugador(String nombreJ, String veces_bebidas) {
		return mDatabase.addJugador(nombreJ, veces_bebidas);
	}

	public int actualizarJugador(String nombre, ContentValues cv) {
		return mDatabase.actualizarJugador(nombre, cv);
	}

	/**
	 * Performs a database query.
	 * 
	 * @param selection
	 *            The selection clause
	 * @param selectionArgs
	 *            Selection arguments for "?" components in the selection
	 * @param columns
	 *            The columns to return
	 * @return A Cursor over all rows matching the query
	 */
	public Cursor query(String selection, String[] selectionArgs,
			String[] columns) {
		/*
		 * The SQLiteBuilder provides a map for all possible columns requested
		 * to actual columns in the database, creating a simple column alias
		 * mechanism by which the ContentProvider does not need to know the real
		 * column names
		 */
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(TABLE_NAME);
		Cursor cursor = mDatabase.getReadableDatabase().query(TABLE_NAME,
				columns, selection, selectionArgs, null, null, null);
		
		

		if (cursor == null) {
			return null;
		} else if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}
		return cursor;
	}
	
	
	/**
	 * Performs a database query.
	 * 
	 * @param selection
	 *            The selection clause
	 * @param selectionArgs
	 *            Selection arguments for "?" components in the selection
	 * @param columns
	 *            The columns to return
	 * @return A Cursor over all rows matching the query
	 */
	public Cursor query(String nombreJ, 
			String [] columns) {
		/*
		 * The SQLiteBuilder provides a map for all possible columns requested
		 * to actual columns in the database, creating a simple column alias
		 * mechanism by which the ContentProvider does not need to know the real
		 * column names
		 */
		String selection = SQliteDB.NOMBRE_JUGADOR+" like "+ " '"+nombreJ+"';";
		Cursor cursor = mDatabase.getReadableDatabase().query(TABLE_NAME, columns, selection, null, null, null, null);
		
		

		if (cursor == null) {
			return null;
		} else if (!cursor.moveToFirst()) {
			cursor.close();
			return cursor;
		}
		return null;
	}

	public class BBDD extends SQLiteOpenHelper {
		private SQLiteDatabase mSQliteDB;

		public BBDD(Context context) {
			super(context, DATABASE_NAME, null, 2);
		}

		public long insert(String tableName, Object object,
				ContentValues initialValues) {
			// TODO Auto-generated method stub
			return mSQliteDB.insert(tableName, null, initialValues);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			mSQliteDB = db;
			mSQliteDB
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

		public long addJugador(String nombre_jug, String veces_bebidas) {
			return addJugador(nombre_jug, 1, 0, 0, veces_bebidas);
		}

		private long addJugador(String nombreJug, int i, int j, int k,
				String vecesBebidas) {
			// TODO Auto-generated method stub
			ContentValues initialValues = new ContentValues();
			initialValues.put(NOMBRE_JUGADOR, nombreJug);
			initialValues.put(VECES_JUGADAS, i);
			initialValues.put(VECES_GANADAS, j);
			initialValues.put(VECES_KO, k);
			initialValues.put(VECES_BEBIDAS, vecesBebidas);
			return mSQliteDB.insert(TABLE_NAME, null, initialValues);
		}

		public Cursor searchJugador(String nombre) {
			boolean open = mSQliteDB.isOpen();
			String selection = NOMBRE_JUGADOR + " = ?";
			String[] selectionArgs = new String[] { nombre };
			SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
			builder.setTables(TABLE_NAME);
			String[] columns = new String[] { NOMBRE_JUGADOR };

			/*
			 * Cursor cursor = builder.query(this.mSQliteDB, columns, selection,
			 * selectionArgs, null, null, null);
			 */

			Cursor cursor = mSQliteDB.query(TABLE_NAME, columns, selection,
					selectionArgs, null, null, null);

			if (cursor == null) {
				return null;
			} else if (!cursor.moveToFirst()) {
				cursor.close();
				return null;
			}
			return cursor;

		}

		protected int actualizarJugador(String nombre, ContentValues cv) {
			// TODO Auto-generated method stub
			return mSQliteDB.update("estadisticas", cv, SQliteDB.NOMBRE_JUGADOR
					+ "=?", new String[] { nombre });
		}
	}

	public void close() {
		// TODO Auto-generated method stub
		mDatabase.close();
	}

	public void onUpgrade(SQliteDB sqlitedb, int i, int j) {
		// TODO Auto-generated method stub
		mDatabase.onUpgrade(mDatabase.getWritableDatabase(), 0, 0);
	}

	public int insertarJugador(String nombre, ContentValues cv) {
		// TODO Auto-generated method stub
		return mDatabase.actualizarJugador(nombre, cv);
	}
}