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
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EstadisticasView extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.estadisticasview);
		ListView a = (ListView) findViewById(R.id.lista);
		Top25Adapter ta = new Top25Adapter(this);
		a.setAdapter(ta);
		findViewById(R.id.searchEstadistica).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						startActivity(new Intent(EstadisticasView.this,
								Search.class));
					}
				});

	}

	public class Top25Adapter extends BaseAdapter {

		private Activity c;
		private Cursor cursor;
		private SQliteDB sqlitedb;

		public Top25Adapter(Activity context) {
			this.c = context;

			sqlitedb = new SQliteDB(EstadisticasView.this);

			// String selection = sqlitedb.NOMBRE_JUGADOR + " = ?";
			String selection = "*";

			String[] columns = new String[] { sqlitedb.NOMBRE_JUGADOR,
					sqlitedb.VECES_JUGADAS, sqlitedb.VECES_GANADAS,
					sqlitedb.VECES_KO, sqlitedb.VECES_BEBIDAS };

			try {

				cursor = sqlitedb.query("*", columns);
				if (cursor != null) {
					if (cursor.getCount() != -1) {
						cursor.moveToFirst();
					}
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(),
						Toast.LENGTH_LONG).show();
			}

			sqlitedb.close();
		}

		@Override
		public int getCount() {
			return cursor == null ? 0 : cursor.getCount();
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
			if (cursor != null) {
				cursor.moveToPosition(arg0);
				// cursor.move(ind);
				String nombre;
				int vecesbebidas, vecesJugadas, vecesKO, vecesGanadas;
				try {
					vecesbebidas = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex(cursor.getColumnName(4))));
					vecesJugadas = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex(cursor.getColumnName(1))));
					vecesKO = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex(cursor.getColumnName(3))));
					vecesGanadas = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex(cursor.getColumnName(2))));
					nombre = cursor.getString(cursor.getColumnIndex(cursor
							.getColumnName(0)));
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
			}
			return item;
		}

	}

}
