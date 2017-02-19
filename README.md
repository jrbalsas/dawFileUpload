Sample Servlet for uploading and show images
==========

Sample Tomcat Maven project for uploading images or files in general to a persistent location

Features:
-------------
- Sample shows how to use persistent folders to mantain static contents between application's deployments
- Filesystem destination path and public URL for files must be defined in META-INF/context.xml
- The Servlet allows to upload images to a specific folder and show all uploaded images.

Installation instructions
----------------
* Create a temporal directory on filesystem, by default /tmp/images, and update path in META-INF/context.xml file if needed.
* Change Servlet attribute *imagePath* with the path of the new temporal directory 
* Tomcat user must have read/write permissions in directory.