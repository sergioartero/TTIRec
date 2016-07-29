/**
 * LoteRpsService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.tti.sefaz.sender.campinas;

public interface LoteRpsService extends javax.xml.rpc.Service {
    public java.lang.String getLoteRpsAddress();

    public br.com.tti.sefaz.sender.campinas.LoteRps_PortType getLoteRps() throws javax.xml.rpc.ServiceException;

    public br.com.tti.sefaz.sender.campinas.LoteRps_PortType getLoteRps(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
