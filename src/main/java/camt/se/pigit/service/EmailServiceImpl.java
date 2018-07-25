package camt.se.pigit.service;

import camt.se.pigit.entity.Product;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {

    private static String USER_NAME = "camt.se.pigit";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "pigit_123";

    @Override
    public Boolean sendEmail(Product product, int quantity,String name, String email, String addInfo) {
        String[] receiver = {"emailshop@gmail.com",email}; // list of recipient email addresses
        String subject = "[PIGIT] Summary order";

        String body = "PIGIT"+
                "\ncontact number: 0929639169"+
                "\nAddress: 22/18 suthep, Chiang Mai\n"+
                "----------------------------------------------------"+
                "\nCustomer /name: "+ name+
                "\nCustomer email: \n"+email+
                "----------------------------------------------------"+
                "\nOrder Product \n"+
                "\nProduct barcode: "+product.getBarcodenumber()+
                "\nProduct name: "+product.getName()+
                "\nProduct price: "+product.getPrice()+
                "\nQuantity: "+quantity+"\n"+
                "\nTotal price: "+(product.getPrice()*quantity)+"\n"+
                "\nAdditional information\n"+addInfo;

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", USER_NAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(USER_NAME));
            InternetAddress[] toAddress = new InternetAddress[receiver.length];
            // To get the array of addresses
            for( int i = 0; i < receiver.length; i++ ) {
                toAddress[i] = new InternetAddress(receiver[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, USER_NAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Sending success");
            transport.close();
            return true;
        }
        catch (AddressException ae) {
            ae.printStackTrace();
            return false;
        }
        catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
    }
}
