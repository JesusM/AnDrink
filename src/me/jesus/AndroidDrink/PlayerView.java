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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class PlayerView extends Activity {
	TextView t;
	ImageView i;
	ImageView imag;
	Dialog dialogo;
	int indice;
	boolean mPreviewRunning = false;
	private static final int CAMERA_RESULT = 1;
	private Uri mUri;
	private String path;
	private String DIRECTORY_NAME = "/mnt/sdcard/pychupitos/";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playerview);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutParams a = (LayoutParams) findViewById(R.id.photo_player).getLayoutParams();
		imag = (ImageView) findViewById(R.id.photo_player);
		//int x = a.;
		//int y = a.height;
		

		Bundle bundle = getIntent().getExtras();
		indice = bundle.getInt("indice");
		Datos.jugadorModificando = Datos.jugadores.get(indice);
		((TextView) findViewById(R.id.nombre_player)).setText(Datos.jugadores
				.get(indice).getNombre());
		if (Datos.jugadores.get(indice).getImage() != null) {
			((ImageView) findViewById(R.id.photo_player))
					.setImageBitmap(Datos.jugadores.get(indice).getImage());
		}
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.layout_notify,
				(ViewGroup) findViewById(R.id.toast_layout_root));
		//layout.setLayoutParams(a);
		// i = (ImageView) layout.findViewById(R.id.photo_player);

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("Toca la imagen para modificarla");
		Toast toast = new Toast(getApplicationContext());
	
		//toast.setGravity(Gravity.LEFT, 102, 56);
		
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
		Toast toast2 = new Toast(getApplicationContext());
		t = ((TextView) findViewById(R.id.nombre_player));
		
		text.setText("Toca el nombre del jugador para modificarlo");
		toast2.setGravity(Gravity.CENTER_VERTICAL, 5, 5);
		toast2.setDuration(Toast.LENGTH_SHORT);
		toast2.setView(layout);
		toast2.show();
		t.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialogo = new Dialog(PlayerView.this);
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
											.get(indice)
											.setNombre(
													((EditText) dialogo
															.findViewById(R.id.new_player_name))
															.getText()
															.toString());
									((TextView) findViewById(R.id.nombre_player))
											.setText(Datos.jugadores
													.get(indice).getNombre());
									dialogo.dismiss();
								} else {
									Toast.makeText(getApplicationContext(),
											"debe introducir un nombre",
											Toast.LENGTH_LONG).show();

								}
							}
						});
			}
		});
		((Button) findViewById(R.id.listo))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(PlayerView.this,
								ListadoInicial.class);
						startActivity(intent);
						finish();

					}
				});
		imag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new Intent(PlayerView.this, CameraView.class));
				doTakePhotoAction();

			}
		});

	}

	private void doTakePhotoAction() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		/*
		 * mUri = Uri.fromFile(new
		 * File(Environment.getExternalStorageDirectory(), "pic_" +
		 * String.valueOf(System.currentTimeMillis()) + ".jpg"));
		 
		File f = new File("/mnt/sdcard/pychupitos/");
		f.mkdirs();
		mUri = Uri.fromFile(new File(this.DIRECTORY_NAME + "pic_"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);

		try {
			intent.putExtra("return-data", true);
			startActivityForResult(intent, CAMERA_RESULT);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}*/
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
				Datos.jugadores.get(indice).setImage(getBitmap(path));
				refreshPhotoPlayer();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(),
						Toast.LENGTH_LONG).show();
			}

		}

	}

	private void refreshPhotoPlayer() {
		// TODO Auto-generated method stub
		((ImageView) findViewById(R.id.photo_player))
				.setImageBitmap(Datos.jugadores.get(indice).getImage());

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
	public void onBackPressed(){
		Intent intent = new Intent(PlayerView.this,
				ListadoInicial.class);
		startActivity(intent);
		finish();
	}
}