package sjsu.edu.flightreservation.util;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

	@Value("${flightreservation.itinerary.email.subject}")
	private String EMAIL_SUBJECT;
	
	@Value("${flightreservation.itinerary.email.body}")
	private String EMAIL_BODY;

	@Autowired
	private JavaMailSender sender;
	
	public void sendItinerary(String toAddress, String filePath) {
		LOGGER.info("Executing sendItinerary() << {}", toAddress);
		
		MimeMessage message = sender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(toAddress);
			helper.setSubject(EMAIL_SUBJECT);
			helper.setText(EMAIL_BODY);
			helper.addAttachment("Itinerary", new File(filePath));
			sender.send(message);
		} catch (MessagingException e) {
			LOGGER.error("Exception in Sending Itinerary {}", e);
			e.printStackTrace();
		}
		
		LOGGER.info("Exiting sendItinerary() >>");
	}
}
