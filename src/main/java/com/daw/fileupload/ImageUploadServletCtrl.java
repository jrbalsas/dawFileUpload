/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daw.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
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

/**
 *
 * @author jrbalsas
 */
@WebServlet(name = "ImageUploadServletCtrl", urlPatterns = {"/image"})
@MultipartConfig
public class ImageUploadServletCtrl extends HttpServlet {

    private final String viewPath="/WEB-INF/image/";
    private String imagePath=""; //enter the folder absolute path in server
    private File imageFolder=null;
    private Logger Log;


    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        
        Log = Logger.getLogger(ImageUploadServletCtrl.class.getName());
        
        //By default, place upload images in root context location http://host:8080/images/
        //Only in Tomcat container
        if (imagePath.equals("")) {
            //TODO, check upload folder or create it if needed
            imagePath=System.getProperty("catalina.base")+"/webapps/images";                      
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

        //TODO, list files in view

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
        response.sendRedirect("");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
