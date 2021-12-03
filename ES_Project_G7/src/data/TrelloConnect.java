package data;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.julienvey.trello.Trello;
import com.julienvey.trello.TrelloHttpClient;
import com.julienvey.trello.domain.Argument;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.Member;
import com.julienvey.trello.domain.Membership;
import com.julienvey.trello.domain.TList;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;

import appearence.Button;
import appearence.FontType;
import appearence.Layout;
import appearence.TextField;
import appearence.TextLabel;
import gui.MainWindow;

@SuppressWarnings("serial")
public class TrelloConnect extends JPanel implements Conection {

	private Trello trelloMvn;
	private static final int STARTING_POINT = 0;
	private boolean connected;

//	private String login;
//	private String accessToken;

	// Key & AccessToken - Tiago
	private String login = "61d79cb5bcc75c155c7fd74aef6f1b4f";
	private String accessToken = "c9802440801393a957373bf718d042ff7d4083befa43681de8d93f56282cc118";
	private String boardName = "ES Project";

	private List<Member> members;

	/**
	 * @throws IOException
	 * 
	 */
	public TrelloConnect(MainWindow window) throws IOException {
		super();
		setAlignmentY(TOP_ALIGNMENT);
		members = new ArrayList<Member>();
		getData(window);
		connected = false;
		connectTo();
	}

	/**
	 * Get Trello data from window frame.
	 */
	public void getData(MainWindow window) {
		TextLabel title = new TextLabel("Login Trello", 15, FontType.FONT_TITLE);
		TextLabel user_lab = new TextLabel("Username: ", 15, FontType.FONT_BOLD);
		TextField login = new TextField();
		TextLabel token_lab = new TextLabel("Access Token: ", 15, FontType.FONT_BOLD);
		TextField accessToken = new TextField();
		TextLabel card_lab = new TextLabel("CardBoard: ", 15, FontType.FONT_BOLD);
		TextField cardBoard = new TextField();

//		Button b = new Button("Submit Trello data");
//		add(b);
//		add(b, RIGHT_ALIGNMENT);

//		this.login = login.getText();
//		this.accessToken = accessToken.getText();
//		this.boardName = cardBoard.getText();

		TextLabel[] labels = { user_lab, token_lab, card_lab };
		TextField[] fields = { login, accessToken, cardBoard };

		Layout layout = new Layout();
		layout.addToSpringLayout(window, title, labels, fields, STARTING_POINT);
	}

	/**
	 * Establish connection to Trello.
	 */
	@Override
	public void connectTo() throws IOException {
		trelloMvn = new TrelloImpl(login, accessToken, new ApacheHttpClient());
		getMembers(boardName);
	}

	/**
	 * Get Trello API.
	 */
	private Trello getTrello() {
		return trelloMvn;
	}

	/**
	 * Get members from Trello Board.
	 */
	public void getMembers(String boardName) {
		Board board = new Board();
		board.setName(boardName);
//		Member host = trelloMvn.getMemberInformation(login);
		List<Board> allBoards = trelloMvn.getMemberBoards("me", new Argument("fields", "name"));
		for (Board bd : allBoards) {
			if (bd.getName() == boardName) {
				board = trelloMvn.getBoard(bd.getId());
				List<Membership> boardMembers = board.getMemberships();
				for (Membership ms : boardMembers) {
					Member m = new Member();
					m.setId(ms.getId());
					System.out.println(m.getEmail());
					members.add(m);
				}
			}
//			List<TList> lists = board.fetchLists();
//			for (TList lista : lists) {
//				System.out.println(lista.getName() + "- " + lista.getId() + "-" + lista.getIdBoard());
//			}
		}
	}

	public void getAPI() {
		getTrello();
	}

	@Override
	public boolean connected() {
		return connected;
	}

//	//apenas teste!
//	public static void main(String[] args) {
//		//TrelloDetails td = new TrelloDetails(null);
//		Trello trelloApi = new TrelloImpl("61d79cb5bcc75c155c7fd74aef6f1b4f", "c9802440801393a957373bf718d042ff7d4083befa43681de8d93f56282cc118", new ApacheHttpClient());
//		//List <Board> b = trelloApi.getMemberBoards("me", new Argument("fields", "name"));
//		List <Board> boards = trelloApi.getMemberBoards("me", new Argument("fields", "name"));
//		for (Board board : boards) {
//		    board.getName();
//		    board.getId();
//		    System.out.println(board.getId() + ":" + board.getName());
//		    List <TList> lists = trelloApi.getBoardLists(board.getId());
//		    for (TList list : lists) {
//		        System.out.println(list.getId() + ":" + list.getName());
//		        List <Card> cards = trelloApi.getListCards(list.getId());
//		        for (Card card : cards) {
//		            System.out.println(card.getId() + ":" + card.getName());
//		        }
//		    }
//		}
//	}
}