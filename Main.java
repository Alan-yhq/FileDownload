package filedownload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static String u;
    static ExecutorService es = Executors.newFixedThreadPool(1000);
    static FileOutputStream writer;
    public static void main(String[] args){    
        try {
            File f = new File(URLDecoder.decode(getName("https.txt")));
            f.createNewFile();
            writer = new FileOutputStream(URLDecoder.decode(getName("https.txt")));
            take(new URL("https://img.fczbl.vip/"));    
        } catch (MalformedURLException ex) {
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }
    public static void take(URL url){
        try {
            InputStream is = url.openStream();
            byte data[] = new byte[1024];
            while (is.read(data) != -1){
                String line = new String(data);  
                char[] cs = line.replace("\\", "").toCharArray();                
                for (int i = 0;i < cs.length-5;i++){
                    if (cs[i] == 'h' && cs[i+1] == 't' && cs[i+2] == 't' && cs[i+3] == 'p' && (cs[i+4] == 's' || cs[i+4] == ':') && (cs[i+5] == ':' || cs[i+5] == '/')){
                        u = "";
                        for (int j = i;j < cs.length && cs[j] != '"' && cs[j] != '\'';j++){
                            u += cs[j];
                        }
                        System.out.print(i);
                        if (u.contains("jpg") || u.contains("png") || u.contains("gif") || u.contains("bmp")){                                   
                            download(new URL(u));
                        }
                        writer.write((u+"\r\n").getBytes());
                    }
                }               
            }
        } catch (IOException ex) {
        }
    }
    private static void download(URL url) throws IOException {
        try (InputStream is = url.openStream()) {
            if (!new File(getName(url.getPath().split("/")[url.getPath().split("/").length-1])).exists()){
                FileOutputStream writer;
                writer = new FileOutputStream(URLDecoder.decode(getName(url.getPath().split("/")[url.getPath().split("/").length-1]))); 
                int len;
                byte data[] = new byte[1024];
                while((len = is.read(data))!=-1){
                    writer.write(data,0,len);
                }
                writer.flush();
                writer.close();
            }
        }
    }
    private static String getName(String _new){
        String[] strings = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("/", "\\\\").split("\\\\");
        strings[strings.length-1] = _new;
        String r = "";
        for (int i = 0;i < strings.length-2;i++){
            if (i != strings.length-3){
                r += strings[i+2] + "\\\\";
            } else {
                r += strings[i+2];
            }
        }
        return r;
    }
}