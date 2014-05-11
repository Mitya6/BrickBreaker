package brickBreaker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface HighscoreManager extends Remote {
	
	public ArrayList<Score> getScores() throws RemoteException;
	
	public void uploadScore(Score score) throws RemoteException;

}
