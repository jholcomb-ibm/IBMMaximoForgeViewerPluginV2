/**
* Copyright IBM Corporation 2009-2020
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
* @Author Sean Fitzpatrick
**/
package psdi.app.bim.viewer.lmv;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class DefectView extends Mbo implements DefectViewRemote
{
 	
	/**
	 * Constructor.
	 * @param ms The NonPersistentMboSet.
	 */
	public DefectView(MboSet ms) throws RemoteException
	{
		super(ms);
	}

	@Override
     public void add() throws MXException, RemoteException
     {
        super.add();
        
     }
	
	@Override
	public void init() throws MXException
	{
		super.init();
	}
}