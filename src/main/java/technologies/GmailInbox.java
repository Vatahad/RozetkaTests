package technologies;

import javax.mail.*;
import javax.mail.search.SearchTerm;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

public class GmailInbox {

    private Properties props = new Properties();

    public String read(String userMail, String password) {
        String body = "";
        String typeContent = "";

        try {
            props.load(new FileInputStream(new File("src/main/resources/smtp.properties")));
            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", userMail, password);

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_WRITE);
            SearchTerm searchCondition = new SearchTerm() {
                @Override
                public boolean match(Message message) {
                    try {
                        String strOfArray = Arrays.toString(message.getAllRecipients()).replaceAll("\\[|\\]|,|\\s", "");

                        if (strOfArray.contains(userMail)) {
                            return true;
                        }
                    } catch (MessagingException e) {
                        System.out.printf("Search the letter in Inbox %s", e);
                    }
                    return false;
                }
            };
            // performs search through the folder
            System.out.println("count message in box" + inbox.getMessages().length);
            Message[] foundMessages = inbox.search(searchCondition);
            int numberOfTrying = 0;

            while (foundMessages.length == 0 && numberOfTrying < 300) {
                Thread.sleep(1000);
                numberOfTrying++;
                foundMessages = inbox.search(searchCondition);
            }

            typeContent = foundMessages[foundMessages.length - 1].getContentType();
            if (typeContent.contains("TEXT/HTML")) {
                body = foundMessages[foundMessages.length - 1].getContent().toString();
            } else {
                Multipart mp = (Multipart) foundMessages[foundMessages.length - 1].getContent();
                body = (String) mp.getBodyPart(0).getContent();
            }

            inbox.close(true);
            store.close();

        } catch (Exception e) {
            System.out.println("Some problems with mail box");
        }

        return body;
    }

}