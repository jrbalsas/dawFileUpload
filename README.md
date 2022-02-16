Sample Facelet and Servlet for uploading and show images
==========

Sample JEE Maven project for uploading and sharing images or files in general to a persistent location

Features:
-------------
- Sample shows how to use persistent folders to mantain static contents between application's deployments
- The Servlet/JSP and the Facelets versions allow to upload images to a specific folder and show all uploaded images.
- Images/files can be downloaded (or showed) in two ways:
   1. **Option 1** From shared folder in Payara/Glassfish folder
   2. **Option 2** From a specific Servlet. Thanks to @BalusC for his work [ImageServlet](http://balusc.blogspot.com/2007/04/imageservlet.html)

Usage instructions
----------------
- Create folder on servlet filesystem to upload files, e.g. /tmp/images
- Server must have read/write permissions on imagePath folder.
- **Option 1** requires a Glassfish/Payara JEE Application Server. 
   - Filesystem destination path and public URL for files must be defined in ``/WEB-INF/glassfish-web.xml``
   - Change attributes ``imagePath`` and ``imageUrl`` with the path of the new temporal directory and public url on Serv
- **Option 2** Just change image ``imagePath`` with created folder path  
  
