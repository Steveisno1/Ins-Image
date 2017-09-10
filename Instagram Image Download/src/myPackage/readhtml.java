package myPackage;

import java.io.*;
import java.net.*;
import java.nio.channels.*;

public class readhtml {
	
    public static void main(String[] args) throws Exception {

        URL oracle = new URL("https://www.instagram.com/p/4pU6nxj4kT/?taken-by=katay133");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));
        String inputLine;
        String link = "";
        while ((inputLine = in.readLine()) != null) {
        	if (inputLine.indexOf("og:image") != -1) {
        		int index = inputLine.indexOf("content=\"") + 9;
        		String rest = inputLine.substring(index);
        		int end = rest.indexOf("\"");
        		link = rest.substring(0, end);
        		System.out.println(link);
        		break;
        	}
        }
        in.close();
        URL website = new URL(link);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        String[] split = link.split("/");
        FileOutputStream fos = new FileOutputStream(split[split.length - 1]);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}