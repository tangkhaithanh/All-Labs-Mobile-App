package org.example.apituan7.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false,columnDefinition = "nvarchar(255)")
    private String name;

    @Column(name = "image_url", nullable = false,columnDefinition = "nvarchar(255)")
    private String image;

    @Column(name = "description", nullable = false,columnDefinition = "nvarchar(255)")
    private String description;
}
