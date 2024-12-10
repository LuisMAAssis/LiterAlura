package com.luismiguel.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private int birth_year;
  private int death_year;

  @OneToMany(mappedBy = "authors", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Livro> livros = new ArrayList<>();

  public Autor() {
  }

  public Autor(AutorDTO dtoAutor) {
    this.name = dtoAutor.name();
    this.birth_year = dtoAutor.birth_year();
    this.death_year = dtoAutor.death_year();
  }
}
