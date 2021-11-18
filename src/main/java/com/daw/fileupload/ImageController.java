package com.daw.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

/** Sample JSF controller for uploading files to server and show them
 *
 * @note Requires an images folder on filesystem for uploading and showing images
 * @see glassfish-web.xml for JEE server deployment or context.xml for Tomcat deployment)
 *
 * @author jrbalsas@ujaen.es
 */
@RequestScoped
@Named ("imgCtrl")
public class ImageController implements Serializable{
                
    private static final Logger logger = Logger.getLogger(ImageController.class.getName());
    
    @Inject
    FacesContext fc;
            
    //Images filesystem path and URL as defined in /WEB-INF/glassfish-web.xml
    private final String imagesPath="/tmp/images";   //enter the folder absolute path in server filesystem, e.g. c:/tmp/images
    private final String imagesUrl="/images";       //Images public URL 
                                                          
    private  Part file;

    public ImageController() {
    }
      
    @PostConstruct
    public void init() {

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
                Path destFile=Path.of(imagesPath,newFileName);
                Files.copy(input, destFile);
                fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"Archivo enviado correctamente",""));
                logger.log(Level.INFO, "Uploaded file: {0}", newFileName);
            } catch (IOException e) {
                fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se ha podido enviar el archivo",""));
            }
        
        return "";
    }

    /*Util methods*/
    
    /**
     * Get uploaded file names
     */
    public List<String> getImageFileNames() throws IOException {
        List<String> fileNames=new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(imagesPath))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileNames.add( path.getFileName().toString() );
                }
            }
        }
        return fileNames;
    }
    
}
