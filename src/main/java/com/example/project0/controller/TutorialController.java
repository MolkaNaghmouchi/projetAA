package com.example.project0.controller;
import com.example.project0.model.Theme;
import com.example.project0.model.Tutorial;
import com.example.project0.repositries.IThemeRepository;
import com.example.project0.repositries.ITutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TutorialController {
    @Autowired
    ITutorialRepository tutorialRepository;

    @Autowired
    private IThemeRepository themeRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<Tutorial> tutorials = new ArrayList<Tutorial>();

            if (title == null)
                tutorialRepository.findAll().forEach(tutorials::add);
            else
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        try {
            Tutorial _tutorial = tutorialRepository
                    .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        try {
            List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/tutorials/{tutorialId}/themes/{idTheme}")
    public Tutorial UpdateThemetoTutorial(@PathVariable(value = "tutorialId") Long tutorialId, @PathVariable(value = "idTheme") Long themeId) {
        try {
            Tutorial tuto = tutorialRepository.findById(tutorialId).orElse(null);
            Theme theme = themeRepository.findById(themeId).orElse(null);
            if (tuto != null && theme != null) {
                tuto.getThemes().add(theme);
                theme.getTutorials().add(tuto);
                return tutorialRepository.save(tuto);
            } else {
                // Handle the case where either the tutorial or theme is not found
                throw new IllegalArgumentException("Tutorial or Theme not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/tutorialss/{tutorialId}")
    public Tutorial FindThemetoTutorial(@PathVariable(value = "tutorialId") Long tutorialId) {
        try {

            return tutorialRepository.findById(tutorialId).get();
        } catch (Exception e) {
            throw new RuntimeException(("tutorial introvable avec l id " + tutorialId));
        }
    }

  /*  @PutMapping("/update/{tutorialId}")
    public Tutorial updateTuto(@PathVariable(value = "tutorialId") Long tutorialId, @RequestBody Set<Theme> listTheme) {
        try {

            Tutorial t = tutorialRepository.findById(tutorialId).get();
            t.setThemes(listTheme);

            return tutorialRepository.save(t);
        } catch (Exception e) {
            throw new RuntimeException(("tutorial introvable avec l id " + tutorialId));
        }
    }*/



/*@PostMapping("/add/{tutorialId}")
  public Tutorial addTutorial(@PathVariable(value = "tutorialId") Long tutorialId, @RequestBody Set<Theme> listTheme) {
      try {
          Tutorial t = tutorialRepository.findById(tutorialId).orElseThrow(() -> new RuntimeException("Tutorial introuvable avec l'ID " + tutorialId));
          t.setThemes(listTheme);
          return tutorialRepository.save(t);
      } catch (Exception e) {
          throw new RuntimeException("Tutorial introuvable avec l'ID " + tutorialId, e);
      }
  }*/


    // @PostMapping("/add/{tutorialId}")
   /* public Tutorial addTutorial(@PathVariable(value = "tutorialId") Long tutorialId, @RequestBody Set<Theme> listTheme) {
        try {
            Tutorial t = tutorialRepository.findById(tutorialId).orElseThrow(() -> new RuntimeException("Tutorial introuvable avec l'ID " + tutorialId));

            // Supprimer les thèmes existants pour éviter les doublons
            t.getThemes().clear();

            // Ajouter les nouveaux thèmes
            for (Theme theme : listTheme) {
                t.addTheme(theme);
            }

            return tutorialRepository.save(t);
        } catch (Exception e) {
            throw new RuntimeException("Tutorial introuvable avec l'ID " + tutorialId, e);
        }
    }*/



  /*  @PostMapping("/add/{idTheme}")
    public Tutorial addTutorial(Tutorial tutorial, Long idTheme) {
        try {
            Theme theme = themeRepository.findById(idTheme)
                    .orElseThrow(() -> new RuntimeException("Post introuvable avec l'ID " + idTheme));

            tutorial.getThemes().add(theme);
            Tutorial savedTutorial = tutorialRepository.save(tutorial);
            return savedTutorial;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/


    @PostMapping("/add/{idTheme}")
    public Tutorial addTutorial(@RequestBody Tutorial tutorial, @PathVariable Long idTheme) {
        try {
            Theme theme = themeRepository.findById(idTheme)
                    .orElseThrow(() -> new RuntimeException("Thème introuvable avec l'ID " + idTheme));

            Set<Theme> themes = new HashSet<>();
            themes.add(theme);
            tutorial.setThemes(themes);

            Tutorial savedTutorial = tutorialRepository.save(tutorial);
            return savedTutorial;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    @PutMapping("/update/{tutorialId}/{idTheme}")
    public Tutorial updateTutorial(@PathVariable Long tutorialId, @RequestBody Tutorial updatedTutorial, @PathVariable Long idTheme) {
        try {
            Tutorial existingTutorial = tutorialRepository.findById(tutorialId)
                    .orElseThrow(() -> new RuntimeException("Tutoriel introuvable avec l'ID " + tutorialId));

            Theme theme = themeRepository.findById(idTheme)
                    .orElseThrow(() -> new RuntimeException("Thème introuvable avec l'ID " + idTheme));

            existingTutorial.setTitle(updatedTutorial.getTitle());
            existingTutorial.setDescription(updatedTutorial.getDescription());
            // Mettez à jour d'autres propriétés du tutoriel si nécessaire

            Set<Theme> themes = new HashSet<>();
            themes.add(theme);
            existingTutorial.setThemes(themes);

            Tutorial savedTutorial = tutorialRepository.save(existingTutorial);
            return savedTutorial;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}