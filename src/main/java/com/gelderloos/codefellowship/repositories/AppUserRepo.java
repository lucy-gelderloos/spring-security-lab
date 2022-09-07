package com.gelderloos.codefellowship.repositories;

import com.gelderloos.codefellowship.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO: Step1B: Make user repository (INTERFACE!!!!)
public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
