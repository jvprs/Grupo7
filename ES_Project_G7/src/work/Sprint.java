package work;

import java.awt.Container;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Action;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.Member;

import data.TrelloConnect;
import gui.MainWindow;
import timings.Hours;
import timings.Times;

public class Sprint implements Times {
	private TrelloConnect trelloconnect = MainWindow.getFrame().getTrelloConnect();
	private Trello trello = trelloconnect.getTrello();
	private List<String> membersIDs; // List<Member> ?
	private String sprint;
	private List<Double> array = new ArrayList<>();
	private List<Card> taskList;
	private MainWindow main;

	public Sprint() {
		this.membersIDs = new ArrayList<String>();
		this.taskList = new ArrayList<Card>();
		// iniciar trello
	}

	public String getSprint() {
		return sprint;
	}

	public void getListtarefa(String sprintName) {
		this.taskList = trelloconnect.listCardsSprint(trelloconnect.getSprint(sprintName));
	}

	private List<String> getMembersIDs() {

		taskList = trello.getListCards(getSprint());
		List<String> newList = new ArrayList<String>();
		for (Card c : this.taskList) {

			Task t = new Task(c);
			newList.addAll(t.getMembersIDs());
		}
		this.membersIDs = newList;
		return membersIDs;
	}

	@Override
	public boolean hasTimeSpent(String sprintName) {
		if (getTimeSpent(sprintName) == 0)
			return false;
		else
			return true;
	}

	@Override
	public double getTimeSpent(String sprintName) {
		double timeSpent = 0;
		getListtarefa(sprintName);
		for (Card c : this.taskList) {
			for (Action a : trello.getCardActions(c.getId())) {
				if (a.getData().getText() != null) {
					String data = a.getData().getText();
					if (!data.contains("@global")) {
						String[] auxiliar = data.split("/");
						String[] auxiliar2 = auxiliar[0].split("!");
						timeSpent = timeSpent + Double.parseDouble(auxiliar2[1]);
					}
				}
			}
		}

		return timeSpent;
	}

	public double getTimeEstimated() {
		double estimatedTime = 0;
		for (Card c : this.taskList) {
			Task t = new Task(c);
			estimatedTime += t.getTimeEstimated();
		}
		return estimatedTime;
	}

	@Override
	public double membergetTimeEstimated(String memberUsername) {
		double estimatedTime = 0;
		for (Card c : this.taskList) {
			Task t = new Task(c);
			estimatedTime += t.membergetTimeEstimated(memberUsername);
		}
		return estimatedTime;

	}

	@Override
	public double membergetTimeSpent(String memberUsername, String idmember, String sprintName) {

		double timeSpent = 0;
		double timeSpent2 = 0;
		double timeSpentaux3 = 0;
		for (Card card : this.taskList) {
			List<Hours> memberhoursListaux = new ArrayList<>();
			for (Member m : trello.getCardMembers(card.getId())) {
				if (m.getId().equals(idmember))
					memberhoursListaux.add(new Hours(m, card));
			}
			List<Hours> memberhoursList = memberhoursListaux;
			for (Hours h : memberhoursList) {
				if (h.getMember().getUsername().equals(memberUsername)) {
					timeSpent += h.membergetTimeSpent();
				}
				timeSpent2 = timeSpent + timeSpent2;
			}

		}
		return timeSpent / 2;
	}

	@Override
	public boolean memberhasTimeSpent(String memberUsername, Card card, String idmember) {
		if (membergetTimeSpent(memberUsername, card, idmember) == 0)
			return false;
		else
			return true;

	}

	public static void main(String[] args) throws IOException {
		TrelloConnect trelloconnect = MainWindow.getInstance().getTrelloConnect();
		Trello trello = trelloconnect.getTrello();
		double aux = 0;
		Sprint sprint = new Sprint();
		System.out.println("Tempo Total da Sprint");
		System.out.println(sprint.getTimeSpent("3rd Sprint"));
		String id = null;
		for (Card card : trelloconnect.listCardsSprint(trelloconnect.getSprint("3rd Sprint"))) {
			for (Member m : trello.getCardMembers(card.getId())) {
				if (m.getUsername().equals("tiagoalmeida01"))
					id = m.getId();
			}
		}
		System.out.println(sprint.membergetTimeSpent("tiagoalmeida01", id, "3rd Sprint"));

	}

	@Override
	public boolean hasTimeSpent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getTimeSpent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double membergetTimeSpent(String memberUsername, Card card, String idmember) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Double> getArray() {
		return array;
	}

}