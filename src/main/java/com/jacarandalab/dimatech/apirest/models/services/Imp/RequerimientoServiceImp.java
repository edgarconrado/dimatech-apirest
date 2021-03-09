package com.jacarandalab.dimatech.apirest.models.services.Imp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import javax.mail.internet.MimeMessage;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

import com.jacarandalab.dimatech.apirest.models.dao.IRequerimientoDao;
import com.jacarandalab.dimatech.apirest.models.entity.Email;
import com.jacarandalab.dimatech.apirest.models.entity.Requerimiento;
import com.jacarandalab.dimatech.apirest.models.services.IRequerimientoService;

@Service
public class RequerimientoServiceImp implements IRequerimientoService {

	private static final String PDF_RESOURCES = "/static/";
	
	@Autowired
	private IRequerimientoDao requerimientoDao;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Override
	@Transactional(readOnly = true)
	public List<Requerimiento> findAll() {
		return (List<Requerimiento>) requerimientoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Requerimiento findById(Long id) {
		return requerimientoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Requerimiento save(Requerimiento requerimiento) {
		
		//Send Email				
		this.sendEmail(requerimiento );
		
		return requerimientoDao.save(requerimiento);
	}
	

	@Override
	@Transactional
	public void delete(Long id) {
		requerimientoDao.deleteById(id);		
	}
	
	
	private void sendEmail(Requerimiento requerimiento) {
		// Send Email
		MimeMessageHelper mimeMessageHelper;
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		
		String estudios = requerimiento.getEstudios();
		String str[] = estudios.split(",");
		List<String> estuReq = new ArrayList<String>();
		estuReq = Arrays.asList(str);

		try {

			Context context = new Context();
			context.setVariable("name", requerimiento.getNombrePaciente());
			context.setVariable("doctor", requerimiento.getNombreDoctor());
			context.setVariable("estudios", estuReq);
			context.setVariable("imageResourceName", "imageResourceName");

			String process = templateEngine.process("requerimiento-temlate", context);

			Email email = new Email();
			
			String[] cc = new String[1];
			if (requerimiento.getEmailPaciente() != "") {
				cc[0] = requerimiento.getEmailPaciente();	
			}

			String[] bcc = new String[1];
			if (requerimiento.getEmailDoctor() != "") {
				bcc[0] = requerimiento.getEmailDoctor();	
			}
			
			
			email.setTo(new String[] { "dimatechpv.mx@gmail.com"});
			
			if (cc.length > 0 ) {
				email.setCc(cc);	
			}
				

			if (bcc.length > 0 ) {
				email.setBcc(bcc);	
			}
			
			email.setFrom("dimatechpv.mx@gmail.com");
			email.setSubject("Nuevo requerimiento de Estudio");
			
			
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
			mimeMessageHelper.setTo(email.getTo());
			if (requerimiento.getEmailPaciente() != "") {
				mimeMessageHelper.setCc(email.getCc());	
			}

			if (requerimiento.getEmailDoctor() != "") {
				mimeMessageHelper.setBcc(email.getBcc());
			}
			
			
			mimeMessageHelper.setFrom(email.getFrom());
			mimeMessageHelper.setSubject(email.getSubject());

			mimeMessageHelper.addAttachment("Requerimiento", renderPdf(process));		
 			mimeMessageHelper.setText(process, true);

			javaMailSender.send(mimeMessage);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
 
    private File renderPdf(String html) throws IOException, DocumentException {
        File file = File.createTempFile("paciente", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        //ITextRenderer renderer = new ITextRenderer();
        
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }
	
 

}
