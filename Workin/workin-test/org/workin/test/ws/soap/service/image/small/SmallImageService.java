package org.workin.test.ws.soap.service.image.small;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.4
 * Tue Dec 01 16:47:45 CST 2009
 * Generated source version: 2.2.4
 * 
 */
 
@WebService(targetNamespace = "http://soap.ws.workin.org", name = "SmallImageService")
@XmlSeeAlso({ObjectFactory.class})
public interface SmallImageService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getImage", targetNamespace = "http://soap.ws.workin.org", className = "org.workin.test.ws.soap.service.image.small.GetImage")
    @ResponseWrapper(localName = "getImageResponse", targetNamespace = "http://soap.ws.workin.org", className = "org.workin.test.ws.soap.service.image.small.GetImageResponse")
    @WebMethod
    public org.workin.test.ws.soap.service.image.small.SmallImageResult getImage(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getImageWithPath", targetNamespace = "http://soap.ws.workin.org", className = "org.workin.test.ws.soap.service.image.small.GetImageWithPath")
    @ResponseWrapper(localName = "getImageWithPathResponse", targetNamespace = "http://soap.ws.workin.org", className = "org.workin.test.ws.soap.service.image.small.GetImageWithPathResponse")
    @WebMethod
    public org.workin.test.ws.soap.service.image.small.SmallImageResult getImageWithPath(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );
}
