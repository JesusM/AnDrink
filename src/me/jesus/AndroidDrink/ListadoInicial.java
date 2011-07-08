package me.jesus.AndroidDrink;

/**
 * @package AndroidDrink
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class ListadoInicial extends Activity {
	boolean show_inst = true;
	final ActionItem first = new ActionItem();
	final ActionItem second = new ActionItem();
	final ActionItem third = new ActionItem();
	final ActionItem instruction = new ActionItem();
	QuickAction qa = null;
	int indice_lista = -1;
	boolean mPreviewRunning = false;
	private static final int CAMERA_RESULT = 1;
	private Uri mUri;
	private String path;
	private String DIRECTORY_NAME = "/mnt/sdcard/andrink/";
	Dialog dialogo;
	int num_jug = 0;
	SQliteDB sqlitedb = new SQliteDB(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setContentView(R.layout.listadoinicial);
		setContentView(R.layout.pre_listado_inicial);

		findViewById(R.id.forward).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Integer.parseInt(((EditText) findViewById(R.id.num_jug))
						.getText().toString()) > 0
						&& !((EditText) findViewById(R.id.num_jug)).getText()
								.toString().equals("")) {
					SharedPreferences settings = getSharedPreferences("datos",
							MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();

					editor.putString("NJugadores",
							((EditText) findViewById(R.id.num_jug)).getText()
									.toString());
					editor.commit();
					setContentView(R.layout.listadoinicial);
					cargarInterfazListado();
				}
			}
		});

	}

	public void cargarInterfazListado() {
		IniciarQuickActions();

		if (Datos.jugadores == null)
			crearJugadores();

		((ListView) findViewById(R.id.listadoinicial))
				.setAdapter(new ListadoAdapter(this));

		ImageView b = (ImageView) this.findViewById(R.id.godrink);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				SharedPreferences settings = getSharedPreferences("datos",
						MODE_PRIVATE);
				num_jug = Integer.parseInt(settings.getString("veces jugadas",
						"1"));
				if (num_jug == 1) {
					if (show_inst == true) {
						showInstrucciones(arg0);
						show_inst = false;
					} else {
						for (int i = 0; i < Datos.jugadores.size(); i++) {
							insertarJugador(Datos.jugadores.get(i));
						}
						startActivity(new Intent(ListadoInicial.this,
								DrinkView.class));
						finish();
					}

				} else {
					for (int i = 0; i < Datos.jugadores.size(); i++) {
						insertarJugador(Datos.jugadores.get(i));
					}
					startActivity(new Intent(ListadoInicial.this,
							DrinkView.class));
					finish();
				}

			}
		});

		((ListView) findViewById(R.id.listadoinicial))
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View v,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						try {

							/*
							 * Intent intent = new Intent(ListadoInicial.this,
							 * PlayerView.class);
							 * 
							 * intent.putExtra("indice", arg2);
							 * startActivity(intent); finish();
							 */
							indice_lista = arg2;
							qa = new QuickAction(v);

							qa.addActionItem(first);
							qa.addActionItem(second);
							qa.addActionItem(third);

							qa.show(true);

						} catch (Exception e) {
							Toast.makeText(getApplicationContext(), "error",
									Toast.LENGTH_LONG).show();
						}

					}
				});
	}

	public void showInstrucciones(View v) {
		// TODO Auto-generated method stub
		instruction.setTitle("Presione otra vez para comenzar");
		// first.setIcon(getResources().getDrawable(R.drawable.ic_menu_edit));
		instruction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}
		});
		final QuickAction qa = new QuickAction(v);
		qa.addActionItem(instruction);
		qa.show(false);

		new CountDownTimer(2000, 1000) {

			public void onTick(long millisUntilFinished) {
			}

			public void onFinish() {
				qa.dismiss();
			}
		}.start();

	}

	private void IniciarQuickActions() {
		// TODO Auto-generated method stub

		first.setTitle("Cambiar nombre");
		first.setIcon(getResources().getDrawable(R.drawable.ic_menu_edit));
		first.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Dashboard",
						Toast.LENGTH_SHORT).show();
				dialogo = new Dialog(ListadoInicial.this);
				dialogo.setContentView(R.layout.custom_dialog);
				dialogo.setTitle("Inserte un nombre");
				dialogo.show();
				((Button) dialogo.findViewById(R.id.Aceptar))
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								if (!((EditText) dialogo
										.findViewById(R.id.new_player_name))
										.getText().toString().equals("")) {
									Datos.jugadores
											.get(indice_lista)
											.setNombre(
													((EditText) dialogo
															.findViewById(R.id.new_player_name))
															.getText()
															.toString());
									((ListView) findViewById(R.id.listadoinicial))
											.setAdapter(new ListadoAdapter(
													ListadoInicial.this));

									dialogo.dismiss();
									qa.dismiss();
								} else {
									Toast.makeText(getApplicationContext(),
											"debe introducir un nombre",
											Toast.LENGTH_LONG).show();

								}
							}
						});
			}
		});

		second.setTitle("Cambiar foto");
		second.setIcon(getResources().getDrawable(R.drawable.camera_icon));
		second.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ListadoInicial.this, "User & Group",
						Toast.LENGTH_SHORT).show();
				doTakePhotoAction();
				qa.dismiss();

				// startActivity(new Intent(ListadoInicial.this,
				// PlayerView.class));
			}
		});

		third.setTitle("Borrar");
		third.setIcon(getResources().getDrawable(R.drawable.delete));
		third.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ListadoInicial.this, "User & Group",
						Toast.LENGTH_SHORT).show();
				borrarJugador();
				qa.dismiss();
			}

		});
	}

	private void borrarJugador() {
		// TODO Auto-generated method stub
		Datos.jugadores.remove(this.indice_lista);
		((ListView) findViewById(R.id.listadoinicial))
				.setAdapter(new ListadoAdapter(ListadoInicial.this));
		// ((Button) findViewById(R.id.godrink)).setText("A beber");
	}

	private void crearJugadores() {
		// TODO Auto-generated method stub
		SharedPreferences settings = getSharedPreferences("datos", MODE_PRIVATE);
		String b = settings.getString("NJugadores", "0");
		int njug = Integer.parseInt(b);
		// Datos.jugadores.clear();
		Datos.jugadores = new ArrayList<Jugador>();
		for (int i = 0; i < njug; i++) {
			Jugador a = new Jugador("Jugador " + i, BitmapFactory
					.decodeResource(getResources(),
							R.drawable.ic_contact_picture));
			a.setNbebe(0);
			a.setKO(false);
			Datos.jugadores.add(a);
		}
	}

	public class ListadoAdapter extends BaseAdapter {

		private Activity c;

		public ListadoAdapter(Activity context) {
			this.c = context;

		}

		public int getCount() {
			return Datos.jugadores.size();
		}

		public Object getItem(int arg0) {
			return arg0;
		}

		public long getItemId(int arg0) {
			return arg0;
		}

		public View getView(int i, View arg1, ViewGroup arg2) {
			LayoutInflater inflater = c.getLayoutInflater();
			View item = null;
			item = inflater.inflate(R.layout.elemento_lista, null);
			String nombre = Datos.jugadores.get(i).getNombre();
			((TextView) item.findViewById(R.id.nombre)).setText(nombre);
			((ImageView) item.findViewById(R.id.photo))
					.setImageBitmap(Datos.jugadores.get(i).getImage());

			return item;
		}
	}

	private void insertarJugador(Jugador j) {

		int vecesbebidas, vecesJugadas, vecesKO, vecesGanadas;
		try {
			String selection = sqlitedb.NOMBRE_JUGADOR + " like '"
					+ j.getNombre() + "' ;";
			// String[] selectionArgs = new String[] { j.getNombre() };
			SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
			builder.setTables(sqlitedb.TABLE_NAME);
			String[] columns = new String[] { sqlitedb.NOMBRE_JUGADOR };
			Cursor c = sqlitedb.query(j.getNombre(),
					new String[] { sqlitedb.NOMBRE_JUGADOR });

			if (c != null) {
				int a = c.getCount();
				if (a == 1) {
					c.moveToFirst();

					vecesJugadas = Integer.parseInt(c.getString(c
							.getColumnIndex("vecesjugadas")));
					ContentValues cv = new ContentValues();
					cv.put(sqlitedb.NOMBRE_JUGADOR, j.getNombre());
					cv.put(sqlitedb.VECES_JUGADAS, String
							.valueOf((vecesJugadas + 1)));
					sqlitedb.actualizarJugador(j.getNombre(), cv);

				} else {
					long res = sqlitedb.addJugador(j.getNombre(), String.valueOf(j.getNbebe()));
					if(res==-1){
						Toast.makeText(getApplicationContext(), "hubo un error insertando el jugador en la base de datos", Toast.LENGTH_LONG).show();
					}
					/*
					 * db.execSQL("INSERT into estadisticas values ('" +
					 * j.getNombre() + "' , '" + 1 + "' , '" + 0 + "' , '" + 0 +
					 * "' , '" + j.getNbebe() + "');");
					 */
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
		inflater.inflate(R.menu.menu_listado_inicial, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int a = item.getItemId();
		switch (a) {
		case R.id.BorrarEstadistica:
			// InsertarControl();
			/**
			 * Iniciamos que vea la tabla o grafica con sus valores
			 */

			try {
				sqlitedb.onUpgrade(sqlitedb, 0, 0);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.Dashboard:
			startActivity(new Intent(this, ViewDashboard.class));
			break;
		}

		return true;
	}

	private void doTakePhotoAction() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		/*
		 * mUri = Uri.fromFile(new
		 * File(Environment.getExternalStorageDirectory(), "pic_" +
		 * String.valueOf(System.currentTimeMillis()) + ".jpg"));
		 */
		File f = new File(this.DIRECTORY_NAME);
		f.mkdirs();
		mUri = Uri.fromFile(new File(this.DIRECTORY_NAME + "pic_"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);

		try {
			intent.putExtra("return-data", true);
			startActivityForResult(intent, CAMERA_RESULT);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		if (requestCode == CAMERA_RESULT) {
			Intent intent = new Intent(this, CropI.class);
			// here you have to pass absolute path to your file
			path = mUri.getPath();
			intent.putExtra("image-path", path);
			intent.putExtra("scale", true);
			// startActivity(intent);
			startActivityForResult(intent, 2);

		}
		if (requestCode == 2) {
			try {
				Datos.jugadores.get(indice_lista).setImage(getBitmap(path));
				refreshPhotoPlayer();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(),
						Toast.LENGTH_LONG).show();
			}

		}

	}

	private void refreshPhotoPlayer() {
		// TODO Auto-generated method stub
		/*
		 * ((ImageView) findViewById(R.id.photo_player))
		 * .setImageBitmap(Datos.jugadores.get(indice_lista).getImage());
		 */
		((ListView) findViewById(R.id.listadoinicial))
				.setAdapter(new ListadoAdapter(this));
	}

	private Bitmap getBitmap(String path) {
		Uri uri = getImageUri(path);
		InputStream in = null;
		try {
			in = getContentResolver().openInputStream(uri);
			return BitmapFactory.decodeStream(in);
		} catch (FileNotFoundException e) {
			// Log.e(TAG, "file " + path + " not found");
		}
		return null;
	}

	private Uri getImageUri(String path) {
		return Uri.fromFile(new File(path));
	}

}
