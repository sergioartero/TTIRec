
package br.org.abrasf.nfse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.org.abrasf.nfse package. 
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

    private final static QName _ConsultarNfsePorFaixaRequest_QNAME = new QName("http://nfse.abrasf.org.br", "ConsultarNfsePorFaixaRequest");
    private final static QName _RecepcionarLoteRpsSincronoRequest_QNAME = new QName("http://nfse.abrasf.org.br", "RecepcionarLoteRpsSincronoRequest");
    private final static QName _ConsultarLoteRpsResponse_QNAME = new QName("http://nfse.abrasf.org.br", "ConsultarLoteRpsResponse");
    private final static QName _ConsultarNfseServicoTomadoResponse_QNAME = new QName("http://nfse.abrasf.org.br", "ConsultarNfseServicoTomadoResponse");
    private final static QName _ConsultarNfseServicoPrestadoRequest_QNAME = new QName("http://nfse.abrasf.org.br", "ConsultarNfseServicoPrestadoRequest");
    private final static QName _ConsultarLoteRpsRequest_QNAME = new QName("http://nfse.abrasf.org.br", "ConsultarLoteRpsRequest");
    private final static QName _ConsultarNfseServicoPrestadoResponse_QNAME = new QName("http://nfse.abrasf.org.br", "ConsultarNfseServicoPrestadoResponse");
    private final static QName _RecepcionarLoteRpsSincronoResponse_QNAME = new QName("http://nfse.abrasf.org.br", "RecepcionarLoteRpsSincronoResponse");
    private final static QName _RecepcionarLoteRpsResponse_QNAME = new QName("http://nfse.abrasf.org.br", "RecepcionarLoteRpsResponse");
    private final static QName _ConsultarNfsePorFaixaResponse_QNAME = new QName("http://nfse.abrasf.org.br", "ConsultarNfsePorFaixaResponse");
    private final static QName _SubstituirNfseResponse_QNAME = new QName("http://nfse.abrasf.org.br", "SubstituirNfseResponse");
    private final static QName _ConsultarNfseServicoTomadoRequest_QNAME = new QName("http://nfse.abrasf.org.br", "ConsultarNfseServicoTomadoRequest");
    private final static QName _GerarNfseRequest_QNAME = new QName("http://nfse.abrasf.org.br", "GerarNfseRequest");
    private final static QName _ConsultarNfsePorRpsResponse_QNAME = new QName("http://nfse.abrasf.org.br", "ConsultarNfsePorRpsResponse");
    private final static QName _CancelarNfseRequest_QNAME = new QName("http://nfse.abrasf.org.br", "CancelarNfseRequest");
    private final static QName _SubstituirNfseRequest_QNAME = new QName("http://nfse.abrasf.org.br", "SubstituirNfseRequest");
    private final static QName _CancelarNfseResponse_QNAME = new QName("http://nfse.abrasf.org.br", "CancelarNfseResponse");
    private final static QName _ConsultarNfsePorRpsRequest_QNAME = new QName("http://nfse.abrasf.org.br", "ConsultarNfsePorRpsRequest");
    private final static QName _RecepcionarLoteRpsRequest_QNAME = new QName("http://nfse.abrasf.org.br", "RecepcionarLoteRpsRequest");
    private final static QName _GerarNfseResponse_QNAME = new QName("http://nfse.abrasf.org.br", "GerarNfseResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.org.abrasf.nfse
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Output }
     * 
     */
    public Output createOutput() {
        return new Output();
    }

    /**
     * Create an instance of {@link Input }
     * 
     */
    public Input createInput() {
        return new Input();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Input }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "ConsultarNfsePorFaixaRequest")
    public JAXBElement<Input> createConsultarNfsePorFaixaRequest(Input value) {
        return new JAXBElement<Input>(_ConsultarNfsePorFaixaRequest_QNAME, Input.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Input }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "RecepcionarLoteRpsSincronoRequest")
    public JAXBElement<Input> createRecepcionarLoteRpsSincronoRequest(Input value) {
        return new JAXBElement<Input>(_RecepcionarLoteRpsSincronoRequest_QNAME, Input.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Output }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "ConsultarLoteRpsResponse")
    public JAXBElement<Output> createConsultarLoteRpsResponse(Output value) {
        return new JAXBElement<Output>(_ConsultarLoteRpsResponse_QNAME, Output.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Output }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "ConsultarNfseServicoTomadoResponse")
    public JAXBElement<Output> createConsultarNfseServicoTomadoResponse(Output value) {
        return new JAXBElement<Output>(_ConsultarNfseServicoTomadoResponse_QNAME, Output.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Input }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "ConsultarNfseServicoPrestadoRequest")
    public JAXBElement<Input> createConsultarNfseServicoPrestadoRequest(Input value) {
        return new JAXBElement<Input>(_ConsultarNfseServicoPrestadoRequest_QNAME, Input.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Input }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "ConsultarLoteRpsRequest")
    public JAXBElement<Input> createConsultarLoteRpsRequest(Input value) {
        return new JAXBElement<Input>(_ConsultarLoteRpsRequest_QNAME, Input.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Output }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "ConsultarNfseServicoPrestadoResponse")
    public JAXBElement<Output> createConsultarNfseServicoPrestadoResponse(Output value) {
        return new JAXBElement<Output>(_ConsultarNfseServicoPrestadoResponse_QNAME, Output.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Output }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "RecepcionarLoteRpsSincronoResponse")
    public JAXBElement<Output> createRecepcionarLoteRpsSincronoResponse(Output value) {
        return new JAXBElement<Output>(_RecepcionarLoteRpsSincronoResponse_QNAME, Output.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Output }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "RecepcionarLoteRpsResponse")
    public JAXBElement<Output> createRecepcionarLoteRpsResponse(Output value) {
        return new JAXBElement<Output>(_RecepcionarLoteRpsResponse_QNAME, Output.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Output }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "ConsultarNfsePorFaixaResponse")
    public JAXBElement<Output> createConsultarNfsePorFaixaResponse(Output value) {
        return new JAXBElement<Output>(_ConsultarNfsePorFaixaResponse_QNAME, Output.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Output }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "SubstituirNfseResponse")
    public JAXBElement<Output> createSubstituirNfseResponse(Output value) {
        return new JAXBElement<Output>(_SubstituirNfseResponse_QNAME, Output.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Input }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "ConsultarNfseServicoTomadoRequest")
    public JAXBElement<Input> createConsultarNfseServicoTomadoRequest(Input value) {
        return new JAXBElement<Input>(_ConsultarNfseServicoTomadoRequest_QNAME, Input.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Input }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "GerarNfseRequest")
    public JAXBElement<Input> createGerarNfseRequest(Input value) {
        return new JAXBElement<Input>(_GerarNfseRequest_QNAME, Input.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Output }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "ConsultarNfsePorRpsResponse")
    public JAXBElement<Output> createConsultarNfsePorRpsResponse(Output value) {
        return new JAXBElement<Output>(_ConsultarNfsePorRpsResponse_QNAME, Output.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Input }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "CancelarNfseRequest")
    public JAXBElement<Input> createCancelarNfseRequest(Input value) {
        return new JAXBElement<Input>(_CancelarNfseRequest_QNAME, Input.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Input }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "SubstituirNfseRequest")
    public JAXBElement<Input> createSubstituirNfseRequest(Input value) {
        return new JAXBElement<Input>(_SubstituirNfseRequest_QNAME, Input.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Output }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "CancelarNfseResponse")
    public JAXBElement<Output> createCancelarNfseResponse(Output value) {
        return new JAXBElement<Output>(_CancelarNfseResponse_QNAME, Output.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Input }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "ConsultarNfsePorRpsRequest")
    public JAXBElement<Input> createConsultarNfsePorRpsRequest(Input value) {
        return new JAXBElement<Input>(_ConsultarNfsePorRpsRequest_QNAME, Input.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Input }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "RecepcionarLoteRpsRequest")
    public JAXBElement<Input> createRecepcionarLoteRpsRequest(Input value) {
        return new JAXBElement<Input>(_RecepcionarLoteRpsRequest_QNAME, Input.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Output }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nfse.abrasf.org.br", name = "GerarNfseResponse")
    public JAXBElement<Output> createGerarNfseResponse(Output value) {
        return new JAXBElement<Output>(_GerarNfseResponse_QNAME, Output.class, null, value);
    }

}
