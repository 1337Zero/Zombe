package me.zero.cc.Zero_lite.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import me.zero.cc.Zero_lite.LiteModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import org.apache.commons.io.IOUtils;

public class UpdateChecker extends Thread{

	private URL url;
	private String version = "";
	private ArrayList<String> messages = new ArrayList<String>();
	private LiteModMain main;
	
	public UpdateChecker(LiteModMain main){
		this.main = main;
		
		try {
			url = new URL("http://pastebin.com/raw.php?i=nVNLqVWR");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		readHTMLPage(url);		
	}
	private void readHTMLPage(URL url) {
        BufferedReader reader = null;
         
        try {

			this.sleep(10000);
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            
            String readed = reader.readLine();
            
            while(readed != null){  
            	String between = readed;
            	
            	if(main.getUrlVersion() == ""){
            		main.setUrlVersion(readed);
            	}else if(main.getDownloadURL() == ""){
            			main.setDownloadURL(readed);            		
            	}else{
                	messages.add(readed);
            	}
            	readed = reader.readLine();
            } 
            main.setAnnouncement(messages);
            reader.close();
        }catch(IOException e){
        	e.printStackTrace();
        }catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
