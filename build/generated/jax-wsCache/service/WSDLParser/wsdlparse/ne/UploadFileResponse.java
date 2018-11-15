
package wsdlparse.ne;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="WSDLName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "status",
    "wsdlName"
})
@XmlRootElement(name = "UploadFileResponse")
public class UploadFileResponse {

    @XmlElement(name = "Status")
    protected boolean status;
    @XmlElement(name = "WSDLName", required = true)
    protected String wsdlName;

    /**
     * Gets the value of the status property.
     * 
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     */
    public void setStatus(boolean value) {
        this.status = value;
    }

    /**
     * Gets the value of the wsdlName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWSDLName() {
        return wsdlName;
    }

    /**
     * Sets the value of the wsdlName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWSDLName(String value) {
        this.wsdlName = value;
    }

}
