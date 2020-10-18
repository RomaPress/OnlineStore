package com.pres.util.file;

import com.pres.constants.ErrorMessage;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Photo {
    private static final Logger LOG = Logger.getLogger(Photo.class);

    private static String PATH ;

    public static void init(String path){
        PATH = path;
    }

    /**
     * Saves an image to DB.
     * @throws IOException if file path is not found
     **/
    public static void saveImage(Part part, int id) throws IOException {
        String nameImg = id + ".jpg";
        File file = new File(PATH + "onlineshop\\src\\main\\webapp\\img\\" + nameImg);
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

    public static void deleteImage(int id) {
        String path = PATH + "onlineshop\\src\\main\\webapp\\img\\" + id + ".jpg";
        File file = new File(path);
        if (file.delete()) {
            LOG.info("File " + path + " has been deleted");
        }else{
            LOG.info("File " + path + " hasn't been deleted");
        }
    }
}
