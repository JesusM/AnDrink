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

import java.util.Random;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkView extends Activity {
	int ind = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// int ind = (int)Math.random()*Datos.jugadores.size();
		int a = Datos.getRestantes();
		if (Datos.getRestantes() > 1) {
			setContentView(R.layout.drinkview);

			Random r = new Random();
			ind = r.nextInt(Datos.jugadores.size());
			while (Datos.jugadores.get(ind).isKO() == true) {
				ind = r.nextInt(Datos.jugadores.size());
			}
			((TextView) findViewById(R.id.nombre_player2))
					.setText(Datos.jugadores.get(ind).getNombre());
			((ImageView) findViewById(R.id.photo_player2))
					.setImageBitmap((Datos.jugadores.get(ind).getImage()));

			((Button) findViewById(R.id.KO))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Datos.jugadores.get(ind).setKO(true);
							actualizarJugador(Datos.jugadores.get(ind));
							startActivity(new Intent(DrinkView.this,
									DrinkView.class));
							finish();
						}
					});
			((Button) findViewById(R.id.listo))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Datos.jugadores.get(ind).setNbebe(
									Datos.jugadores.get(ind).getNbebe() + 1);
							actualizarJugador(Datos.jugadores.get(ind));
							startActivity(new Intent(DrinkView.this,
									DrinkView.class));
							finish();
						}
					});
		} else {
			int indice_ganador = Datos.getGanador();
			setContentView(R.layout.endgame);
			((TextView) findViewById(R.id.nombreGanador)).setText("Ganador: "
					+ Datos.jugadores.get(indice_ganador).getNombre());
			((ImageView) findViewById(R.id.photo_player2))
					.setImageBitmap(Datos.jugadores.get(indice_ganador)
							.getImage());
			((TextView) findViewById(R.id.nombre_player)).setText("Ganador: "
					+ Datos.jugadores.get(indice_ganador).getNombre());
			SharedPreferences settings1 = getSharedPreferences("datos",
					MODE_PRIVATE);
			SharedPreferences.Editor editor = settings1.edit();

			editor.putString("Estado", "nuevo");
			editor.commit();
			for (int i = 0; i < Datos.jugadores.size(); i++) {
				if (i == indice_ganador) {
					Datos.jugadores.get(i).setGanador(true);
					this.actualizarJugador(Datos.jugadores.get(i));
				}

			}
			((Button) findViewById(R.id.KO))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							/*
							 * Limpiar datos de la partida y empezar con mismos
							 * jugadores
							 */
							for (int i = 0; i < Datos.jugadores.size(); i++) {
								Datos.limpiarJugador(Datos.jugadores.get(i));

							}
							startActivity(new Intent(DrinkView.this,
									DrinkView.class));
							finish();
						}
					});
			((Button) findViewById(R.id.listo))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Datos.jugadores = null;
							Datos.jugadorModificando = null;
							Datos.turno = null;
							startActivity(new Intent(DrinkView.this,
									ViewDashboard.class));
							finish();
						}
					});

		}
	}

	private void actualizarJugador(Jugador j) {
		SQliteDB sqlitedb = new SQliteDB(this);

		String id = j.getNombre();

		int vecesbebidas, vecesJugadas, vecesKO, vecesGanadas;
		try {
			ContentValues cv = new ContentValues();
			Cursor c = sqlitedb.getJugador(j.getNombre());
			
			if (c != null) {
				vecesGanadas = Integer.parseInt(c.getString(2));
				vecesKO =  Integer.parseInt(c.getString(3));
				int a = c.getCount();
				if (a > 0) {
					c.moveToFirst();

					
					cv.put(SQliteDB.NOMBRE_JUGADOR, j.getNombre());

					/*
					 * cv.put(SQliteDB.VECES_JUGADAS, String
					 * .valueOf((vecesJugadas + 1)));
					 */
					if (j.isGanador() == true) {
						cv.put(SQliteDB.VECES_GANADAS, String
								.valueOf(vecesGanadas + 1));
					} else {
						cv.put(SQliteDB.VECES_BEBIDAS, String.valueOf((j
								.getNbebe())));
					}
					if (j.isKO()) {
						cv.put(SQliteDB.VECES_KO, String.valueOf(vecesKO + 1));
					}
					sqlitedb.actualizarJugador(j.getNombre(), cv);
				} else {
					int ganador = 0, ko = 0;
					if (j.isGanador()) {
						ganador = 1;
					}
					if (j.isKO()) {
						ko = 1;
					}
					cv.put(sqlitedb.NOMBRE_JUGADOR, j.getNombre());
					cv.put(sqlitedb.VECES_JUGADAS	, 1+"");
					cv.put(sqlitedb.VECES_GANADAS, 0+"");
					cv.put(sqlitedb.VECES_KO, 0+"");
					cv.put(sqlitedb.VECES_BEBIDAS, j.getNbebe()+"");
					sqlitedb.insertarJugador(j.getNombre(),cv);
					/*db.execSQL("INSERT into estadisticas values ('"
							+ j.getNombre() + "' , '" + 1 + "' , '" + ganador
							+ "' , '" + ko + "' , '" + j.getNbebe() + "');");*/
				}
			}

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
		}

		sqlitedb.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int a = item.getItemId();
		switch (a) {
		case R.id.VerEstadistica:
			// InsertarControl();
			/**
			 * Iniciamos que vea la tabla o grafica con sus valores
			 */
			VerEstadisticas();
			break;

		}

		return true;
	}

	private void VerEstadisticas() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, EstadisticasView.class));

	}
}
