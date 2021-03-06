package edu.upenn.cis350;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GMailSender extends javax.mail.Authenticator {   
    private String mailhost = "smtp.gmail.com";   
    private String user;   
    private String password;   
    private Session session;   

    static {   
        Security.addProvider(new JSSEProvider());   
    }  

    public GMailSender(String user, String password) {   
        this.user = user;   
        this.password = password;   

        Properties props = new Properties();   
        props.setProperty("mail.transport.protocol", "smtp");   
        props.setProperty("mail.host", mailhost);   
        props.put("mail.smtp.auth", "true");   
        props.put("mail.smtp.port", "465");   
        props.put("mail.smtp.socketFactory.port", "465");   
        props.put("mail.smtp.socketFactory.class",   
                "javax.net.ssl.SSLSocketFactory");   
        props.put("mail.smtp.socketFactory.fallback", "false");   
        props.setProperty("mail.smtp.quitwait", "false");   

        session = Session.getDefaultInstance(props, this);   
        System.out.println("In GmailSender constructor");
    }   

    protected PasswordAuthentication getPasswordAuthentication() {   
    	System.out.println("In GmailSender Password");
    	return new PasswordAuthentication(user, password);   
    }   

    public synchronized void sendMail(String subject, String body, String sender, String recipients, ArrayList<String> filenames) throws Exception {   
        try{
        MimeMessage message = new MimeMessage(session);   
        DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));   
        message.setSender(new InternetAddress(sender));   
        message.setSubject(subject);   
        message.setDataHandler(handler);  
        MimeMultipart mp = new MimeMultipart();

        if (recipients.indexOf(',') > 0)   
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));   
        else  
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
        try
        {  int l;
        	for (String str:filenames){

          BodyPart messageBodyPart = new MimeBodyPart(); 
      	  DataSource source = new FileDataSource("/mnt/sdcard/"+str); 
      	  messageBodyPart.setDataHandler(new DataHandler(source)); 
      	  messageBodyPart.setFileName(str); 
      	  mp.addBodyPart(messageBodyPart);
        	}
//      	  BodyPart messageBodyPart1 = new MimeBodyPart(); 
//      	  DataSource source1 = new FileDataSource("/mnt/sdcard/a.csv"); 
//    	  messageBodyPart1.setDataHandler(new DataHandler(source1)); 
//    	  messageBodyPart1.setFileName("a.csv"); 
//    	  mp.addBodyPart(messageBodyPart1);
      	  
      	  message.setContent(mp);
      	  System.out.println(message.getSender() +" ");
      	System.out.println("Attachment processing");
      	
//      	Multipart multipart = new MimeMultipart("mixed");
//      	System.out.println("TO SHOW FILE NAMES");
//      	for (String str : filenames) {
//      		System.out.println(str);
//      		System.out.println("/mnt/sdcard/"+str);
//      	    MimeBodyPart messageBodyPart = new MimeBodyPart();
//      	    DataSource source = new FileDataSource("/mnt/sdcard/"+str);
//      	    messageBodyPart.setDataHandler(new DataHandler(source));
//      	    System.out.println("souce name"+source.getName());
//      	    messageBodyPart.setFileName(source.getName());
//      	    multipart.addBodyPart(messageBodyPart);
//      	}
//      	message.setContent(multipart);
    
      	
      	
      	
      	
      	
        }
        catch(Exception e)
        {
        	System.out.println("FileException");
        }
        
        System.out.println("Sending email");
        Transport.send(message);   
        }catch(Exception e){
        	System.out.println("In GmailSender Exception");
        	System.out.println(e.toString());
        	System.out.println(e.getLocalizedMessage());
        	e.printStackTrace();
        	
        }
    }   
 /*   public void addAttachment(String filename) throws Exception { 
    	  BodyPart messageBodyPart = new MimeBodyPart(); 
    	  DataSource source = new FileDataSource(filename); 
    	  messageBodyPart.setDataHandler(new DataHandler(source)); 
    	  messageBodyPart.setFileName(filename); 
    	 
    	  _multipart.addBodyPart(messageBodyPart); 
    	} */

    public class ByteArrayDataSource implements DataSource {   
        private byte[] data;   
        private String type;   

        public ByteArrayDataSource(byte[] data, String type) {   
            super();   
            this.data = data;   
            this.type = type;   
        }   

        public ByteArrayDataSource(byte[] data) {   
            super();   
            this.data = data;   
        }   

        public void setType(String type) {   
            this.type = type;   
        }   

        public String getContentType() {   
            if (type == null)   
                return "application/octet-stream";   
            else  
                return type;   
        }   

        public InputStream getInputStream() throws IOException {   
            return new ByteArrayInputStream(data);   
        }   

        public String getName() {   
            return "ByteArrayDataSource";   
        }   

        public OutputStream getOutputStream() throws IOException {   
            throw new IOException("Not Supported");   
        }   
    }   
}