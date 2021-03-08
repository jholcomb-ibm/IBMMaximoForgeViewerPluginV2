<%--
* Licensed Materials - Property of IBM
* Restricted Materials of IBM
* 
* (C) COPYRIGHT IBM CORP. 2010,2018 All Rights Reserved.
* US Government Users Restricted Rights - Use, duplication or
* disclosure restricted by GSA ADP Schedule Contract with
* IBM Corp.
--%>
<%@page import="psdi.webclient.components.*"%>
<%@page import="psdi.server.MXServer"%>
<%@ include file="../common/componentheader.jsp" %>
<%

// when in design mode return some stub html for App Designer
if(designmode) 
{
	IMAGE_PATH = servletBase + "/"+skin+"images/"+(rtl?"rtl/":"")+wcs.getImagePath();
	%>
    <div>
    <img src="<%=IMAGE_PATH%>bim//ViewerDesignerMode.png" alt="BIM 3D viewer" draggable="false"> 
    </div>
    <%
    return;
}

// Must be bound to an instance of NavisWorks to function
if( !(component instanceof BIMViewer ) )
{
	return;
}
String uiSessionId = wcs.getUISessionID();
BIMViewer bldgMdl = (BIMViewer)component;
IMAGE_PATH = servletBase + "/"+skin+"images/"+(rtl?"rtl/":"")+wcs.getImagePath();

// Designer mode may put "-" into the ID string which make them invalid for JavaScript 
// idenfiers - Get rid of them
id = id.replace( "-", "_" );

String containerTable   = id + "container";

String value   = null;

boolean _needsRendered = bldgMdl.needsRender();


if( _needsRendered )
{%>
	<script type="text/javascript" >
		var jsLibrary = document.createElement('SCRIPT' );
		jsLibrary.type = "text/javascript";
		jsLibrary.src  = "<%=servletBase%>/javascript/bimviewerlib.js";
		if( navigator.appName == "Microsoft Internet Explorer" )
		{
			try
			{
				window.top.document.appendChild( jsLibrary );			// 7.5 wants this
			}
			catch( e ) 
			{
				window.top.document.head.appendChild( jsLibrary );		// 7.6 Want this
			}
		}
		else
		{
			var headers = document.getElementsByTagName('head');
			var head = headers[0];
			head.appendChild( jsLibrary );
		}
		
		<%
		String version = MXServer.getMXServer().getMaxupgValue();
		if( version.startsWith( "V7503" ) )
		{%>
			jsLibrary = document.createElement('SCRIPT' );
			jsLibrary.type = "text/javascript";
			jsLibrary.src  = "<%=servletBase%>/javascript/menus.js";
			if( navigator.appName == "Microsoft Internet Explorer" )
			{
				window.top.document.appendChild( jsLibrary );			// 7.5 wants this
			}
			else
			{
				var headers = document.getElementsByTagName('head');
				var head = headers[0];
				head.appendChild( jsLibrary );
			}
		<%}%>
		
	<%if( bldgMdl.getAppType() ==  BIMViewer.TYPE_LOOKUP )
	{%>
		// Need to allow time for the NavisWorks control to initialize or it fails to bind
		// to its event handlers
		addLoadMethod( 'setTimeout( \'<%=bldgMdl.jspScript( id )%>\', 100 );' );
	<%}
	else
	{%>
		addLoadMethod( 'setTimeout( \'<%=bldgMdl.jspScript( id )%>\', 100 );' );
		//addLoadMethod( '<%=bldgMdl.jspScript( id )%>' );
	<%}%>
	
	</script>
	<%
} 
else
{
	if( bldgMdl.getMxVersion() >= BIMViewer.VERSION_75_OR_GREATER )
	{%>
		<component id="<%=id%>_holder"><%="<![CDATA["%>
			<script>
				setTimeout( '<%=bldgMdl.jspScript( id )%>', 100 );
			</script>
		<%="]]>"%></component>
		<%
	}
	else
	{%>
		<%=bldgMdl.jspScript( id )%>
	<%}
}  
  
if( _needsRendered )
{
	// Force a reload of the model file if the control is being redrawn
	bldgMdl.setModelListChanged( true );
	bldgMdl.setValueChanged( true );
	
	String controlTop = component.getProperty("controltop");
	controlTop = (controlTop == null || controlTop.equalsIgnoreCase("")) ? "250" : controlTop;
	String controlLeft     = component.getProperty("controlleft");
	controlLeft = (controlLeft == null || controlLeft.equalsIgnoreCase("")) ? "325" : controlLeft;
	String height     = component.getProperty("height");
	height = (height == null) ? "" : height;
	String width     = component.getProperty("width");
	width = (width == null) ? "" : width;
	%>

<div id=<%=id%>_frameLoc style="left: <%=controlLeft%>px; top: <%=controlTop%>px; border: 0px none; position: relative; overflow: hidden; visibility: hidden; z-index: 10000">
</div>

<script>

	var frameLoc = document.getElementById("<%=id%>_frameLoc");
	var controlHeight = "<%=height%>";
	var controlWidth = "<%=width%>";
	if(controlHeight == "")
	{
		controlHeight = (document.documentElement.clientHeight - <%=controlTop%> - 30);
	}
	controlHeight += "px";
	if(controlWidth == "")
	{
		controlWidth = (document.documentElement.clientWidth - <%=controlLeft%> - 30);
	}
	controlWidth += "px";
	if(frameLoc != null && frameLoc != undefined)
	{
		frameLoc.style.height = controlHeight;
		frameLoc.style.width = controlWidth;

		var viewerframe = "<%=servletBase%>/components/bim<%=bldgMdl.getViewerType()%>/viewerframe.jsp?rid=<%=id%>&id=<%=bldgMdl.getId()%>&uisessionid=<%=uiSessionId%>";
		var iframe = document.createElement('iframe');
		iframe.frameBorder = 0;
		iframe.width = frameLoc.style.width;
		iframe.height = frameLoc.style.height;
		iframe.id = "<%=id%>_frame";
		iframe.setAttribute("src", viewerframe);
		frameLoc.appendChild(iframe);
		//console.log("bimviewer iframe set to: " + iframe.width + ", " + iframe.height);
	}
		
</script>

<%
}  // Close else if !bldgMdl.needsRender() )
%>

<%@ include file="../common/componentfooter.jsp" %>
