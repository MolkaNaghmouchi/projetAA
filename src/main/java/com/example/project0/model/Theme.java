package com.example.project0.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "themes")

public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "themes")
    @JsonIgnore
    private Set<Tutorial> tutorials = new HashSet<>();

    public Theme() {
    }


    public Theme(Long id, String nom, Set<Tutorial> tutorials) {
        this.id = id;
        this.nom = nom;
        this.tutorials = tutorials;
    }

    //getTutorials
    public Set<Tutorial> getTutorials() {

        return tutorials;
    }

    //setTutorials
    public void setTutorials(Set<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }
}
