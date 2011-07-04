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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Inicio extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		//Datos.jugadores.clear();
		//Datos.jugadorModificando=null;
		((Button) findViewById(R.id.Entrar))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (!(((EditText) findViewById(R.id.num_players))
								.getText().toString().equals("") || Integer
								.parseInt(((EditText) findViewById(R.id.num_players))
										.getText().toString())<=1)) {
							SharedPreferences settings = getSharedPreferences(
									"datos", MODE_PRIVATE);
							SharedPreferences.Editor editor = settings.edit();

							editor.putString("NJugadores",
									((EditText) findViewById(R.id.num_players))
											.getText().toString());
							editor.commit();
							if(Datos.jugadores!=null)
								Datos.jugadores=null;
							startActivity(new Intent(Inicio.this,
									ListadoInicial.class));
							finish();
						} else {
							Toast.makeText(getApplicationContext(),
									"Debes elegir un nª de jugadores",
									Toast.LENGTH_LONG).show();
						}
					}
				});
	}
}