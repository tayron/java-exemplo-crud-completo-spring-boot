package br.com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projeto.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

	Group findByGroup(String group);

}
