package me.jesus.AndroidDrink;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ViewDashboard extends Activity{
	String estado ;
	SharedPreferences settings = null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dashboard);
		settings = getSharedPreferences("datos", MODE_PRIVATE);
		estado = settings.getString("Estado", "nuevo");
		if(Integer.parseInt(settings.getString("veces jugadas", "0"))==0){
			SharedPreferences.Editor edit = settings.edit();
			edit.putString("veces jugadas", "1");
			edit.commit();
		}else{
			int v_jug=Integer.parseInt(settings.getString("veces jugadas", "0"));
			SharedPreferences.Editor edit = settings.edit();
			edit.putString("veces jugadas", v_jug+1+"");
			edit.commit();
		}
		if(estado.equals("nuevo")){
			((TextView)findViewById(R.id.estadoJuego)).setText("Comenzar partida");
			((TextView)findViewById(R.id.label_header)).setText("Ninguna partida en juego");
//			SharedPreferences settings1 = getSharedPreferences(
//					"datos", MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();

			editor.putString("Estado","en juego");
			editor.commit();
		}else{
			((TextView)findViewById(R.id.estadoJuego)).setText("continuar partida");
			SharedPreferences settings2 = getSharedPreferences("datos", MODE_PRIVATE);
			String n_jug = settings2.getString("NJugadores", "-1");
			if(!n_jug.equals("-1")){
				((TextView)findViewById(R.id.label_header)).setText(n_jug+" jugando actualmente");
			}else{
				((TextView)findViewById(R.id.label_header)).setText("Hubo un problema en la anterior partida, por favor, salga y vuelva a comenzar");
			}
			
		}
		findViewById(R.id.button1).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(estado.equals("nuevo")){
					startActivity(new Intent(ViewDashboard.this,ListadoInicial.class));
				}else{
					startActivity(new Intent(ViewDashboard.this,DrinkView.class));
				}
				
			}});
		findViewById(R.id.button2).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ViewDashboard.this,EstadisticasView.class));
				
			}});
		findViewById(R.id.button3).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final AlertDialog alertDialog = new AlertDialog.Builder(
						ViewDashboard.this).create();
			
				alertDialog.setTitle("AnDrink");
				alertDialog
						.setMessage("Autor: Jes�s Manzano Camino\nmail: manzanocaminojesus@gmail.com\n" +
								"twitter: jesus_manza");

				alertDialog.setButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								alertDialog.dismiss();

							}
						});

				alertDialog.show();
				
			}});
		findViewById(R.id.button4).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences settings1 = getSharedPreferences(
						"datos", MODE_PRIVATE);
				SharedPreferences.Editor editor = settings1.edit();

				editor.putString("Estado","nuevo");
				editor.commit();
				finish();
				
			}});
		
		
		
		
	}
}
