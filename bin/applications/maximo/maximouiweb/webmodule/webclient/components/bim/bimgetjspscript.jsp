<%--
* Copyright IBM Corporation 2009-2019
*
* Licensed under the Eclipse Public License - v 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.eclipse.org/legal/epl-v10.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* 
* @Author Josh Holcomb
--%>
<%
if( _needsRendered )
{%>
	<script>
//alert(">>> bimgetjspscript hit");
	<%if( bldgMdl.getAppType() ==  BIMViewer.TYPE_LOOKUP )
	{%>
		// Need to allow time for the NavisWorks control to initialize or it fails to bind
		// to its event handlers
		addLoadMethod( 'setTimeout( \'<%=bldgMdl.jspScript( id )%>\', 500 );' );
	<%}
	else
	{%>
		// >>> this is the one that i think is erroring
		addLoadMethod( '<%=bldgMdl.jspScript( id )%>' );
	<%}%>
	
	</script>
	<%
		} 
	else
	{
		if( bldgMdl.getMxVersion() >= BIMViewer.VERSION_75_OR_GREATER )
		{
	%>
		<component id="<%=id%>_holder"><%="<![CDATA["%>
			<script>
				setTimeout( '<%=bldgMdl.jspScript( id )%>', 10 );
			</script>
		<%="]]>"%></component>
		<%
	}
	else
	{%>
		<script>
			<%=bldgMdl.jspScript( id )%>
		</script>
	<%}
}  
%>