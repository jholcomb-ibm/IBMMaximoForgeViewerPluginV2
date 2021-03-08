# IBMMaximoForgeViewerPluginV2
BIM Extensions version 7.6.1.2 changes 
Notes 
 COBie support is now a base Maximo feature available in Maximo Feature Pack 7.6.0.6. Only 
viewer feature not included in Maximo core are covered here. 
 This version supports Maximo 7.6.0.10 and Maximo 7.6.1.x. At time of release this was tested 
against the latest Maximo 7.6.1.2 and earlier version 7.6.0.10. 
Install 
If installing for the first time use the steps outlined in the 7612 Autodesk Forge Viewer Plugin - Install 
Guide.pdf. 
 
If you are upgrading from a previous version then 
1. copy the files in the ForgeViewerPlugin directory to the Maximo <root> directory. 
2. Run updated 
3. Rebuild and redeploy the maximo ear file 
4. Update the presentations using the steps listed in the next section. 
Updating the application presentations 
If you are upgrading an existing install of the Forge plugin then the application presentation must be 
updated manually using the following steps: 
 from a command prompt, execute the following commands: 
o cd <maximo root>\tools\maximo\internal 
o runscriptfile -cbimlmv -fresizefixUpdates 
o runscriptfile -cbimlmv -fV7612_01 
