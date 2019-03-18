/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 *
 * @author admin
 */
public class FileUtils {
    public static final String PRODUCT_IMG_FOLDER = "productimg";
    
    public static void saveIntoFile(String path, String content){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File(path)));
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void saveImageFromUrl(String urlStr, String location) {
        try (InputStream is = new URL(urlStr).openStream()) {
            Files.copy(is, Paths.get(location));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getFileExtension(String path) {
        int dotIndex = path.indexOf(".");
        
        if(dotIndex == -1) {
            return path;
        } else {
            String subString = path.substring(dotIndex + 1);
            return getFileExtension(subString);
        }
    }
    
    public static void saveProductRawImage(String urlStr, String imgName) {
        String currentPath = System.getProperty("user.dir");
        String folderPath = currentPath + "\\" + PRODUCT_IMG_FOLDER;
        String imgPath = currentPath + "\\" + PRODUCT_IMG_FOLDER + "\\" + imgName;
        
        File directory = new File(folderPath);
        if(!directory.exists()) {
            directory.mkdir();
        }
        
        saveImageFromUrl(urlStr, imgPath);
    }
    
    public static void writeImageToOutputStream(String imagePath, OutputStream outputStream) {
        try {
            String extension = getFileExtension(imagePath);
            File file = new File(imagePath);
            BufferedImage bufferedImage = ImageIO.read(file);
            ImageIO.write(bufferedImage, extension.toUpperCase(), outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void writeProductRawImageToOutputStream(String imgName, OutputStream outputStream) {
        try {
            String currentPath = System.getProperty("user.dir");
            String imgPath = currentPath + "\\" + PRODUCT_IMG_FOLDER + "\\" + imgName;
            writeImageToOutputStream(imgPath, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean checkFileExisted(String filePath) {
        try {
            File file = new File(filePath);
            boolean existed = file.exists();
            return existed;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean checkProductImageExisted(String imgName){
        String currentPath = System.getProperty("user.dir");
        String imgPath = currentPath + "\\" + PRODUCT_IMG_FOLDER + "\\" + imgName;
        return checkFileExisted(imgPath);
    }
}
