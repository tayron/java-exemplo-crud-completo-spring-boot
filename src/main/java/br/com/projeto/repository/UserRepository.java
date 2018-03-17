package br.com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);
	
	Integer countByEmail(String email);
	
}
