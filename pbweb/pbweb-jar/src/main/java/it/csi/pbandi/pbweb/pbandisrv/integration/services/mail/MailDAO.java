package it.csi.pbandi.pbweb.pbandisrv.integration.services.mail;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.mail.datasource.AttachmentDataSource;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.mail.vo.AttachmentVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.mail.vo.MailVO;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.util.StringUtil;

public class MailDAO {
	public static final String MAIL_ADDRESSES_SEPARATOR = ",";
	private Session mailSession;
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private ConfigurationManager configurationManager;

	public void setMailSession(Session mailSession) {
		this.mailSession = mailSession;
	}

	public Session getMailSession() {
		return mailSession;
	}

	public void setLogger(LoggerUtil loggerUtil) {
		this.logger = loggerUtil;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setConfigurationManager(
			ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public void send(MailVO mail) {

		List<String> to = mail.getToAddresses();
		List<String> cc = mail.getCcAddresses();
		String from = mail.getFromAddress();
		logger.info("to : "+to+
		 "\nfrom : "+from +"\ncc: "+cc);
		try {
			// Instantiate a message
			Message msg = new MimeMessage(mailSession);

			// Set message attributes
			if (from != null) {
				msg.setFrom(new InternetAddress(from));
			}

			msg.setRecipients(Message.RecipientType.TO, convertAddresses(to));
			msg.setRecipients(Message.RecipientType.CC, convertAddresses(cc));
			logger.info("addresses converted");
			msg.setSubject(mail.getSubject());
			msg.setSentDate(new Date());
			if (mail.getPriority().equals(MailVO.Priority.HIGH)) {
				msg.setHeader("X-Priority", "1");
				msg.setHeader("X-MSMail-Priority", "High");
				msg.setHeader("Importance", "High");
			}
            logger.info("checkin attachments");
			if (mail.getAttachments() != null
					&& (!mail.getAttachments().isEmpty())) {
				logger.info("attachments are there!");
				BodyPart msgBodyPart = new MimeBodyPart();
//				msgBodyPart.setText(mail.getContent());
				msgBodyPart.setContent(mail.getContent(), "text/html");
				logger.info("mail.getContent() : "+mail.getContent());
				Multipart multiPart = new MimeMultipart();
				multiPart.addBodyPart(msgBodyPart);
				for (AttachmentVO vo : mail.getAttachments()) {
					if(vo!=null){
						logger.info("attachment "+vo.getName());
						DataSource ds = new AttachmentDataSource(vo.getData(),
								vo.getName(), vo.getContentType());
						BodyPart attach = new MimeBodyPart();
						attach.setDataHandler(new DataHandler(ds));
						attach.setFileName(vo.getName());
						multiPart.addBodyPart(attach);
					}else{
						logger.info("attachment is null " );
					}
				}
				logger.info("setting attachments : msg.setContent(multiPart)");
				msg.setContent(multiPart, "text/html");
			} else {
				// Set message content
				msg.setText(mail.getContent());
			}
            logger.info("before Transport.send(msg);");
			// Send the message
			Transport.send(msg);
			logger.info("after Transport.send(msg);");
		} catch (MessagingException mex) {
			logger.error("Email non inviata: " + mex.getMessage(), mex);
			
			//RuntimeException re = new RuntimeException("Email non inviata");
		//	re.initCause(mex);
		//	throw re;
		}   catch ( Exception  ex) {
			logger.error("Email non inviata: " +  ex.getMessage(), ex);
			
			//RuntimeException re = new RuntimeException("Email non inviata");
		//	re.initCause(mex);
		//	throw re;
		} 
	}
	
	/**
	 * Invio di una mail
	 * 
	 * @param fromEmail
	 * @param fromName
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @param attachment
	 */
	public void sendEmail(String fromEmail, String fromName, String toEmail, String subject, String body,
			InputStream attachment, String bccMail) {
		logger.info("[MailDAO::sendEmail] BEGIN");
		logger.info("from: " + fromEmail + " " + fromName + ", to:" + toEmail + ", subject: " + subject + ", body: "
				+ body + ", bccMail: " + bccMail);
		try {
			Properties props = System.getProperties();

			props.put("mail.smtp.host", "mailfarm-app.csi.it");

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
				attachmentPart.setFileName("");
				multipart.addBodyPart(attachmentPart);
			}
			msg.setContent(multipart);

			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			if(!StringUtil.isEmpty(bccMail)) {
				msg.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccMail, false));
			}
			logger.info("Message is ready");
			Transport.send(msg);

			logger.info("EMail spedita correttamente");
			logger.info("[MailDAO::sendEmail] END");
		} catch (Exception e) {
			logger.error("Email non inviata: " +  e.getMessage(), e);
		}
	}

	private InternetAddress[] convertAddresses(List<String> l)
			throws AddressException {
		if (l == null) {
			return new InternetAddress[] {};
		}

		InternetAddress[] addresses = new InternetAddress[l.size()];
		for (int i = 0; i < l.size(); i++) {
			addresses[i] = new InternetAddress(l.get(i));
		}
		return addresses;
	}

	public List<String> getAssistenzaEmailAddresses() {
		List<String> separatedMailAddresses = new ArrayList<String>();
		String mailAddresses = getConfigurationManager().getConfiguration()
				.getServerConfiguration().getIndirizzoEmailSupporto();
		for (String mailAddress : mailAddresses.split(MAIL_ADDRESSES_SEPARATOR)) {
			separatedMailAddresses.add(mailAddress.trim());
		}
		return separatedMailAddresses;
	}

	public List<String> getTemplateEmailAddresses() {
		List<String> separatedMailAddresses = new ArrayList<String>();
		String mailAddresses = getConfigurationManager().getConfiguration()
				.getServerConfiguration().getIndirizzoEmailSupporto();
		for (String mailAddress : mailAddresses.split(MAIL_ADDRESSES_SEPARATOR)) {
			separatedMailAddresses.add(mailAddress.trim());
		}
		mailAddresses = getConfigurationManager().getConfiguration()
				.getServerConfiguration().getIndirizzoEmailTemplate();
		if (mailAddresses != null) {
			for (String mailAddress : mailAddresses
					.split(MAIL_ADDRESSES_SEPARATOR)) {
				separatedMailAddresses.add(mailAddress.trim());
			}
		}

		return separatedMailAddresses;
	}
}
