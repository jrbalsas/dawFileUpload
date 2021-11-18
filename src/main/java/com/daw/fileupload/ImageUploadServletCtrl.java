package com.daw.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/** Sample servlet for uploading files to server and show them
 *
 * @note Requires an images folder on filesystem for uploading and showing images
 * @see glassfish-web.xml for JEE server deployment or context.xml for Tomcat deployment)
 *
 * @author jrbalsas@ujaen.es
 */
@WebServlet(name = "ImageUploadServletCtrl", urlPatterns = {"/image"})
@MultipartConfig(maxFileSize = 1024*1024*5) //Max upload file size 5MB
public class ImageUploadServletCtrl extends HttpServlet {

    private final String viewPath="/WEB-INF/image/"; //Servlet templates folder

    //Images filesystem path and URL as defined in META-INF/context.xml
    private final String imagePath="/tmp/images";   //enter the folder absolute path in server filesystem e.g. c:/tmp/images
    private final String imagesUrl="/images";       //Images public URL 
                                                     
    private File imageFolder=null;
    private static final Logger logger = Logger.getLogger(ImageUploadServletCtrl.class.getName());;

    @Override
    public void init() throws ServletException {
        super.init();
                        
        //Open upload folder
        imageFolder=new File(imagePath);

        logger.log(Level.INFO, "Image upload path: {0}", imagePath);        
        
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        //Get available images files and send to view for listing
        request.setAttribute("images", getImageFileNames());
        //Pass to view the public URL for uploaded images 
        request.setAttribute("imagesUrl", imagesUrl);
        request.setAttribute("imagesPath", imagePath);
        
        RequestDispatcher rd=request.getRequestDispatcher(viewPath+"/send.jsp");
        rd.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        Part filePart = request.getPart("fileParam"); // Retrieves <input type="file" name="fileParam">
        if (filePart!=null) {  
            String fileName = filePart.getSubmittedFileName();
            File newFile = new File(imageFolder, fileName);

            //Move temp file to web public folder
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, newFile.toPath());
                logger.log(Level.INFO, "Uploaded file: {0}", fileName);

            }
        }
        response.sendRedirect("image");
    }

    /** Get uploaded file names*/
    private List<String> getImageFileNames() {
        List<String> fileNames=new ArrayList<>();
        File[] fList = imageFolder.listFiles();
        for (File file : fList){
            if (file.isFile()){
                fileNames.add(file.getName());
            }
        }
        return fileNames;
    }
            
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Sample servlet for uploading an showing images";
    }
}
