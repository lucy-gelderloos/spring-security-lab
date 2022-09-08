package com.gelderloos.codefellowship.repositories;

import com.gelderloos.codefellowship.models.AppPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppPostRepository extends JpaRepository<AppPost, Long> {
//    public AppPost findByPostsByAuthor(String username);
}
