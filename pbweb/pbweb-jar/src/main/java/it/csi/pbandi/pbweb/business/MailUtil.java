package it.csi.pbandi.pbweb.business;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.datasource.AttachmentDataSource;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.mail.vo.AttachmentVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.mail.vo.MailVO;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.ConversionUtil;

@Component
public class MailUtil {

	/**
	 * Utility method to send simple HTML email
	 * 
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	private static Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	private ConfigurationManager configurationManager;

	private Session mailSession;

	public void setMailSession(Session mailSession) {
		this.mailSession = mailSession;
	}

	public Session getMailSession() {
		return mailSession;
	}

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	private static final ResourceBundle RESOURCE_BUNDLE;

	static {
		RESOURCE_BUNDLE = ResourceBundle.getBundle("conf.mail");
	}

	public MailUtil() {
		Properties mailProperties = ConversionUtil.convertResourceBundleToProperties(RESOURCE_BUNDLE);
		mailSession = Session.getInstance(mailProperties);
	}

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

	// Dal vecchio - MailDAO
	public void send(MailVO mail) {
		String prf = "[MailUtil::send]";
		LOG.info(prf + " START");
		List<String> to = mail.getToAddresses();
		List<String> cc = mail.getCcAddresses();
		String from = mail.getFromAddress();
		LOG.info(prf + " to : " + to + "\nfrom : " + from + "\ncc: " + cc);
		try {
			// Instantiate a message
			Message msg = new MimeMessage(mailSession);

			// Set message attributes
			if (from != null) {
				msg.setFrom(new InternetAddress(from));
			}

			msg.setRecipients(Message.RecipientType.TO, convertAddresses(to));
			msg.setRecipients(Message.RecipientType.CC, convertAddresses(cc));
			LOG.info("addresses converted");
			msg.setSubject(mail.getSubject());
			msg.setSentDate(new Date());
			if (mail.getPriority().equals(MailVO.Priority.HIGH)) {
				msg.setHeader("X-Priority", "1");
				msg.setHeader("X-MSMail-Priority", "High");
				msg.setHeader("Importance", "High");
			}
			LOG.info(prf + " checkin attachments");
			if (mail.getAttachments() != null && (!mail.getAttachments().isEmpty())) {
				LOG.info(prf + " ci sono allegati!");
				BodyPart msgBodyPart = new MimeBodyPart();
//				msgBodyPart.setText(mail.getContent());
				msgBodyPart.setContent(mail.getContent(), "text/html");
				LOG.info("mail.getContent() : " + mail.getContent());
				Multipart multiPart = new MimeMultipart();
				multiPart.addBodyPart(msgBodyPart);
				for (AttachmentVO vo : mail.getAttachments()) {
					if (vo != null) {
						LOG.info(prf + "attachment " + vo.getName());
						DataSource ds = new AttachmentDataSource(vo.getData(), vo.getName(), vo.getContentType());
						BodyPart attach = new MimeBodyPart();
						attach.setDataHandler(new DataHandler(ds));
						attach.setFileName(vo.getName());
						multiPart.addBodyPart(attach);
					} else {
						LOG.info(prf + " attachment é null ");
					}
				}
				LOG.info(prf + " setting attachments : msg.setContent(multiPart)");
				msg.setContent(multiPart, "text/html");
			} else {
				// Set message content
				msg.setText(mail.getContent());
			}
			LOG.info(prf + " before Transport.send(msg);");
			// Send the message
			Transport.send(msg);
			LOG.info(prf + " after Transport.send(msg);");
			LOG.info(prf + " END");
		} catch (MessagingException mex) {
			LOG.error(prf + " Email non inviata: " + mex.getMessage(), mex);

			// RuntimeException re = new RuntimeException("Email non inviata");
			// re.initCause(mex);
			// throw re;
		} catch (Exception ex) {
			LOG.error(prf + "Email non inviata: " + ex.getMessage(), ex);

			// RuntimeException re = new RuntimeException("Email non inviata");
			// re.initCause(mex);
			// throw re;
		}
	}

	private static InternetAddress[] convertAddresses(List<String> l) throws AddressException {
		if (l == null) {
			return new InternetAddress[] {};
		}

		InternetAddress[] addresses = new InternetAddress[l.size()];
		for (int i = 0; i < l.size(); i++) {
			addresses[i] = new InternetAddress(l.get(i));
		}
		return addresses;
	}

	public List<String> getAssistenzaEmailAddress() throws Exception {
		String prf = "[MailUtil :: getAssistenzaEmailAddress]";
		LOG.info(prf + " START");
		List<String> separatedMailAddresses = new ArrayList<String>();
		configurationManager.init();
		String mailAddresses = configurationManager.getCurrentConfiguration().getServerConfiguration()
				.getIndirizzoEmailSupporto();
		for (String mailAddress : mailAddresses.split(Constants.MAIL_ADDRESSES_SEPARATOR)) {
			separatedMailAddresses.add(mailAddress.trim());
		}
		LOG.info(prf + " mailAddresses : " + mailAddresses);
		LOG.info(prf + " END");
		return separatedMailAddresses;
	}

}