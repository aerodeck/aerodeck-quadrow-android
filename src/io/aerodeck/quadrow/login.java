package io.aerodeck.quadrow;

import java.util.Arrays;
import java.util.Vector;
import org.json.JSONArray;
import android.app.Activity;

public class login extends Activity{
	
		public static String main(String name, String username, String email, String initPassword, String initConfirmPassword){
			
			String [] array = {"", "", "", ""};
			String password = "";
			
			if(initPassword == initConfirmPassword){
				password = initPassword;
			}
			
			if (name.length() != 0 || username.length() != 0 || 
					email.length() != 0 || password.length() != 0){
        		array[0] = name;
        		array[1] = username;
        		array[2] = email;
        		array[3] = password;
        	}
			
			Vector<String> v = new Vector<String>(Arrays.asList(array));
			JSONArray jsonArray = new JSONArray(v);
			return null;
		}
}
