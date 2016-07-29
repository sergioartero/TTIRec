package br.com.tti.sefaz.cache;

import java.util.Calendar;
import java.util.Date;

import br.com.tti.sefaz.persistence.SetData;
import br.com.tti.sefaz.persistence.dao.DAO;
import br.com.tti.sefaz.persistence.dao.DaoFactory;
import br.com.tti.sefaz.systemconfig.SystemProperties.RECIBE_STATE;
import br.com.tti.sefaz.systemconfig.SystemProperties.SET_STATE;

public class SetDataCache {
	private static SetDataCache INSTANCE;

	public static SetDataCache getINSTANCE() {
		if (INSTANCE == null) {
			INSTANCE = new SetDataCache();
		}
		return INSTANCE;
	}

	private DAO<SetData> daoSet;

	private SetDataCache() {
		this.daoSet = DaoFactory.createDAO(SetData.class);
	}

	synchronized public void saveSet(SetData data) {
		SetData setExists = this.daoSet.findEntity(data.getNumberSet());
		if (setExists == null)
			this.daoSet.saveEntity(data);
		else
			this.daoSet.updateEntity(data);
	}

	synchronized public SetData findSet(String nSet) {
		return this.daoSet.findEntity(nSet);
	}

	synchronized public void updateState(String nSet, SET_STATE state,
			Date date) {
		SetData set = this.daoSet.findEntity(nSet);
		set.setState(state);
		set.setLastDateUpdate(date);
		this.daoSet.updateEntity(set);
	}

	synchronized public void updateStateSefaz(String nSet,
			SET_STATE state, Date date, String cStat, String xMotivo,
			String dhSefaz) {
		SetData set = this.daoSet.findEntity(nSet);
		set.setState(state);
		set.setLastDateUpdate(date);
		set.setSefazCode(Integer.parseInt(cStat.trim()));
		set.setXMotivo(xMotivo);
		set.setDhSefaz(dhSefaz);
		this.daoSet.updateEntity(set);
	}

	synchronized public void updateSendState(String nSet, String nRecibe,
			String xmlProtocol, String cStat, String xMotivo, String dhSefaz) {
		SetData set = this.daoSet.findEntity(nSet);
		Date currentDate = Calendar.getInstance().getTime();

		set.setState(SET_STATE.ENVIADO);
		set.setLastDateUpdate(currentDate);
		set.setNumberSet(nSet);
		set.setXmlProtocol(xmlProtocol);
		set.setDataRecive(currentDate);
		set.setStateRecibe(RECIBE_STATE.PENDENTE);
		set.setProcessSet(false);
		set.setSefazCode(Integer.parseInt(cStat));
		set.setXMotivo(xMotivo);
		set.setDhSefaz(dhSefaz);

		this.daoSet.updateEntity(set);
	}

	synchronized public void updateCheckState(String nSet, String cStat,
			String xMotivo, String dhSefaz) {
		SetData set = this.daoSet.findEntity(nSet);
		Date current = Calendar.getInstance().getTime();

		set.setDateProcess(current);
		set.setProcessSet(true);
		set.setState(SET_STATE.PROCESSADO);
		set.setStateRecibe(RECIBE_STATE.CHECADO);
		set.setSefazCode(Integer.parseInt(cStat));
		set.setXMotivo(xMotivo);
		set.setDhSefaz(dhSefaz);
		set.setLastDateUpdate(current);

		this.daoSet.updateEntity(set);
	}

	synchronized public void updateTentative(String nSet) {
		SetData set = this.daoSet.findEntity(nSet);
		int tt = set.getNumeroTentativa();
		set.setNumeroTentativa(tt++);
		set.setLastDateUpdate(Calendar.getInstance().getTime());

		this.daoSet.updateEntity(set);
	}

	synchronized public void forceFlushCache() {
		this.daoSet.flush();
	}

}
