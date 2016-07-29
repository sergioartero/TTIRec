
package br.com.tti.sefaz.ttirec.wsclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.tti.sefaz.ttirec.wsclient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EnviarEventoResponse_QNAME = new QName("http://wsendpoint.sefaz.tti.com.br/", "enviarEventoResponse");
    private final static QName _RetConsNFeDest_QNAME = new QName("http://www.portalfiscal.inf.br/nfe", "retConsNFeDest");
    private final static QName _ObterEventos_QNAME = new QName("http://wsendpoint.sefaz.tti.com.br/", "obterEventos");
    private final static QName _Signature_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Signature");
    private final static QName _Exception_QNAME = new QName("http://wsendpoint.sefaz.tti.com.br/", "Exception");
    private final static QName _ConsultaNFeDestResponse_QNAME = new QName("http://wsendpoint.sefaz.tti.com.br/", "consultaNFeDestResponse");
    private final static QName _ObterEventosResponse_QNAME = new QName("http://wsendpoint.sefaz.tti.com.br/", "obterEventosResponse");
    private final static QName _ConsultaNFeDest_QNAME = new QName("http://wsendpoint.sefaz.tti.com.br/", "consultaNFeDest");
    private final static QName _EnviarEvento_QNAME = new QName("http://wsendpoint.sefaz.tti.com.br/", "enviarEvento");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.tti.sefaz.ttirec.wsclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TretEvento }
     * 
     */
    public TretEvento createTretEvento() {
        return new TretEvento();
    }

    /**
     * Create an instance of {@link ObterEventosResponse }
     * 
     */
    public ObterEventosResponse createObterEventosResponse() {
        return new ObterEventosResponse();
    }

    /**
     * Create an instance of {@link EnviarEvento }
     * 
     */
    public EnviarEvento createEnviarEvento() {
        return new EnviarEvento();
    }

    /**
     * Create an instance of {@link RetConsNFeDest }
     * 
     */
    public RetConsNFeDest createRetConsNFeDest() {
        return new RetConsNFeDest();
    }

    /**
     * Create an instance of {@link EnviarEventoResponse }
     * 
     */
    public EnviarEventoResponse createEnviarEventoResponse() {
        return new EnviarEventoResponse();
    }

    /**
     * Create an instance of {@link SignatureValueType }
     * 
     */
    public SignatureValueType createSignatureValueType() {
        return new SignatureValueType();
    }

    /**
     * Create an instance of {@link TEvento }
     * 
     */
    public TEvento createTEvento() {
        return new TEvento();
    }

    /**
     * Create an instance of {@link RetConsNFeDest.Ret.ResNFe }
     * 
     */
    public RetConsNFeDest.Ret.ResNFe createRetConsNFeDestRetResNFe() {
        return new RetConsNFeDest.Ret.ResNFe();
    }

    /**
     * Create an instance of {@link TretEvento.InfEvento }
     * 
     */
    public TretEvento.InfEvento createTretEventoInfEvento() {
        return new TretEvento.InfEvento();
    }

    /**
     * Create an instance of {@link RetConsNFeDest.Ret.ResCCe }
     * 
     */
    public RetConsNFeDest.Ret.ResCCe createRetConsNFeDestRetResCCe() {
        return new RetConsNFeDest.Ret.ResCCe();
    }

    /**
     * Create an instance of {@link ReferenceType.DigestMethod }
     * 
     */
    public ReferenceType.DigestMethod createReferenceTypeDigestMethod() {
        return new ReferenceType.DigestMethod();
    }

    /**
     * Create an instance of {@link SignatureType }
     * 
     */
    public SignatureType createSignatureType() {
        return new SignatureType();
    }

    /**
     * Create an instance of {@link TEvento.InfEvento }
     * 
     */
    public TEvento.InfEvento createTEventoInfEvento() {
        return new TEvento.InfEvento();
    }

    /**
     * Create an instance of {@link ObterEventos }
     * 
     */
    public ObterEventos createObterEventos() {
        return new ObterEventos();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link TransformsType }
     * 
     */
    public TransformsType createTransformsType() {
        return new TransformsType();
    }

    /**
     * Create an instance of {@link SignedInfoType.CanonicalizationMethod }
     * 
     */
    public SignedInfoType.CanonicalizationMethod createSignedInfoTypeCanonicalizationMethod() {
        return new SignedInfoType.CanonicalizationMethod();
    }

    /**
     * Create an instance of {@link ConsultaNFeDest }
     * 
     */
    public ConsultaNFeDest createConsultaNFeDest() {
        return new ConsultaNFeDest();
    }

    /**
     * Create an instance of {@link TRetEnvEvento }
     * 
     */
    public TRetEnvEvento createTRetEnvEvento() {
        return new TRetEnvEvento();
    }

    /**
     * Create an instance of {@link ConsultaNFeDestResponse }
     * 
     */
    public ConsultaNFeDestResponse createConsultaNFeDestResponse() {
        return new ConsultaNFeDestResponse();
    }

    /**
     * Create an instance of {@link SignedInfoType }
     * 
     */
    public SignedInfoType createSignedInfoType() {
        return new SignedInfoType();
    }

    /**
     * Create an instance of {@link X509DataType }
     * 
     */
    public X509DataType createX509DataType() {
        return new X509DataType();
    }

    /**
     * Create an instance of {@link RetConsNFeDest.Ret.ResCanc }
     * 
     */
    public RetConsNFeDest.Ret.ResCanc createRetConsNFeDestRetResCanc() {
        return new RetConsNFeDest.Ret.ResCanc();
    }

    /**
     * Create an instance of {@link Hashtable }
     * 
     */
    public Hashtable createHashtable() {
        return new Hashtable();
    }

    /**
     * Create an instance of {@link RetConsNFeDest.Ret }
     * 
     */
    public RetConsNFeDest.Ret createRetConsNFeDestRet() {
        return new RetConsNFeDest.Ret();
    }

    /**
     * Create an instance of {@link KeyInfoType }
     * 
     */
    public KeyInfoType createKeyInfoType() {
        return new KeyInfoType();
    }

    /**
     * Create an instance of {@link SignedInfoType.SignatureMethod }
     * 
     */
    public SignedInfoType.SignatureMethod createSignedInfoTypeSignatureMethod() {
        return new SignedInfoType.SignatureMethod();
    }

    /**
     * Create an instance of {@link ReferenceType }
     * 
     */
    public ReferenceType createReferenceType() {
        return new ReferenceType();
    }

    /**
     * Create an instance of {@link TEvento.InfEvento.DetEvento }
     * 
     */
    public TEvento.InfEvento.DetEvento createTEventoInfEventoDetEvento() {
        return new TEvento.InfEvento.DetEvento();
    }

    /**
     * Create an instance of {@link TransformType }
     * 
     */
    public TransformType createTransformType() {
        return new TransformType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarEventoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsendpoint.sefaz.tti.com.br/", name = "enviarEventoResponse")
    public JAXBElement<EnviarEventoResponse> createEnviarEventoResponse(EnviarEventoResponse value) {
        return new JAXBElement<EnviarEventoResponse>(_EnviarEventoResponse_QNAME, EnviarEventoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetConsNFeDest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.portalfiscal.inf.br/nfe", name = "retConsNFeDest")
    public JAXBElement<RetConsNFeDest> createRetConsNFeDest(RetConsNFeDest value) {
        return new JAXBElement<RetConsNFeDest>(_RetConsNFeDest_QNAME, RetConsNFeDest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObterEventos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsendpoint.sefaz.tti.com.br/", name = "obterEventos")
    public JAXBElement<ObterEventos> createObterEventos(ObterEventos value) {
        return new JAXBElement<ObterEventos>(_ObterEventos_QNAME, ObterEventos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignatureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Signature")
    public JAXBElement<SignatureType> createSignature(SignatureType value) {
        return new JAXBElement<SignatureType>(_Signature_QNAME, SignatureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsendpoint.sefaz.tti.com.br/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaNFeDestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsendpoint.sefaz.tti.com.br/", name = "consultaNFeDestResponse")
    public JAXBElement<ConsultaNFeDestResponse> createConsultaNFeDestResponse(ConsultaNFeDestResponse value) {
        return new JAXBElement<ConsultaNFeDestResponse>(_ConsultaNFeDestResponse_QNAME, ConsultaNFeDestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObterEventosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsendpoint.sefaz.tti.com.br/", name = "obterEventosResponse")
    public JAXBElement<ObterEventosResponse> createObterEventosResponse(ObterEventosResponse value) {
        return new JAXBElement<ObterEventosResponse>(_ObterEventosResponse_QNAME, ObterEventosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaNFeDest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsendpoint.sefaz.tti.com.br/", name = "consultaNFeDest")
    public JAXBElement<ConsultaNFeDest> createConsultaNFeDest(ConsultaNFeDest value) {
        return new JAXBElement<ConsultaNFeDest>(_ConsultaNFeDest_QNAME, ConsultaNFeDest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarEvento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsendpoint.sefaz.tti.com.br/", name = "enviarEvento")
    public JAXBElement<EnviarEvento> createEnviarEvento(EnviarEvento value) {
        return new JAXBElement<EnviarEvento>(_EnviarEvento_QNAME, EnviarEvento.class, null, value);
    }

}
