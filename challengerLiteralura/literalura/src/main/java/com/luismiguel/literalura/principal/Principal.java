package com.luismiguel.literalura.principal;

import com.luismiguel.literalura.model.*;
import com.luismiguel.literalura.repository.LivroRepository;
import com.luismiguel.literalura.service.ConsumoApi;
import com.luismiguel.literalura.service.ConverteDados;

import java.util.*;

public class Principal {
  private Scanner leitor = new Scanner(System.in);
  private ConsumoApi consumo = new ConsumoApi();
  private ConverteDados converte = new ConverteDados();
  private final String ENDERECO = "http://gutendex.com/books?search=";
  private List<LivroDTO> dtoLivro = new ArrayList<>();
  private LivroRepository repositorio;
  private List<Livro> livros = new ArrayList<>();

  public Principal(LivroRepository repositorio) {
    this.repositorio = repositorio;
  }

  public void exibeMenu() {
    int opcao = -1;

    while (opcao != 0) {
      var menu = """
        1 - Buscar livro por título
        2 - Listar livros registrados
        3 - Listar autores registrados
        4 - Listar autores vivos em determinado ano
        5 - Listar livros por idioma
        0 - Sair
        """;
      System.out.println(menu);
      opcao = leitor.nextInt();
      leitor.nextLine();

      switch (opcao) {
        case 1:
          buscarLivroPorTitulo(leitor.nextLine());
          break;
        case 2:
          listarLivros();
          break;
        case 3:
          listarAutores();
          break;
        case 4:
          listarAutoresVivos();
          break;
        case 5:
          listarLivrosPorIdioma();
          break;
        case 0:
          System.out.println("Saindo...");
          break;
        default:
          System.out.println("Escolha uma opção válida!!!");
      }
    }
  }
  private Resultado getLivroApi(String title) {
    var json = consumo.obterDados(ENDERECO + title.replace(" ", "+"));
    return converte.obterDados(json, Resultado.class);
  }

  private Optional<Livro> getLivroDados(Resultado resultado, String title) {
    return resultado.resultados().stream()
        .filter(l -> l.getTitle().toLowerCase().contains(title.toLowerCase()))
        .map(l -> new Livro(
            l.getTitle(),
            l.getAuthors(),
            l.getDownload_count(),
            l.getLanguages()
        ))
        .findFirst();
  }

  private Optional<Livro> buscarLivroPorTitulo(String title) {
    Resultado resultadoLivro = getLivroApi(title);
    Optional<Livro> livro = getLivroDados(resultadoLivro, title);

    System.out.println("Digite o nome do livro que deseja encontrar");

    if(livro.isPresent()) {
      var resultado = repositorio.save(livro.get());
      System.out.println(resultado);
    } else {
      System.out.println("Livro não encontrado");
    }

    return livro;
  }

  private void listarLivros() {
    livros = repositorio.findAll();
    livros.forEach(System.out::println);
  }

  private void listarAutores() {
    livros = repositorio.findAll();
    livros.stream()
        .map(Livro::getAuthors)
        .distinct()
        .forEach(System.out::println);
  }

  private void listarAutoresVivos() {
    System.out.println("Digite o ano:");
    int year = leitor.nextInt();
    leitor.nextLine();

    livros = repositorio.findAll();
    livros.stream()
        .filter(l -> l.getAuthors().contains("(" + year + ")"))
        .map(Livro::getAuthors)
        .distinct()
        .forEach(System.out::println);
  }

  private void listarLivrosPorIdioma() {
    livros = repositorio.findAll();
    livros.stream()
        .map(Livro::getLanguages)
        .distinct()
        .forEach(System.out::println);
  }
}