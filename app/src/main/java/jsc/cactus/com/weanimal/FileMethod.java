package jsc.cactus.com.weanimal;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by nyyyn on 2015-10-06.
 */
public class FileMethod {

    private File folder;
    private File file;

    //FileMethod fm = new FileMethod(activity, new File(this.getFilesDir()+"/hi/"), "test.txt" )

    public FileMethod(File folder, String filename) {
        this.folder = folder;
        this.file = new File(folder.getPath()+"/"+filename);
    }

    public void writeFile(String append) {
        try {
            if (!file.exists()) {
                folder.mkdir();
                file.createNewFile();
            }

//            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            bw.append(append);
            bw.flush();
            bw.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public String readFile() {
        String content = "";
        try {
            if (!file.exists()) {
                return "";
            }

            BufferedReader br = new BufferedReader(new FileReader(file));

            while (br.read() != -1) {
                content += br.readLine();
            }
            br.close();
            return content;
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

        return content;
    }
}
