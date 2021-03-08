package psdi.webclient.components;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValueData;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class BIMModelSpec
{
  public static final String TABLE_BUILDINGMODEL = "BUILDINGMODEL";
  public static final String FIELD_BUILDINGMODELID = "BUILDINGMODELID";
  private String _assetView = "";
  private String _locationName = "";
  private String _location = "";
  private String _binding = "";
  private long _modelId = 0L;
  private String _modelURL = "";
  private String _title = "";
  private String _description = "";
  private String _lookupView = "";
  private String _locationView = "";
  private String _attribClass = "";
  private String _attribName = "";
  private String _paramClass = "";
  private String _paramName = "";
  private int _selectionMode = -1;
  private String _siteId = "";
  private String _workOrderView = "";

  BIMModelSpec()
  {
  }

  BIMModelSpec(String locationName, String location, String binding, MboRemote model)
    throws RemoteException, MXException
  {
    this._locationName = locationName;
    this._location = location;
    this._binding = binding;
    this._modelId = model.getLong("BUILDINGMODELID");
    setTitle(model.getString("TITLE"));
    this._description = model.getString("DESCRIPTION");
    this._assetView = model.getString("ASSETVIEW");
    this._lookupView = model.getString("LOOKUPVIEW");
    this._locationView = model.getString("LOCATIONVIEW");
    this._attribClass = model.getString("ATTRIBUTECLASS");
    this._attribName = model.getString("ATTRIBUTENAME");
    this._paramClass = model.getString("PARAMCLASS");
    this._paramName = model.getString("PARAMNAME");
    this._siteId = model.getString("SITEID");
    this._workOrderView = model.getString("WORKORDERVIEW");

    if (!model.getMboValueData("SELMODE").isNull())
    {
      try
      {
        this._selectionMode = Integer.parseInt(model.getString("SELMODE"));
      }
      catch (Throwable localThrowable)
      {
      }
    }

    String modelURL = model.getString("URL");
    try
    {
      URL url = new URL(modelURL);
      String hostName = url.getHost();
      if (hostName.equalsIgnoreCase("<HOSTNAME>"))
      {
        hostName = getModelHostname();
        this._modelURL = (url.getProtocol() + "://" + getModelHostname() + url.getPath());
      }
      else
      {
        this._modelURL = modelURL;
      }
    }
    catch (MalformedURLException e)
    {
      this._modelURL = modelURL;
    }

    if (this._attribClass == null) this._attribClass = "LcRevitData";
    if (this._attribName == null) this._attribName = "Element";
    if (this._paramClass == null) this._paramClass = "LcRevitData";
    if (this._paramName == null) this._paramName = "Guid";
  }

  BIMModelSpec(DataBean modelBean)
    throws RemoteException, MXException
  {
    this._locationName = modelBean.getString("LOCATION");
    this._location = modelBean.getString("LOCATION");

    setTitle(modelBean.getString("TITLE"));
    this._description = modelBean.getString("DESCRIPTION");
    this._assetView = modelBean.getString("ASSETVIEW");
    this._lookupView = modelBean.getString("LOOKUPVIEW");
    this._locationView = modelBean.getString("LOCATIONVIEW");
    this._attribClass = modelBean.getString("ATTRIBUTECLASS");
    this._attribName = modelBean.getString("ATTRIBUTENAME");
    this._paramClass = modelBean.getString("PARAMCLASS");
    this._paramName = modelBean.getString("PARAMNAME");
    this._siteId = modelBean.getString("SITEID");
    this._workOrderView = modelBean.getString("WORKORDERVIEW");

    MboRemote modelMbo = modelBean.getMbo();
    this._modelId = modelMbo.getLong("BUILDINGMODELID");

    String modelURL = modelBean.getString("URL");

    if (!modelBean.getMboValueData("SELMODE").isNull())
    {
      try
      {
        this._selectionMode = Integer.parseInt(modelBean.getString("SELMODE"));
      }
      catch (Throwable localThrowable)
      {
      }
    }

    init(modelURL);
  }

  private void init(String modelURL)
  {
    try
    {
      URL url = new URL(modelURL);
      String hostName = url.getHost();
      if (hostName.equalsIgnoreCase("<HOSTNAME>"))
      {
        hostName = getModelHostname();
        this._modelURL = (url.getProtocol() + "://" + getModelHostname() + url.getPath());
      }
      else
      {
        this._modelURL = modelURL;
      }
    }
    catch (MalformedURLException e)
    {
      this._modelURL = modelURL;
    }

    if (this._attribClass == null) this._attribClass = "LcRevitData";
    if (this._attribName == null) this._attribName = "Element";
    if (this._paramClass == null) this._paramClass = "LcRevitData";
    if (this._paramName == null) this._paramName = "Guid";
  }

  public boolean equals(BIMModelSpec modelSpec)
  {
    if (modelSpec == null) return false;
    if (this._modelId != modelSpec._modelId) return false;
    if (!this._locationName.equals(modelSpec._locationName)) return false;
    if (!this._location.equals(modelSpec._location)) return false;
    if (!this._binding.equals(modelSpec._binding)) return false;
    if (!this._modelURL.endsWith(modelSpec._modelURL)) return false;
    if (!this._title.equals(modelSpec._title)) return false;
    if (!this._assetView.equals(modelSpec._assetView)) return false;
    if (!this._lookupView.equals(modelSpec._lookupView)) return false;
    if (!this._locationView.equals(modelSpec._locationView)) return false;
    if (!this._attribClass.equals(modelSpec._attribClass)) return false;
    if (!this._attribName.equals(modelSpec._attribName)) return false;
    if (!this._paramClass.equals(modelSpec._paramClass)) return false;
    if (!this._paramName.equals(modelSpec._paramName)) return false;
    return this._selectionMode == modelSpec._selectionMode;
  }

  public String getLocationName()
  {
    return this._locationName;
  }
  public String getLocation() { return this._location; } 
  public String getBinding() {
    return this._binding;
  }
  public long getModelId() { return this._modelId; } 
  public String getModelURL() {
    return this._modelURL;
  }
  public String getTitle() { return this._title; } 
  public String getDescription() {
    return this._description;
  }
  public String getAssetView() { return this._assetView; } 
  public String getLookupView() {
    return this._lookupView;
  }
  public String getLocationView() { return this._locationView; } 
  public String getAttribClass() {
    return this._attribClass;
  }
  public String getAttribName() { return this._attribName; } 
  public String getParamName() {
    return this._paramName;
  }
  public String getParamClass() { return this._paramClass; } 
  public int getSelectionMode() {
    return this._selectionMode;
  }
  public String getSiteId() { return this._siteId; } 
  public String getWorkOrderView() {
    return this._workOrderView;
  }

  private void setTitle(String title)
  {
    this._title = title;
    this._title = this._title.replace("\"", "");
    this._title = this._title.replace("'", "");
  }

  public String getModelHostname()
  {
    String hostName = "";
    try
    {
      hostName = MXServer.getMXServer().getProperty("bim.model.hostname");
    }
    catch (RemoteException localRemoteException)
    {
    }
    if (hostName == null) hostName = "";
    return hostName;
  }
}