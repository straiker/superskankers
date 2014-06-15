package org.rait.ss;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SuperskankersActivity extends Activity {
	
	
    
	
	/*Main layout button handler*/
	public void backButtonHandler(View target){
		switch(target.getId()){
		case R.id.eventsButton:
			setContentView(R.layout.view_event);
			parseJson("json");
			break;
		case R.id.galleryButton:
			setContentView(R.layout.gallery);
			break;
		case R.id.contatUs:
			setContentView(R.layout.view_event);
			break;
		case R.id.backButton:
			setContentView(R.layout.main);
			break;
		case R.id.aboutUsButton:
			setContentView(R.layout.about_us);
			parseJson("about_us");
			break;
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
    }    
    
    public void parseJson(String adr){
    	
    	Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        
        String result = "";
        //String x = "";
        InputStream is=null;
        //http post
	        try{
	        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost("http://ravastu.eu/json/"+adr+".php");
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
	        }catch(Exception e){
	            CharSequence error =("Error in http connection "+e.toString());
	            Toast toast = Toast.makeText(context, error, duration);
	            toast.show();
	        }

        	try{
	        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost("http://ravastu.eu/json/"+adr+".php");
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
	        }catch(Exception e){
	            CharSequence error =("Error in http connection "+e.toString());
	            Toast toast = Toast.makeText(context, error, duration);
	            toast.show();
	        }
        //convert response to string
        try{       
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                }
                is.close();
         
                result=sb.toString();
                
        }catch(Exception e){
                Log.e("log_tag", "Error converting result "+e.toString());
        }
        
        //parse json data
        //View linearLayout =  findViewById(R.id.viewEvents);    
        
        OnTouchListener touchBack = new OnTouchListener(){

			public boolean onTouch(View v, MotionEvent event) {
				setContentView(R.layout.main);
				return true;
			}
        };
        
        
        try{
        	JSONArray jArray = new JSONArray(result);
            
        	if(adr == "json"){
        		LinearLayout layout = (LinearLayout) findViewById(R.id.events);
        		
                for(int i=0;i<jArray.length();i++){
	                JSONObject json_data = jArray.getJSONObject(i);                                    
	                                
	                CharSequence text = ("id: "+json_data.getString("id")+", title: "+json_data.getString("title"));
	                Toast toast = Toast.makeText(context, text, duration);
	                toast.show();
	                        
	                //Button valueTV = new Button(this);
	                TextView valueTV = new TextView(this);
	                valueTV.setBackgroundColor(R.color.bg_buttons);
	                
	                valueTV.setText(json_data.getString("title")+"\n\n"
	                	+json_data.getString("location")+"\n\n"
	                	+json_data.getString("time")+"\n\n"
	                	+json_data.getString("description")
	                );
	                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)valueTV.getLayoutParams();
	                params.setMargins(0, 0, 0, 10);
	                valueTV.setLayoutParams(params);
	                layout.addView(valueTV);  
	                
               }	
                //Button backButton = new Button(this);
                
                Button backButton = (Button) findViewById(R.id.backButton);
                
                backButton.setId(R.id.backButton);
                backButton.setText(R.string.back);
                backButton.setOnTouchListener(touchBack);
                } 
                else {
                	LinearLayout layout = (LinearLayout) findViewById(R.id.aboutUs);
                    for(int i=0;i<jArray.length();i++){
    	                JSONObject json_data = jArray.getJSONObject(i);                                    
    	                                
    	                //CharSequence text = (json_data.getString("text"));
    	                //Toast toast = Toast.makeText(context, text, duration);
    	                //toast.show();
    	                        
    	                TextView valueTV = new TextView(this);
    	                //valueTV.setBackgroundColor(R.color.green);
    	                        
    	                valueTV.setText(json_data.getString("text"));
    	                //valueTV.setTag(json_data.getString("id_about"));
    	                //valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
    	                        
    	                ((LinearLayout) layout).addView(valueTV);
                }
                }}
        catch(JSONException e){
                Log.e("log_tag", "Error parsing data "+e.toString());
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
		return true;
    }
}