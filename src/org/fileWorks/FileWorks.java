package org.fileWorks;

import com.google.gson.Gson;
import org.player.Player;

import java.io.*;
import java.util.Scanner;


public class FileWorks {
    public static void filewrite(String fileName , Object object) throws IOException {
        File myObj = new File(fileName);
        myObj.createNewFile();
        String data = new Gson().toJson(object);
        FileWriter fileWriter = new FileWriter(fileName, true);
        fileWriter.write(data + "\r\n");
        //fileWriter.flush();
        fileWriter.close();

    }
    public static boolean twoEqualsUserName(String fileName,String username)throws IOException{
        File myObj = new File(fileName);
        myObj.createNewFile();
        Scanner fileReader = new Scanner(new FileReader(fileName));
        while (fileReader.hasNext()){
            Player player = new Gson().fromJson(fileReader.nextLine() , Player.class);
            if (player.getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }

}
