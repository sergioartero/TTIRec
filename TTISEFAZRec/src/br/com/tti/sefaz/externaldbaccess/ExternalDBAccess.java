package br.com.tti.sefaz.externaldbaccess;

import br.com.tti.sefaz.buffer.DBAccess;
import br.com.tti.sefaz.printer.XMLPrinter;
import br.com.tti.sefaz.users.UserManager;

public interface ExternalDBAccess extends DBAccess, UserManager, XMLPrinter {

}
