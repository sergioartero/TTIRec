
package br.com.tti.sefaz.wsendpoint.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "confirm", namespace = "http://wsendpoint.sefaz.tti.com.br/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "confirm", namespace = "http://wsendpoint.sefaz.tti.com.br/", propOrder = {
    "chave",
    "arg1",
    "arg2",
    "arg3",
    "arg4"
})
public class Confirm {

    @XmlElement(name = "chave", namespace = "")
    private String chave;
    @XmlElement(name = "arg1", namespace = "")
    private String arg1;
    @XmlElement(name = "arg2", namespace = "")
    private String arg2;
    @XmlElement(name = "arg3", namespace = "")
    private String arg3;
    @XmlElement(name = "arg4", namespace = "")
    private String arg4;

    /**
     * 
     * @return
     *     returns String
     */
    public String getChave() {
        return this.chave;
    }

    /**
     * 
     * @param chave
     *     the value for the chave property
     */
    public void setChave(String chave) {
        this.chave = chave;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getArg1() {
        return this.arg1;
    }

    /**
     * 
     * @param arg1
     *     the value for the arg1 property
     */
    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getArg2() {
        return this.arg2;
    }

    /**
     * 
     * @param arg2
     *     the value for the arg2 property
     */
    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getArg3() {
        return this.arg3;
    }

    /**
     * 
     * @param arg3
     *     the value for the arg3 property
     */
    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getArg4() {
        return this.arg4;
    }

    /**
     * 
     * @param arg4
     *     the value for the arg4 property
     */
    public void setArg4(String arg4) {
        this.arg4 = arg4;
    }

}
