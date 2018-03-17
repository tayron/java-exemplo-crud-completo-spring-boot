package br.com.projeto.service;

import org.springframework.stereotype.Service;

@Service
public class SendMailService {
	
	public void sendMail(final String name, final String mail, final String msg){
		configureDestination(name, mail);
		applyTemplate(msg);
		sendMail();
	}
	
	private void applyTemplate(final String msg){
		
	}
	
	private void configureDestination(final String name, final String mail){
		
	}
	
	private void sendMail(){
		
	}
}
