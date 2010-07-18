
package org.workin.test.ws.soap.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AllEmployeeResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AllEmployeeResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://soap.ws.workin.org}WSResult">
 *       &lt;sequence>
 *         &lt;element name="employeeList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Employee" type="{http://soap.ws.workin.org}employee" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllEmployeeResult", propOrder = {
    "employeeList"
})
public class AllEmployeeResult
    extends WSResult
{

    protected AllEmployeeResult.EmployeeList employeeList;

    /**
     * Gets the value of the employeeList property.
     * 
     * @return
     *     possible object is
     *     {@link AllEmployeeResult.EmployeeList }
     *     
     */
    public AllEmployeeResult.EmployeeList getEmployeeList() {
        return employeeList;
    }

    /**
     * Sets the value of the employeeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllEmployeeResult.EmployeeList }
     *     
     */
    public void setEmployeeList(AllEmployeeResult.EmployeeList value) {
        this.employeeList = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Employee" type="{http://soap.ws.workin.org}employee" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "employee"
    })
    public static class EmployeeList {

        @XmlElement(name = "Employee")
        protected List<Employee> employee;

        /**
         * Gets the value of the employee property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the employee property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEmployee().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Employee }
         * 
         * 
         */
        public List<Employee> getEmployee() {
            if (employee == null) {
                employee = new ArrayList<Employee>();
            }
            return this.employee;
        }

    }

}
