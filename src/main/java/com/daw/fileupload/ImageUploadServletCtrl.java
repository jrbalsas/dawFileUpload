package com.daw.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/** Sample servlet for uploading files to server and show them
 *
 * @note Requires an images folder on filesystem for uploading and showing images
 *
 * @author jrbalsas@ujaen.es
 */
@WebServlet(name = "ImageUploadServletCtrl", urlPatterns = {"/images"})
@MultipartConfig(maxFileSize = 1024*1024*5) //Max upload file size 5MB
public class ImageUploadServletCtrl extends HttpServlet {


    private final String viewPath="/WEB-INF/images/"; //Servlet templates folder

    private String imagesPath; //images sub-folder in user home, e.g. /home/username/webimages
    private String imagesUrl ="imagen";  //Images public URL (servlet url), e.g. /imagen
                                                     
    private File imagesFolder=null;
    private static final Logger logger = Logger.getLogger(ImageUploadServletCtrl.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();

        //get images folder path from web.xml
        imagesPath=getServletContext().getInitParameter("imagesPath");
        //create images folder if not exists
        AppConfig.initFolder(imagesPath);

        logger.log(Level.INFO, "Image upload path: {0}", imagesPath);

        //Open upload folder
        imagesFolder=new File(imagesPath);


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
            File newFile = new File(imagesFolder, fileName);

            //Move temp file to web public folder
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, newFile.toPath());
                logger.log(Level.INFO, "Uploaded file: {0}", fileName);

            }
        }
        response.sendRedirect("images");
    }

    /** Get uploaded file names*/
    private List<String> getImageFileNames() {
        List<String> fileNames=new ArrayList<>();
        File[] fList = imagesFolder.listFiles();
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
