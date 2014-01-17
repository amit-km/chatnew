package com.amit.second;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import io.socket.*;

public class MainActivity extends Activity {
	private SocketIO socket;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			socket = new SocketIO("http://10.0.2.2:8000/");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		socket.connect(new IOCallback() {
			@Override
			public void onMessage(JSONObject json, IOAcknowledge ack) {
				try {

					System.out.println("Server said:" + json.toString(2));
				} catch (JSONException e) {
					//Toast.makeText(getApplicationContext(), "jsone" , Toast.LENGTH_LONG).show();

					e.printStackTrace();
				}
			}

			@Override
			public void onMessage(String data, IOAcknowledge ack) {
				System.out.println("Server said: " + data);

				// Toast.makeText(getApplicationContext(), (String)data, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(SocketIOException socketIOException) {
				//	Toast.makeText(getApplicationContext(), "socie" , Toast.LENGTH_LONG).show();

				System.out.println("an Error occured");
				socketIOException.printStackTrace();
			}

			@Override
			public void onDisconnect() {
				//	Toast.makeText(getApplicationContext(), "conet" , Toast.LENGTH_LONG).show();

				System.out.println("Connection terminated.");
			}

			@Override
			public void onConnect() {
				//	Toast.makeText(getApplicationContext(), "conest" , Toast.LENGTH_LONG).show();

				System.out.println("Connection established");
				socket.send("Hello server"); //this sends message "Hello server" to the server
			}

			@Override
			public void on(String event, IOAcknowledge ack, Object... args) {
				//Toast.makeText(getApplicationContext(), "hey" , Toast.LENGTH_LONG).show();
				// response echoed back by server is received here
				System.out.println("Server triggered event '" + event + "'");
				System.out.println("Server said: " + args[0]);
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
