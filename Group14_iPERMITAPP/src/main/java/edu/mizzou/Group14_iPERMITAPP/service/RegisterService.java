package edu.mizzou.Group14_iPERMITAPP.service;

import edu.mizzou.Group14_iPERMITAPP.model.RE;
import edu.mizzou.Group14_iPERMITAPP.repository.RERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegisterService {

    @Autowired
    private RERepository reRepository;

    public boolean login(String email, String password) {
        RE user = reRepository.findByEmail(email);

        if (user == null) return false;

        return user.getPassword().equals(password);
    }

    public boolean register(String email, String password) {
        if (reRepository.findByEmail(email) != null) {
            return false; // user already exists
        }

        RE user = new RE();
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedDate(new Date());

        reRepository.save(user);

        return true;
    }
}