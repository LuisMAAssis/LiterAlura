package com.luismiguel.literalura.principal;

import com.luismiguel.literalura.model.Livro;
import com.luismiguel.literalura.model.Resultado;
import com.luismiguel.literalura.repository.LivroRepository;
import com.luismiguel.literalura.service.ConsumoApi;
import com.luismiguel.literalura.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
  private Scanner leitor = new Scanner(System.in);
  private ConsumoApi consumo = new ConsumoApi();
  private ConverteDados converte = new ConverteDados();
  private List<Livro> livros = new ArrayList<>();
  private LivroRepository repositorio;
  private final String ENDERECO = "http://gutendex.com/books?search=";

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
          System.out.println("Digite o título do livro: ");
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
  private Resultado getLivroDaApi(String titulo) {
    var url = ENDERECO + titulo.replace(" ", "%20");
    var json = consumo.obterDados(url);
    return converte.obterDados(json, Resultado.class);
  }
  private Optional<Livro> getLivroDados(Resultado resultado, String titulo) {
    return resultado.getResultados().stream()
        .filter(l -> l.getTitle().toLowerCase().contains(titulo.toLowerCase()))
        .map(l -> new Livro(
            l.getTitle(),
            l.getAuthors(),
            l.getLanguages(),
            l.getDownload_count()
        ) )
        .findFirst();
  }
  private void buscarLivroPorTitulo(String titulo) {
    Resultado resultadoLivro = getLivroDaApi(titulo);
    Optional<Livro> livro = getLivroDados(resultadoLivro, titulo);

    if (livro.isPresent()) {
      var livroSalvo = repositorio.save(livro.get());
      System.out.println(livroSalvo);
    } else {
      System.out.println("Livro não encontrado!");
    }

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
    int ano = leitor.nextInt();
    leitor.nextLine();

    livros = repositorio.findAll();
    livros.stream()
        .map(Livro::getAuthors)
        .filter(author -> author.contains("(" + ano + ")"))
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