package com.sprinklr.InternProjectBot.FileUtils;

import java.io.InputStream;
import java.util.Scanner;


public class FileOperations {

    public static String readFileAsString(String fileName)

    {
        String data = null;
        try {
            InputStream is = FileOperations.class.getClassLoader().getResourceAsStream(fileName);
            Scanner scanner = new Scanner(is);
            data = "";
            while (scanner.hasNextLine()) {
                data += scanner.nextLine();
            }
            is.close();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return data;
    }
}
