package com.example.project0.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="tutorials")
@AllArgsConstructor

public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title")
    private String title;


    public Tutorial(String title, String description, boolean published) {

        this.title = title;
        this.description = description;
        this.published = published;
    }

    @Column(name = "description")
    private String description;


    @Column(name = "published")
    private boolean published;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tutorial_themes",
            joinColumns = {@JoinColumn(name = "tutorial_id")},
            inverseJoinColumns = {@JoinColumn(name = "theme_id")})
    private Set<Theme> themes = new HashSet<>();

    public Tutorial() {
    }


    public Set<Theme> getThemes() {
        return themes;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }


    public void addTheme(Theme theme) {
        this.themes.add(theme);
        theme.getTutorials().add(this);
    }

    public void removeTheme(long themeId) {
        Theme tag = this.themes.stream().filter(t -> t.getId() == themeId).findFirst().orElse(null);
        if (tag != null) {
            this.themes.remove(tag);
            tag.getTutorials().remove(this);
        }
    }


}

