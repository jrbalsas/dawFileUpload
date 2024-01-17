Sample Facelet and Servlet for uploading and show images
==========

Sample JakartaEE Maven project for uploading and sharing images or files in general to a persistent location

Features:
-------------
- The application shows how to use persistent folders to mantain static contents between application's deployments
- The Servlet/JSP and the Facelets versions allow to upload images to a specific folder and show all uploaded images.
- Files/images folder can be changed in ``WEB-INF/web.xml``
- The application shows to ways of uploading images: using a Servlet ``ImageUploadServletCtrl`` or a JSF Controller ``ImageController``
- Images/files can be downloaded (or showed) in two ways:
  1. **Option 1 (recommended)** From a specific Servlet. Thanks to @BalusC for his work [ImageServlet](http://balusc.blogspot.com/2007/04/imageservlet.html) 
  2. **Option 2** From shared folder (using Payara/Glassfish public folder feature)

Usage instructions
----------------
- By default, application place or read images from ``webimages`` subfolder on user home folder, e.g. ``/home/user/webimages`` (unix) or ``c:/Users/user/webimages`` (windows) 
- Server must have read/write permissions on this folder
- If you want to change default folder, it must be modified on ``WEB-INF/web.xml``
- **Option 1** The application creates the folder specified in ``WEB-INF/web.xml`` if not exists and shows/download images from servlet */imagen*, e.g./app/imagen/test.jpg. If image does not exist, a default image is shown. 
- **Option 2** Only can be used with Glassfish/Payara JakartaEE 10 Application Servers.
  - Filesystem destination path and public URL for files must be defined in ``/WEB-INF/glassfish-web.xml``
  - Change new *imagePath* in web.xml and property ``imageUrl`` on Servlet/Controller with the name of the new subfolder, e.g. *webimages* 
