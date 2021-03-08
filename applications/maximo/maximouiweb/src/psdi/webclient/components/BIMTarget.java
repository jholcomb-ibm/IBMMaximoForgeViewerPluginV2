/**
* Copyright IBM Corporation 2009-2017
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
* @Author Doug Wood
**/
package psdi.webclient.components;

import java.lang.reflect.InvocationTargetException;

import psdi.webclient.system.controller.BoundComponentInstance;

/**
 * Component class to expose an API for integrating 3D BIM viewers with Maximo.
 * The reference implementation is the Autodesk NavisWOrks ActiveX control
 * 
 * <table>
 * 	<tr>
 * 		<td><b>Prefix</b></td><td><b>Use</b></td>
 * 	</tr>
 * 	<tr>
 * 		<td>event</td><td>Call from control jsp via JavaScript sentEvent function</td>
 * 	</tr>
 * 	<tr>
 * 		<td>jsp</td><td>Call from control jsp to dynamically generate JavaScriptn</td>
 * 	</tr>
 * 	<tr>
 * 		<td>jsp</td><td>Call from control jsp to dynamically generate JavaScriptn</td>
 * 	</tr>
 * </table>
 * 
 * @author Doug Wood
 */
public class BIMTarget extends BoundComponentInstance
{
	
	private String _width  = "100%";
	private String _height = "100%";
	private int    _topOffset = 0;
	private int    _leftOffset = 0;

	@Override
	public void initialize() 
    {
        super.initialize();
	}
    
	@Override
	public int render() 
		throws NoSuchMethodException, 
		       IllegalAccessException, 
		       InvocationTargetException
	{
		return super.render();
	}
}