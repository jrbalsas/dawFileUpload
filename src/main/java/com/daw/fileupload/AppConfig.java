
package com.daw.fileupload;

import jakarta.faces.annotation.FacesConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 *
 * @author jrbalsas
 */

@FacesConfig  //init JSF front-controller
public class AppConfig  {

    /** Create folder if not exists*/
    public static boolean initFolder(String folderPath) {
        Logger log=Logger.getLogger(AppConfig.class.getName());
        Path path = Paths.get(folderPath);
        boolean created = false;
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                log.info("Created folder: "+folderPath);
                created=true;
            } catch (IOException ex) {
                log.severe("Error creating folder: "+ex.getMessage());
            }
        }
        return created;
    }
}
