package com.example.project0.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="tutorials")
@AllArgsConstructor
@NoArgsConstructor
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title")
    private  String title;

    public Tutorial( String title, String description, boolean published) {

        this.title = title;
        this.description = description;
        this.published = published;
    }

    @Column(name = "description")
    private String description;


    @Column(name="published")
    private boolean published;


}
