
package org.workin.test.ws.soap.service.image.small;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.workin.test.ws.soap.service.image.small package. 
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

    private final static QName _GetImage_QNAME = new QName("http://soap.ws.workin.org", "getImage");
    private final static QName _GetImageResponse_QNAME = new QName("http://soap.ws.workin.org", "getImageResponse");
    private final static QName _GetImageWithPath_QNAME = new QName("http://soap.ws.workin.org", "getImageWithPath");
    private final static QName _GetImageWithPathResponse_QNAME = new QName("http://soap.ws.workin.org", "getImageWithPathResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.workin.test.ws.soap.service.image.small
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetImageResponse }
     * 
     */
    public GetImageResponse createGetImageResponse() {
        return new GetImageResponse();
    }

    /**
     * Create an instance of {@link GetImageWithPathResponse }
     * 
     */
    public GetImageWithPathResponse createGetImageWithPathResponse() {
        return new GetImageWithPathResponse();
    }

    /**
     * Create an instance of {@link SmallImageResult }
     * 
     */
    public SmallImageResult createSmallImageResult() {
        return new SmallImageResult();
    }

    /**
     * Create an instance of {@link WSResult }
     * 
     */
    public WSResult createWSResult() {
        return new WSResult();
    }

    /**
     * Create an instance of {@link GetImage }
     * 
     */
    public GetImage createGetImage() {
        return new GetImage();
    }

    /**
     * Create an instance of {@link GetImageWithPath }
     * 
     */
    public GetImageWithPath createGetImageWithPath() {
        return new GetImageWithPath();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetImage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.workin.org", name = "getImage")
    public JAXBElement<GetImage> createGetImage(GetImage value) {
        return new JAXBElement<GetImage>(_GetImage_QNAME, GetImage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetImageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.workin.org", name = "getImageResponse")
    public JAXBElement<GetImageResponse> createGetImageResponse(GetImageResponse value) {
        return new JAXBElement<GetImageResponse>(_GetImageResponse_QNAME, GetImageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetImageWithPath }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.workin.org", name = "getImageWithPath")
    public JAXBElement<GetImageWithPath> createGetImageWithPath(GetImageWithPath value) {
        return new JAXBElement<GetImageWithPath>(_GetImageWithPath_QNAME, GetImageWithPath.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetImageWithPathResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.workin.org", name = "getImageWithPathResponse")
    public JAXBElement<GetImageWithPathResponse> createGetImageWithPathResponse(GetImageWithPathResponse value) {
        return new JAXBElement<GetImageWithPathResponse>(_GetImageWithPathResponse_QNAME, GetImageWithPathResponse.class, null, value);
    }

}
