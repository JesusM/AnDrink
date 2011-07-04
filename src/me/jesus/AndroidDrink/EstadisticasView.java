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

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EstadisticasView extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.estadisticasview);
		ListView a = (ListView) findViewById(R.id.lista);
		Top25Adapter ta = new Top25Adapter(this);
		a.setAdapter(ta);
	}

	public class Top25Adapter extends BaseAdapter {

		private Activity c;
		private Cursor cursor;

		public Top25Adapter(Activity context) {
			this.c = context;
			SQliteDB sqlitedb = new SQliteDB(EstadisticasView.this);
			SQLiteDatabase db = sqlitedb.getReadableDatabase();
			String sentenciaSelect = "SELECT  nombreJugador as nombre, vecesJugadas as vecesjugadas, vecesGanadas as vecesganadas, vecesKO"
					+ " as vecesko,vecesBebidas as vecesbebidas FROM estadisticas ORDER BY nombreJugador ;";
			try {
				this.cursor = db.rawQuery(sentenciaSelect, null);
				cursor.moveToFirst();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(),
						Toast.LENGTH_LONG).show();
			}

			db.close();
		}

		@Override
		public int getCount() {

			int a = cursor.getCount();
			//return cursor.getCount() + 1;
			return a;
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			LayoutInflater inflater = c.getLayoutInflater();
			View item = null;
			item = inflater.inflate(R.layout.list_element, null);
			/*if (arg0 == 0) {
				
				((TextView) item.findViewById(R.id.textviewlistelement1))
						.setText("Nombre");
				((TextView) item.findViewById(R.id.textviewlistelement1))
						.setTextColor(Color.WHITE);

				((TextView) item.findViewById(R.id.textviewlistelement2))
						.setTextColor(Color.WHITE);

				((TextView) item.findViewById(R.id.textviewlistelement3))
						.setTextColor(Color.WHITE);
				((TextView) item.findViewById(R.id.textviewlistelement4))
						.setTextColor(Color.WHITE);
				((TextView) item.findViewById(R.id.textviewlistelement5))
						.setTextColor(Color.WHITE);

				((TextView) item.findViewById(R.id.textviewlistelement2))
						.setText("Veces jugadas");
				((TextView) item.findViewById(R.id.textviewlistelement3))
						.setText("Veces ganadas");
				((TextView) item.findViewById(R.id.textviewlistelement4))
						.setText("KO");
				((TextView) item.findViewById(R.id.textviewlistelement5))
						.setText("Nº tragos");

				((LinearLayout) item.findViewById(R.id.list_element_layout))
						.setBackgroundColor(Color.DKGRAY);

			} else {*/
				// int ind = arg0-1;

				cursor.moveToPosition(arg0);
				// cursor.move(ind);
				String nombre;
				int vecesbebidas, vecesJugadas, vecesKO, vecesGanadas;
				try {
					vecesbebidas = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex("vecesbebidas")));
					vecesJugadas = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex("vecesjugadas")));
					vecesKO = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex("vecesko")));
					vecesGanadas = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex("vecesganadas")));
					nombre = cursor.getString(cursor.getColumnIndex("nombre"));
					((TextView) item.findViewById(R.id.textviewlistelement1))
							.setText(nombre);
					((TextView) item.findViewById(R.id.textviewlistelement2))
							.setText(vecesJugadas + "");
					((TextView) item.findViewById(R.id.textviewlistelement3))
							.setText(vecesGanadas + "");
					((TextView) item.findViewById(R.id.textviewlistelement4))
							.setText(vecesKO + "");
					((TextView) item.findViewById(R.id.textviewlistelement5))
							.setText(vecesbebidas + "");
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
				}

			//}
			return item;
		}

	}

}
