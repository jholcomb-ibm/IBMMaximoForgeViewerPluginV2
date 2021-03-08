package psdi.app.bim.viewer.lmv.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

/**
 * This is the utility class to process svg document and svg string.
 * @author caihualiu
 *
 */
public class SVGUtils {
	
	/**
	 * parse svg string to svg document
	 * @param svgString
	 * @return document
	 */
	 public static SVGDocument getSVGDocumentFromString(String svgString) 
	 {
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SVGDocumentFactory documentFactory = new SAXSVGDocumentFactory(parser, false);
        SVGDocument document = null;
        if (svgString != null) {
            try {
				document = documentFactory.createSVGDocument(null, new StringReader(svgString));
            } catch (IOException e) {
				e.printStackTrace();
			}
        }
        return document;
	}
	
	 
	 /**
	  * parse svg document to svg string
	  * @param svgDocument
	  * @return svgString
	  * @throws TransformerException
	  */
	public static String parseSvgDocumentToString(SVGDocument svgDocument) throws TransformerException 
	{
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		String svgString = "" ;
		try {
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(svgDocument), new StreamResult(writer));
			svgString = writer.getBuffer().toString().replaceAll("\n|\r", "");
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		
		return svgString;

	}

}
