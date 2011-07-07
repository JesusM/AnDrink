package me.jesus.AndroidDrink;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Search extends Activity {
	public Cursor cursor;
	private TextView mTextView;
	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		mTextView = (TextView) findViewById(R.id.text);
		mListView = (ListView) findViewById(R.id.list);

		Intent intent = getIntent();

		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			// handles a click on a search suggestion; launches activity to show
			// word
			Intent wordIntent = new Intent(this, SearchPlayerActivity.class);
			wordIntent.setData(intent.getData());
			startActivity(wordIntent);
			finish();
		} else /* if (Intent.ACTION_SEARCH.equals(intent.getAction())) */{
			// handles a search query
			String inten = intent.getAction();
			String query = intent.getStringExtra(SearchManager.QUERY);
			showResults(query);
		}
	}

	/**
	 * Searches the dictionary and displays results for the given query.
	 * 
	 * @param query
	 *            The search query
	 */
	private void showResults(String query) {

		SQliteDB sqlitedb = new SQliteDB(Search.this);

		/*
		 * String sentenciaSelect =
		 * "SELECT  nombreJugador as name, vecesJugadas as vecesjugadas, vecesGanadas as vecesganadas, vecesKO"
		 * +
		 * " as vecesko,vecesBebidas as vecesbebidas FROM estadisticas where nombreJugador like '"
		 * + id + "';";
		 */
		String sentenciaSelect = "SELECT  nombreJugador as nombre, vecesJugadas as vecesjugadas, vecesGanadas as vecesganadas, vecesKO"
				+ " as vecesko,vecesBebidas as vecesbebidas FROM estadisticas where nombreJugador like '"
				+ query + "';";
		try {

			String selection = sqlitedb.NOMBRE_JUGADOR + " = ?";
			String[] selectionArgs = new String[] { "*" };
			SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
			builder.setTables(sqlitedb.TABLE_NAME);
			String[] columns = new String[] { sqlitedb.NOMBRE_JUGADOR };
			cursor = sqlitedb.query(selection, selectionArgs, columns);
			//this.cursor = db.rawQuery(sentenciaSelect, null);
			if (cursor == null) {
				// There are no results
				/*
				 * mTextView.setText(getString(R.string.no_results, new Object[]
				 * { query }));
				 */
				Toast.makeText(getApplicationContext(),
						"no hay resultado que mostrar", Toast.LENGTH_LONG)
						.show();
			} else {
				// Display the number of results
				cursor.moveToFirst();
				int count = cursor.getCount();

				mTextView.setText(count + "");

				// Specify the columns we want to display in the result
				String[] from = new String[] { sqlitedb.NOMBRE_JUGADOR,
						sqlitedb.VECES_JUGADAS };

				// Specify the corresponding layout elements where we want the
				// columns to go
				int[] to = new int[] { R.id.word, R.id.definition };

				// Create a simple cursor adapter for the definitions and apply
				// them
				// to the ListView
				SimpleCursorAdapter words = new SimpleCursorAdapter(this,
						R.layout.result, cursor, from, to);
				mListView.setAdapter(words);

				// Define the on-click listener for the list items
				/*
				 * mListView.setOnItemClickListener(new OnItemClickListener() {
				 * public void onItemClick(AdapterView<?> parent, View view, int
				 * position, long id) { // Build the Intent used to open
				 * WordActivity with a // specific word Uri Intent wordIntent =
				 * new Intent(getApplicationContext(), WordActivity.class); Uri
				 * data = Uri.withAppendedPath( DictionaryProvider.CONTENT_URI,
				 * String .valueOf(id)); wordIntent.setData(data);
				 * startActivity(wordIntent); } });
				 */
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
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search:
			onSearchRequested();
			return true;
		default:
			return false;
		}
	}

}
