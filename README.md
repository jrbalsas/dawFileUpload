Sample Servlet for uploading and show images
==========

Sample JEE Maven project for uploading images or files in general to a persistent location

Features:
-------------
- Sample shows how to use persistent folders to mantain static contents between application's deployments
- The Servlet allows to upload images to a specific folder and show all uploaded images.

Installation instructions
----------------
- Requires a Glassfish/Payara JEE Application Server
- Filesystem destination path and public URL for files must be defined in /WEB-INF/glassfish-web.xml
- Change Servlet's attributes *imagePath* and *imageUrl* with the path of the new temporal directory and public url
- Glassfish/Paraya must have read/write permissions on imagePath folder.
