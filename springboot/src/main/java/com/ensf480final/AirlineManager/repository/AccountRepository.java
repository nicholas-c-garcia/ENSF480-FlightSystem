package com.ensf480final.AirlineManager.repository;

import com.ensf480final.AirlineManager.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByEmailAddress(String emailAddress);
    List<Account> findByAccessLevel(String accessLevel);
}
