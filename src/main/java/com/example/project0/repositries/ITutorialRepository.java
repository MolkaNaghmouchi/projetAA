package com.example.project0.repositries;

import com.example.project0.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITutorialRepository extends JpaRepository<Tutorial , Long> {


    List<Tutorial> findByPublished(boolean published);
    List<Tutorial> findByTitleContaining(String title);
}
