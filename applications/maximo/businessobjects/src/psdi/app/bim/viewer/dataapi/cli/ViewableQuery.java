package psdi.app.bim.viewer.dataapi.cli;

import java.io.IOException;
import java.net.URISyntaxException;

import psdi.app.bim.viewer.dataapi.DataRESTAPI;
import psdi.app.bim.viewer.dataapi.ResultViewerService;


public class ViewableQuery
{

	private DataRESTAPI _service;
	public ViewableQuery()
	{
		_service = new APIImpl();
	}
	
	public DataRESTAPI getService()
	{
		return _service;
	}

	public ResultViewerService viewableQuery(
		String objectKey
	) 	
		throws IOException, 
			   URISyntaxException 
	{
		return _service.viewableQuery( objectKey );
	}


	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public static void main(
	    String[] arg
    )
		throws IOException, 
		   URISyntaxException 
	{
		if( arg.length < 1 )
		{
			System.out.println( "Usage: viewableQuery urn" );
			return;
		}
		ViewableQuery query = new ViewableQuery();
		
		ResultViewerService result = query.viewableQuery( arg[0] );
		if( result.isError() )
		{
			System.out.println( result.toString() );
			return;
		}
		String bucketKey = DataRESTAPI.bucketFromBase64URN( result.getURN() );
		System.out.println( bucketKey );
		result.setShowDetails(  true  );
		System.out.println( result.toString() );
	}
}
