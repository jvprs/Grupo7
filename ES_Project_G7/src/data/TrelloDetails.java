package data;

import java.awt.Dimension;

import javax.swing.*;

import com.julienvey.trello.Trello;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;

import definitions.TextField;
import definitions.TextLabel;

@SuppressWarnings("serial")
public class TrelloDetails extends JPanel implements Conection {

	private Trello trelloMvn;
//	private String login;
//	private String accessToken;
	//private String cardBoard;

	/**
	 * 
	 */
	public TrelloDetails() {
		super();		
		//		setAlignmentY(TOP_ALIGNMENT);

		TextLabel t = new TextLabel("User Trello", 150);
		add(t);

		TextField user = new TextField();
		add(user);
		TextLabel t2 = new TextLabel("Key Trello", 150);
		add(t2);

		TextField user2 = new TextField();
		add(user2);
		
		TextLabel t23 = new TextLabel("Token Trello", 150);
		add(t23);

		TextField user23 = new TextField();
		add(user23);

//		connectTO(login, accessToken);
		
	}

	/**
	 * Establish connection to Trello.
	 */
	
	public void connectTo(String login, String accessToken) {
		trelloMvn = new TrelloImpl(login, accessToken, new ApacheHttpClient());
	}

	/**
	 * Get Trello API.
	 */
	private Trello getTrello() {
		return trelloMvn;
	}


	public void getAPI() {
		getTrello();		
	}







}
