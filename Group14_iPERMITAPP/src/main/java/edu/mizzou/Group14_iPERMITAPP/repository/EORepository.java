package edu.mizzou.Group14_iPERMITAPP.repository;

import edu.mizzou.Group14_iPERMITAPP.model.EO;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EORepository extends JpaRepository<EO, String> {
	EO findByEmail(String email);
}
