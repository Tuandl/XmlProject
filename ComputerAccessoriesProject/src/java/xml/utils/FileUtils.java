/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author admin
 */
public class FileUtils {
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
}
