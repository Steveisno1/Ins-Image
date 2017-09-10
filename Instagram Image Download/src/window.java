
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.*;

import java.io.*;
import java.net.*;
import java.nio.channels.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class window extends JFrame {
	private JLabel title = new JLabel("Instagram Image Download"), http = new JLabel("Paste the link here..."), error = new JLabel("Invalid link");
	private JTextField text = new JTextField();
	private JButton download = new JButton("Download");
	private JFileChooser fc = new JFileChooser();
	JPanel pane1 = new JPanel(new GridBagLayout());
	public String extractLink(String s) {
		try {
		URL oracle;
		oracle = new URL(s);
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
        		return link;
        	}
        }
        in.close();
        return "";
		} catch (Exception e) {
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = 6;
			pane1.add(error, c);
			return "";
		}
		
	}
	
	public window(String name) {
		super(name);
		//FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG files", "jpg");
		//fc.setFileFilter(filter);
        fc.setCurrentDirectory(new File("."));
        fc.setMultiSelectionEnabled(false);
		fc.setApproveButtonText("Save");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		
		pane1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    pane1.setBorder(new TitledBorder(new EtchedBorder(), "Instagram Image Download") );
		
	    /*GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.CENTER;
		c.ipadx = 50;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 12;
		pane1.add(title, c);*/
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 20, 0, 20);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 50;
		c.gridx = 0;
		c.gridy = 1;
		pane1.add(http, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 50;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 12;
		c.weightx = 400;
		pane1.add(text, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 12;
		c.insets = new Insets(10, 0, 0, 0);
		pane1.add(download, c);
		download.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				String getlink = text.getText();
	            String s = extractLink(getlink);
	            URL website;
				try {
					website = new URL(s);
					ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		            String[] split = s.split("/");
		            FileOutputStream fos = new FileOutputStream(split[split.length - 1]);
		            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		download.setMnemonic(KeyEvent.VK_ENTER);
		getRootPane().setDefaultButton(download);
		
		getContentPane().add(pane1);
		setSize(500,150);
		
		setResizable(false);
		setLocationRelativeTo(null); 
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        //int result = fc.showSaveDialog(null);
		//if(result == JFileChooser.CANCEL_OPTION) {
			//dispose();
		//}
        //if(result == JFileChooser.APPROVE_OPTION) {
        	
        //}
		
	}
	public static void main(String[] args) {
		window w = new window("Demo");
	}
}