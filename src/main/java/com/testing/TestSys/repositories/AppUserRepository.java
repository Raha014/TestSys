package com.testing.TestSys.repositories;

import com.testing.TestSys.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    public AppUser findByUsername(String username);
}
