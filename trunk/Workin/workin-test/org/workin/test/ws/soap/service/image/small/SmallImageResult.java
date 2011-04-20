package org.workin.test.ws.soap.service.image.small;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for SmallImageResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SmallImageResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://soap.ws.workin.org}WSResult">
 *       &lt;sequence>
 *         &lt;element name="imageData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SmallImageResult", propOrder = { "imageData" })
public class SmallImageResult extends WSResult {

	protected byte[] imageData;

	/**
	 * Gets the value of the imageData property.
	 * 
	 * @return
	 *     possible object is
	 *     byte[]
	 */
	public byte[] getImageData() {
		return imageData;
	}

	/**
	 * Sets the value of the imageData property.
	 * 
	 * @param value
	 *     allowed object is
	 *     byte[]
	 */
	public void setImageData(byte[] value) {
		this.imageData = value;
	}

}
