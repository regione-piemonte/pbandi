/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.datasource.AttachmentDataSource;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.vo.AttachmentVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.vo.MailVO;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;

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
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;

public class MailDAO {
	public static final String MAIL_ADDRESSES_SEPARATOR = ",";
	private Session mailSession;
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private ConfigurationManager configurationManager;
	
	private static ResourceBundle rB ;

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
			
			rB = ResourceBundle.getBundle("conf/mail");
			String hostMail= rB.getString("mail.host");
			
			Properties prop = new Properties();
			prop.put("mail.smtp.host", hostMail);

			Session session = Session.getInstance(prop, null);
			// Instantiate a message
			Message msg = new MimeMessage(session);

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
