package psdi.app.bim.viewer.lmv.util; 

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * This is the utility class for process BIM markup.
 * @author caihualiu
 *
 */
public class MarkupUtils {
	
	/**
	 * generate the mark up svg with multiple markup inside.
	 * @return
	 */
	public static String generateMarkupSvgString(List<String> markupList) {

		SVGDocument markupRootSVGElement = processMarkupSvgNode(markupList);

		String markupData = getMarkupDataSvgString(markupRootSVGElement);
		
		return markupData;
	}
	

	public static List<String>  generateMarkupSvgMboSet(MboSetRemote markupList) throws RemoteException, MXException 
	{
		
		List<String> markupDataList = new ArrayList<String>();
		int i =0;
		MboRemote mbo = markupList.getMbo(i);
		while(mbo != null) {
			String markup = mbo.getString("markup");
			markupDataList.add(markup);
			i++;
			mbo = markupList.getMbo(i);
		}
		return markupDataList;
	}
	
	public static JSONObject  generateDefectSvgMboSet(MboSetRemote markupList) throws MXException, RemoteException
	{
    	JSONArray defectArray = new JSONArray();
		JSONObject defectMarkups = new JSONObject();
		String viewerState = "";
		
		int i =0;
		MboRemote mbo = markupList.getMbo(i);
		while(mbo != null) {
			int ticketUid = mbo.getInt("ticketuid");
			String markup = mbo.getString("markup");
			viewerState = mbo.getString("viewerstate");
			MarkupObject markupObject = getMarkupObject(markup);
			
			if(markupObject != null) 
			{
				JSONObject defectMarkup = new JSONObject();
				defectMarkup.put("defectNumber", ticketUid);
			    defectMarkup.put("type", markupObject.getType());
			    defectMarkup.put("position", markupObject.getPosition());
			    defectMarkup.put("size", markupObject.getSize());
			    
			    defectArray.add(defectMarkup);
			}

			i++;
			mbo = markupList.getMbo(i);
		}
		defectMarkups.put("defectMarkups", defectArray);
		defectMarkups.put("viewerState", viewerState);
		return defectMarkups;
	}
	
	
	/**
	 * get markup object with type, postion, size attribute in svg string
	 * @param markupSvgString
	 * @return markupObject
	 */
	public static MarkupObject getMarkupObject(String markupSvgString) 
	{
		SVGDocument markupRootSVGElement = SVGUtils.getSVGDocumentFromString(markupSvgString);
		MarkupObject markupObject = null;
		
		if(markupRootSVGElement != null) 
		{
			Node markupContainerNode = getSVGMarkUpContainerNode(markupRootSVGElement);
			Node markupDocument = getMarkupDocument(markupContainerNode);
			String type = getAttributeValue(markupDocument, "type");
			String position = getAttributeValue(markupDocument, "position");
			String size = getAttributeValue(markupDocument, "size");
			
			markupObject = new MarkupObject(type, position, size);
		}

		return markupObject;
	}
	

	/**
	 * get markupDocument node from markupContainer node
	 * @param markupContainer
	 * @return markupDocument
	 */
	private static Node getMarkupDocument(Node markupContainer) 
	{
		NodeList metadataList = markupContainer.getChildNodes();
		Node markupDocument = null;
		if(metadataList != null && metadataList.getLength() > 0) {
			Node metadata = metadataList.item(0);
			NodeList markupDocumentList = metadata.getChildNodes();
			if(metadataList != null && metadataList.getLength() > 0) {
				markupDocument = markupDocumentList.item(0);
			}
		}
		return markupDocument;
	}
	
	
	/**
	 * get node attribute value
	 * @param markupNode
	 * @param attributeName
	 * @return attributeValue
	 */
	private static String getAttributeValue(Node markupNode, String attributeName) 
	{
		NamedNodeMap namedNodeMap = markupNode.getAttributes();
		Node attribute = namedNodeMap.getNamedItem(attributeName);
		String attributeValue = attribute.getNodeValue();
		return attributeValue;
	}
	
	
	/**
	 * Parse Markup SVG Document to Markup SVG String
	 * @param markupRootSVGElement
	 * @return markupData
	 */
	private static String getMarkupDataSvgString(SVGDocument markupRootSVGElement) 
	{
		String markupData = "";
		try {
			markupData =  SVGUtils.parseSvgDocumentToString(markupRootSVGElement);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		return markupData;
	}
	
	
	/**
	 * update the markup svg document
	 * @param markupDataList
	 * @return markupRootSVGElement
	 */
	private static SVGDocument processMarkupSvgNode(List<String> markupDataList) 
	{
		SVGDocument markupRootSVGElement = null;
		if(markupDataList != null && markupDataList.size() > 0) {
			markupRootSVGElement = SVGUtils.getSVGDocumentFromString(markupDataList.get(0));
			addMarkUpContainer(markupRootSVGElement, markupDataList);
		}
		
		return markupRootSVGElement;
	}
	
	
	/**
	 * add markup container to svg root node
	 * @param markupRootSVGElement
	 * @param markupDataList
	 */
	private static void addMarkUpContainer(SVGDocument markupRootSVGElement, List<String> markupDataList) 
	{
		Node markupRootNode = getRootNode(markupRootSVGElement);
		for(int i = 1; i < markupDataList.size(); i++) {
			SVGDocument markupSVGElement = SVGUtils.getSVGDocumentFromString(markupDataList.get(i));
			if(markupSVGElement != null) 
			{
				Node markupContainerNode = getSVGMarkUpContainerNode(markupSVGElement);
				markupRootNode.appendChild(markupRootSVGElement.adoptNode(markupContainerNode.cloneNode(true)));
			}
		}	
	}
	
	
	/**
	 * get the root node from svg document
	 * @param markupSvgDocument
	 * @return rootNode
	 */
	private static Node getRootNode(SVGDocument markupSvgDocument) 
	{
		Node rootNode = null;
		if(markupSvgDocument != null) {
			NodeList nodeList = markupSvgDocument.getChildNodes();
			if(nodeList != null && nodeList.getLength() > 0) {
				rootNode = nodeList.item(0);
			}
		}
		return rootNode;
	}
	
	
	/**
	 * get the markup container node from svg document
	 * @param markupSvgDocument
	 * @return markupContainerNode
	 */
	private static Node getSVGMarkUpContainerNode(SVGDocument markupSvgDocument) 
	{
		Node markupRootNode = getRootNode(markupSvgDocument);
		NodeList list = markupRootNode.getChildNodes();
		Node markupContainerNode = null;
		for(int i = 0; i < list.getLength(); i++) {
			Node childNode = list.item(i);
			if("g".equals(childNode.getNodeName())) {
				markupContainerNode = childNode;
			}
		}
		return markupContainerNode;
	}
}
