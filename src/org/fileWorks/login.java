package org.fileWorks;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class login {
    public static String Time() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return simpleDateFormat.format(calendar.getTime());
    }

    public static void createLog(String username, String password) throws IOException {
        String name = username + "-userid-log.txt";
        File myObj = new File(name);
        myObj.createNewFile();
        FileWriter fileWriter = new FileWriter(name, true);
        fileWriter.write("USER:" + username + "\r\n");
        fileWriter.write("CREATED_AT:" + login.Time() + "(timestamp)" + "\r\n");
        fileWriter.write("PASSWORD:" + password + "\r\n");
        fileWriter.write("\r\n");
        //fileWriter.flush();
        fileWriter.close();
    }

    public static void deletePlayer(String username) throws IOException {
        int counter = 0;
        String name = username + "-userid-log.txt";
        FileWriter fileWriter = new FileWriter("temporary.log");
        Scanner fileReader = new Scanner(new FileReader(name));
        while (fileReader.hasNext()){
            fileWriter.write(fileReader.nextLine() + "\r\n");
            counter++;
            if (counter == 4) {
                fileWriter.write("DELETED_AT:" + login.Time() + "\r\n");
            }
        }
        fileWriter.flush();
        fileReader.close();
        fileWriter.close();
        File file1 = new File(name);
        File file2 = new File("temporary.log");
        file1.delete();
        file2.renameTo(file1);
    }

    public static void body(String username, String event, String description) throws IOException {
        String name = username + "-userid-log.txt";
        FileWriter fileWriter = new FileWriter(name, true);
        fileWriter.write(event + " " + login.Time() + " " + description + "\r\n");
        fileWriter.flush();
        fileWriter.close();
    }
}
