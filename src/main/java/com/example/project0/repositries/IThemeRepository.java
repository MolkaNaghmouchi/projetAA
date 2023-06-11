package com.example.project0.repositries;
import com.example.project0.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface IThemeRepository extends JpaRepository<Theme,Long> {
    List<Theme> findThemesByTutorialsId(Long tutorialId);


}
