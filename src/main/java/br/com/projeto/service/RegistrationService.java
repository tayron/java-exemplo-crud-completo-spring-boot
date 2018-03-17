package br.com.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.projeto.dto.RegistrationDTO;
import br.com.projeto.model.Group;
import br.com.projeto.model.User;
import br.com.projeto.repository.GroupRepository;
import br.com.projeto.repository.UserRepository;

@Service
public class RegistrationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private SendMailService sendMailService;

	public void registerUser(final RegistrationDTO registrationUser, final BindingResult result) {

		User user = new User();
		user.setName(registrationUser.getName());
		user.setAvatar(registrationUser.getAvatar());
		user.setEmail(registrationUser.getEmail());
		user.setOccupation(registrationUser.getOccupation());
		user.setPassword(bCryptPasswordEncoder.encode(registrationUser.getPassword()));
		user.setSite(registrationUser.getSite());
		user.setActive(true);

		Group userGroup = groupRepository.findByGroup("USER");
		user.setGroup(userGroup);

		userRepository.save(user);
		try {
			String msg = "Here is a message to send to user  with the confirmation!";
			sendMailService.sendMail(user.getName(), user.getEmail(), msg);
		} catch (Exception e) {
			// add error sendMail, but proceed with registration.
		}
	}
}
