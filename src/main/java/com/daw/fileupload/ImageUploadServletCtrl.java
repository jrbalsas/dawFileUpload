/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @note Requires an images folder under TOMCAT_DIR/webapps/ for uploading and showing images-
 *
 * @author jrbalsas
 */
@WebServlet(name = "ImageUploadServletCtrl", urlPatterns = {"/image"})
@MultipartConfig(maxFileSize = 1024*1024*5) //Max upload file size 5MB
public class ImageUploadServletCtrl extends HttpServlet {

    private final String viewPath="/WEB-INF/image/";

    private final String imagesUrl="/images";  //Container public url for uploaded images p.e. http://localhost:8080/images
    private String imagePath=""; //enter the folder absolute path in server
    private File imageFolder=null;
    private Logger Log;


    @Override
    public void init() throws ServletException {
        super.init();
        
        Log = Logger.getLogger(ImageUploadServletCtrl.class.getName());
        
        //By default, place upload images in root context location http://host:8080/images/
        //Only for Tomcat container
        if (imagePath.equals("")) {
            //TODO, check upload folder or create it if needed
            imagePath=System.getProperty("catalina.base")+"/webapps"+imagesUrl;                      
        }
        Log.log(Level.INFO, "Image upload path: {0}", imagePath);
        //Open upload folder
        imageFolder=new File(imagePath);
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
        //Pass to view public URL for uploaded images 
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
            File newFile = new File(imageFolder, fileName);

            //Move temp file to web public folder
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, newFile.toPath());
                Log.log(Level.INFO, "Uploaded file: {0}", fileName);

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
