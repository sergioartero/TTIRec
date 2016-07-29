package br.com.tti.sefaz.manager;

import java.rmi.Remote;

import br.com.tti.sefaz.buffer.DBAccess;
import br.com.tti.sefaz.callback.CallBackInteface;
import br.com.tti.sefaz.cancelinut.CancelInutInterface;
import br.com.tti.sefaz.connector.ConnectorNotifier;
import br.com.tti.sefaz.contingence.ContingeceInterface;
import br.com.tti.sefaz.contingence.ModeOperation;
import br.com.tti.sefaz.listeners.TTIEventListener;
import br.com.tti.sefaz.listeners.TTIEventNotifier;
import br.com.tti.sefaz.messenger.MessengerInterface;
import br.com.tti.sefaz.printer.XMLPrinter;
import br.com.tti.sefaz.querier.QuerierInterface;
import br.com.tti.sefaz.sender.SenderInterface;
import br.com.tti.sefaz.signer.SignerInterface;
import br.com.tti.sefaz.systemconfig.SystemConfigInterface;
import br.com.tti.sefaz.users.UserManager;

public interface ManagerInterface extends MessengerInterface, SenderInterface,
		CallBackInteface, SystemConfigInterface, SignerInterface,
		QuerierInterface, CancelInutInterface, TTIEventListener,
		TTIEventNotifier, ModeOperation, ContingeceInterface, Remote,
		DBAccess, UserManager, XMLPrinter, ConnectorNotifier {

}
