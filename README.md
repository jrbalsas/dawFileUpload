Sample Facelet and Servlet for uploading and show images
==========

Sample JEE Maven project for uploading and sharing images or files in general to a persistent location

Features:
-------------
- Sample shows how to use persistent folders to mantain static contents between application's deployments
- The Servlet and the Facelet versions allow to upload images to a specific folder and show all uploaded images.

Installation instructions
----------------
- Requires a Glassfish/Payara JEE Application Server
- Create folder on servlet filesystem to upload files, e.g. /tmp/images
- Filesystem destination path and public URL for files must be defined in ``/WEB-INF/glassfish-web.xml``
- Change attributes ``imagePath`` and ``imageUrl`` with the path of the new temporal directory and public url
- Glassfish/Paraya must have read/write permissions on imagePath folder.
