package com.pres.util.file;

import com.pres.constants.ErrorMessage;
import com.pres.constants.ServletContent;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class used for performing operations on images.
 *
 * @author Pres Roman
 **/
public class Photo {
    private static final Logger LOG = Logger.getLogger(Photo.class);
    private static final String EXPANSION = ".jpg";
    private static String PATH;

    public static void init(String path) {
        PATH = path;
    }

    /**
     * Saves an image to application.
     *
     * @throws IOException if file path is not found
     **/
    public static void saveImage(Part part, int id) throws IOException {
        String nameImg = id + EXPANSION;
        File file = new File(PATH + ServletContent.PROJECT_NAME + "\\src\\main\\webapp\\img\\" + nameImg);
        try {
            BufferedInputStream bis = new BufferedInputStream(part.getInputStream());
            FileOutputStream fos = new FileOutputStream(file);
            int ch;
            while ((ch = bis.read()) != -1) {
                fos.write(ch);
            }
        } catch (IOException e) {
            LOG.error(ErrorMessage.ERR_CANNOT_SAVE_PHOTO, e);
            throw new IOException(ErrorMessage.ERR_CANNOT_SAVE_PHOTO, e);
        }
    }

    /**
     * Deletes an image to application.
     *
     * @throws IOException if file path is not found.
     **/
    public static void deleteImage(int id) {
        String path = PATH + ServletContent.PROJECT_NAME + "\\src\\main\\webapp\\img\\" + id + EXPANSION;
        File file = new File(path);
        if (file.delete()) {
            LOG.info("File " + path + " has been deleted");
        } else {
            LOG.info("File " + path + " hasn't been deleted");
        }
    }
}
