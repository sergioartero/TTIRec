package br.com.tti.sefaz.sender.port;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Hashtable;

import br.com.tti.sefaz.exceptions.MySenderException;

public class Invoker {

	public Hashtable<String, Class> classServices;
	public Hashtable<String, String> operationNames;

	public Invoker() {
		this.classServices = new Hashtable<String, Class>();
		this.operationNames = new Hashtable<String, String>();
	}

	public String invokePort(String idServico, Object port, String cabecalho,
			String xml) throws RemoteException {

		Class c = this.classServices.get(idServico);
		String method = this.operationNames.get(idServico);
		return invokePort(c, port, method, cabecalho, xml);
	}

	public String invokePort(Class c, Object port, String metodo,
			String cabecalho, String xml) throws RemoteException {

		Method methodo = null;
		Method[] metodos = c.getMethods();
		for (Method m : metodos) {
			if (m.getName().contains(metodo)
					&& m.getParameterTypes().length == 2) {
				methodo = m;
				break;
			}
		}

		try {
			return (String) methodo.invoke(port,
					new String[] { cabecalho, xml });
		} catch (IllegalArgumentException e) {
			throw new MySenderException(e, MySenderException.ERROR_CALL_SERVICE
					+ metodo);
		} catch (IllegalAccessException e) {
			throw new MySenderException(e, MySenderException.ERROR_CALL_SERVICE
					+ metodo);
		} catch (InvocationTargetException e) {
			throw new MySenderException(e, MySenderException.ERROR_CALL_SERVICE
					+ metodo);
		}

	}

	public void setClassServices(Hashtable<String, Class> classServices) {
		this.classServices = classServices;
	}

	public void setOperationNames(Hashtable<String, String> operationNames) {
		this.operationNames = operationNames;
	}

}
