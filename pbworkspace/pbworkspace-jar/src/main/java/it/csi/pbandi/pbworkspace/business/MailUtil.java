/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.business;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class MailUtil {

	/**
	 * Utility method to send simple HTML email
	 * 
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(String fromEmail, String fromName, String toEmail, String subject, String body,
			String smtpHost, InputStream attachment) {
		try {
			Properties props = System.getProperties();

			props.put("mail.smtp.host", smtpHost);

			Session session = Session.getInstance(props, null);
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.setFrom(new InternetAddress(fromEmail, fromName));
			msg.setSubject(subject, "UTF-8");

			BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent(body, "text/HTML; charset=UTF-8");			
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			if (attachment != null) {
				MimeBodyPart attachmentPart = new MimeBodyPart();
				DataSource source = new ByteArrayDataSource(attachment, "application/rtf");
				attachmentPart.setDataHandler(new DataHandler(source));
				attachmentPart.setDisposition(Part.ATTACHMENT);
			    attachmentPart.setHeader("Content-Transfer-Encoding", "base64");
				attachmentPart.setFileName("Delega.rtf");
				multipart.addBodyPart(attachmentPart);
			}
			msg.setContent(multipart);

			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			System.out.println("Message is ready");
			Transport.send(msg);

			System.out.println("EMail Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}