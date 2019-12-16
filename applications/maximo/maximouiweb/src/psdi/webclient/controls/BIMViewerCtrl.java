/*
 * IBM Confidential
 *
 * OCO Source Materials
 *
 * 5724-U18
 *
 * (C) COPYRIGHT IBM CORP. 2009,2014
 *
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright Office.
 *
 */
package psdi.webclient.controls;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import psdi.webclient.system.beans.WebClientBean;
import psdi.webclient.system.controller.BaseInstance;
import psdi.webclient.system.controller.ComponentInstance;
import psdi.webclient.system.controller.ControlInstance;
import psdi.webclient.system.controller.WebClientEvent;

public class BIMViewerCtrl  extends ControlInstance {
	public int render() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return super.render();
	}

	public int refreshselected()
	{
		//System.out.println(">>> BIMViewer CONTROL refreshselected event continue HIT!");
		WebClientEvent event = getWebClientSession().getCurrentEvent();
		Object o = event.getValue();
		if( o == null || !(o instanceof String ))
		{
			// Should never happen unless the .jsp is altered
//				return WebClientBean.EVENT_HANDLED;
		}
		
		//System.out.println(">>> BIMViewer CONTROL refreshselected event setting changed!");
//		setValueChanged(true);
		setChangedFlag();

		List<ComponentInstance> theComponents = getComponents();
		Iterator<ComponentInstance> theIterator = theComponents.iterator();
		ComponentInstance theComponent = null;
		psdi.webclient.components.BIMViewer viewComponent = null;
		while(theIterator.hasNext())
		{
			theComponent = theIterator.next();
			//System.out.println(">>> BIMViewer CONTROL refreshselected component: " + theComponent.getType());
			List<BaseInstance> moreComponents = theComponent.getChildren();
			Iterator<BaseInstance> moreIterator = moreComponents.iterator();
			BaseInstance moreComponent = null;
			while(moreIterator.hasNext())
			{
				moreComponent = moreIterator.next();
				//System.out.println(">>> BIMViewer CONTROL refreshselected component2: " + moreComponent.getType());
				viewComponent = (psdi.webclient.components.BIMViewer) showComponents(moreComponent, 3, "bimviewer");
			}
		}
		
		if(viewComponent != null)
		{
			//System.out.println(">>> BIMViewer CONTROL refreshselected component event directly CALLED!");
			//return viewComponent.refreshselected();
		}

		return WebClientBean.EVENT_HANDLED;
	}
	
	private BaseInstance showComponents(BaseInstance comp, int i, String retType)
	{
		BaseInstance retComponent = null;
		if(comp != null)
		{
			List<BaseInstance> moreComponents = comp.getChildren();
			Iterator<BaseInstance> moreIterator = moreComponents.iterator();
			BaseInstance moreComponent = null;
			while(moreIterator.hasNext())
			{
				moreComponent = moreIterator.next();
				//System.out.println(">>> BIMViewer CONTROL refreshselected component" + i + ": " + moreComponent.getType());
				if(moreComponent.getType().equalsIgnoreCase(retType)) retComponent = moreComponent;
				if(retComponent != null) break;
				
				retComponent = showComponents(moreComponent, i + 1, "bimviewer");
			}
		}
		return retComponent;
	}
	
}
