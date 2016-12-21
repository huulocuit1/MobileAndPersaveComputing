package data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DB extends SQLiteOpenHelper {

	String DB_PATH = null;
	private static  String DB_NAME = "MyLocation";
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	public DB(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
	}

	public DB(Context context, String DBname) {

		
		super(context, DB_NAME+DBname+".sqlite", null, 1);
		this.myContext = context;
		DB_NAME = DBname+".sqlite";
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";

	}

	public void createDB() throws IOException {
		boolean dbExist = checkDB();
		if (dbExist) {
			// do nothing
		} else {
			this.getReadableDatabase();
			try {
				copyDB();
			} catch (IOException e) {
				System.out.println("Copy database error");
				throw new Error("Error copying database");
			}
		}
	}

	private void copyDB() throws IOException {
		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
		System.out.println("copy successfull");
	}

	private boolean checkDB() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			System.out.println("not found database");
		}

		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	/**
	 * Open database
	 */
	public void openDB() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DB_NAME;
		System.out.println("db path :" + myPath);
		try {
			myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
		} catch (Exception ex) {
			System.out.println("khong mo duoc database");
		}
	}

	/**
	 * Close database
	 */
	public void close() {
		if (myDataBase != null) {
			myDataBase.close();
		}
	}

	/**
	 * get all category record
	 */

	public Cursor getCountryInfoById() {
		return myDataBase.query("country", null, null, null, null, null, null);
	}

	public Cursor queryQAs(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		return myDataBase.query("word", null, null, null, null, null, null);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
