package data;

import android.content.Context;

import java.util.ArrayList;

import entities.MyData;

public class City {
	

	Context context;
//	huuloc
	
	String DatabaseName;
	
	public City(Context context){
		this.context = context;
	}
	
//	huuloc
	public City(Context context, String databaseName) {
		super();
		this.context = context;
		this.DatabaseName = databaseName;
	}


	public ArrayList<MyData> getAllCountry(){

		return null;
		
	}


}
