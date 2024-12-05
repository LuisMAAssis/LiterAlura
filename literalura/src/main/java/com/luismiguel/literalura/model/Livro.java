package com.luismiguel.literalura.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "livros")
@Data
public class Livro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @OneToMany(mappedBy = "livro")
  private List<Autor> authors;
  private List<Idioma> languages;
  private Double download_count;

  public Livro() {
  }

  public Livro(String title, List<Autor> authors, List<Idioma> languages, Double download_count) {
    this.title = title;
    this.authors = authors;
    this.languages = languages;
    this.download_count = download_count;
  }
}