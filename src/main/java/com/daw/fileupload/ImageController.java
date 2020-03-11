package com.daw.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

/**
 * @author jrbalsas
 */
@RequestScoped
@Named ("imgCtrl")
public class ImageController implements Serializable{
                
    private static final Logger logger = Logger.getLogger(ImageController.class.getName());
    
    @Inject
    FacesContext fc;
            
    //Images filesystem path and URL as defined in /WEB-INF/glassfish-web.xml
    private final String imagesPath="/tmp/images";   //enter the folder absolute path in server filesystem
    private final String imagesUrl="/images";       //Images public URL 
                                                       
    private File imageFolder;
    
    private  Part file;

    public ImageController() {
    }
      
    @PostConstruct
    public void init() {
    //Open upload folder
        imageFolder=new File(imagesPath);

        logger.log(Level.INFO, "Image upload path: {0}", imagesPath);                
    }

    //view-model properties
    
    public String getImagesUrl() {
        return imagesUrl;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    //view actions
    
    public String sendFile() {
            String newFileName = file.getSubmittedFileName();

            //Move temp file to web public folder
            try (InputStream input = file.getInputStream()) {
                File destFile = new File(imageFolder, newFileName);
                Files.copy(input, destFile.toPath());
                fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"Archivo enviado correctamente",""));
                logger.log(Level.INFO, "Uploaded file: {0}", newFileName);
            } catch (IOException e) {
                fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido enviar el archivo",""));
            }
        
        return "";
    }

    /*Util methods*/
    
    /** Get uploaded file names*/
    public List<String> getImageFileNames() {
        List<String> fileNames=new ArrayList<>();
        File[] fList = imageFolder.listFiles();
        for (File file : fList){
            if (file.isFile()){
                fileNames.add(file.getName());
            }
        }
        return fileNames;
    }
    
}
