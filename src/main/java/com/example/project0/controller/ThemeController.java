package com.example.project0.controller;
import com.example.project0.exception.ResourceNotFoundException;
import com.example.project0.model.Theme;
import com.example.project0.model.Tutorial;
import com.example.project0.repositries.IThemeRepository;
import com.example.project0.repositries.ITutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ThemeController {
    @Autowired
    private ITutorialRepository tutorialRepository;

    @Autowired
    private IThemeRepository themeRepository;

    @GetMapping("/themes")
    public ResponseEntity<List<Theme>> getAllThemes() {
        List<Theme> themes = new ArrayList<Theme>();

        themeRepository.findAll().forEach(themes::add);

        if (themes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(themes, HttpStatus.OK);
    }



            @GetMapping("/tutorials/{tutorialId}/themes")
            public ResponseEntity<List<Theme>> getAllThemesByTutorialId(@PathVariable(value = "tutorialId") Long tutorialId)
            {
                if (!tutorialRepository.existsById(tutorialId)) {
                    throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
                }

                List<Theme> themes = themeRepository.findThemesByTutorialsId(tutorialId);
                return new ResponseEntity<>(themes , HttpStatus.OK);



            }

    @PostMapping("/themess")
    public Theme createTheme(@RequestBody Theme theme) {
        try {
           return themeRepository.save(theme);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }







            @GetMapping("/themes/{id}")
            public ResponseEntity<Theme> getThemesById (@PathVariable(value = "id") Long id){
                Theme tag = themeRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));

                return new ResponseEntity<>(tag, HttpStatus.OK);
            }

            @GetMapping("/themes/{themeId}/tutorials")
            public ResponseEntity<List<Tutorial>> getAllTutorialsByThemeId (@PathVariable(value = "themeId") Long themeId){
                if (!themeRepository.existsById(themeId)) {
                    throw new ResourceNotFoundException("Not found Theme  with id = " + themeId);
                }

                List<Tutorial> tutorials = tutorialRepository.findTutorialsByThemesId(themeId);
                return new ResponseEntity<>(tutorials, HttpStatus.OK);
            }


    @PutMapping("/themes/{id}")
            public ResponseEntity<Theme> updateTheme ( @PathVariable("id") long id, @RequestBody Theme themeRequest){
                Theme theme = themeRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("ThemeId " + id + "not found"));

                theme.setNom(themeRequest.getNom());

                return new ResponseEntity<>(themeRepository.save(theme), HttpStatus.OK);
            }

            @DeleteMapping("/tutorials/{tutorialId}/themes/{themeId}")
            public ResponseEntity<HttpStatus> deleteThemeFromTutorial (@PathVariable(value = "tutorialId") Long
            tutorialId, @PathVariable(value = "themeId") Long themeId){
                Tutorial tutorial = tutorialRepository.findById(tutorialId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

                tutorial.removeTheme(themeId);
                tutorialRepository.save(tutorial);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            @DeleteMapping("/themes/{id}")
            public ResponseEntity<HttpStatus> deleteTheme ( @PathVariable("id") long id){
                themeRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
