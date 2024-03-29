package com.daw.fileupload;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.logging.Logger;

/**
 * The Image servlet for serving from absolute path.
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/04/imageservlet.html
 */
@WebServlet("/imagen/*")
public class ImageServlet extends HttpServlet {

    // Properties ---------------------------------------------------------------------------------

    private String imagesPath;

    static final Logger log= Logger.getLogger(ImageServlet.class.getName());

    @Override
    public void init() throws ServletException {

        // Define base path somehow. Samples:
        //this.imagePath = "/tmp/images";  //Server absolute path, e.g. c:/tmp/images (windows)
        //this.imagePath = System.getProperty("user.home")+"/images";  //User home relative path
        //get images folder path from web.xml
        this.imagesPath=getServletContext().getInitParameter("imagesPath"); //sub-folder in user home, e.g. /home/username/webimages
        //create images folder if not exists
        AppConfig.initFolder(imagesPath);

    }

    // Actions ------------------------------------------------------------------------------------

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get requested image by path info.
        String requestedImage = request.getPathInfo();

        // Check if file name is actually supplied to the request URI.
        if (requestedImage == null) {
            // Do your thing if the image is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Decode the file name (might contain spaces and on) and prepare file object.
        File image = new File(imagesPath, URLDecoder.decode(requestedImage, "UTF-8"));

        // Check if file actually exists in filesystem.
        if (!image.exists()) {
            // Do your thing if the file appears to be non-existing.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.

            //response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            //log.warning(String.format("File %s not found", image.getName()));
            //return;

            //If file not exists, send default image (webapp/img/default.png)
            imagesPath=getServletContext().getRealPath("/img/");
            image = new File(imagesPath, "default.png");
        }

        // Get content type by filename.
        String contentType = getServletContext().getMimeType(image.getName());

        // Check if file is actually an image (avoid download of other files by hackers!).
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        if (contentType == null || !contentType.startsWith("image")) {
            // Do your thing if the file appears not being a real image.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            log.warning(String.format("File %s is not an image", image.getName()));
            return;
        }
        log.info(String.format("GET %s file", image.getName()));
        // Init servlet response.
        response.reset();
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(image.length()));

        // Write image content to response.
        Files.copy(image.toPath(), response.getOutputStream());
    }

}