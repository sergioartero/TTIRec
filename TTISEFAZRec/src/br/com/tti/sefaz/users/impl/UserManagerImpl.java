package br.com.tti.sefaz.users.impl;

import java.rmi.RemoteException;

import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.users.Profile;
import br.com.tti.sefaz.users.User;
import br.com.tti.sefaz.users.UserManager;

public class UserManagerImpl implements UserManager {

	private DAO<User> daoUser;
	private DAO<Profile> daoProf;

	public UserManagerImpl() {
		this.daoUser = DaoFactory.createDAO(User.class);
		this.daoProf = DaoFactory.createDAO(Profile.class);
	}

	@Override
	public void changePassword(String login, String newPassword)
			throws RemoteException {
		User user = this.daoUser.findEntity(login);
		if (user != null) {
			user.setSenha(newPassword);
			this.daoUser.updateEntity(user);
		}
	}

	@Override
	public User getUser(String login, String password) throws RemoteException {
		User user = this.daoUser.findEntity(login);
		return user;
	}

	@Override
	public void register(User user, Profile p) throws RemoteException {
		user.setPerfil(p);

		User uTmp = this.daoUser.findEntity(user.getId());

		if (uTmp == null) {
			this.daoProf.saveEntity(p);
			this.daoUser.saveEntity(user);
		} else {
			this.daoProf.saveEntity(p);
			this.daoUser.updateEntity(user);
		}
	}

}
