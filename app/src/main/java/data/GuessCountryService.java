package data;

import android.content.Context;

import java.util.ArrayList;

import entities.MyData;

public class GuessCountryService {
	

	Context context;
//	huuloc
	
	String DatabaseName;
	
	public GuessCountryService(Context context){
		this.context = context;
	}
	
//	huuloc
	public GuessCountryService(Context context, String databaseName) {
		super();
		this.context = context;
		this.DatabaseName = databaseName;
	}


	public ArrayList<MyData> getAllCountry(){

		return null;
		
	}


}
