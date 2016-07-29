package br.com.tti.sefaz.users;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserManager extends Remote {

	public void register(User user, Profile p) throws RemoteException;

	public User getUser(String login, String password) throws RemoteException;

	public void changePassword(String login, String newPassword)
			throws RemoteException;

}
