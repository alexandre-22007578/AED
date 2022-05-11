package pt.ulusofona.aed.deisiRockstar2021;

public class OcorrenciasDeUmaString {
    String nome;
    String musica;
    int numeroDeTemas;
    int ano;

    public OcorrenciasDeUmaString(String nome, int numeroDeTemas) {
        this.nome = nome;
        this.numeroDeTemas = numeroDeTemas;
    }

    public OcorrenciasDeUmaString(String nome, String musica, int numeroDeTemas, int ano) {
        this.nome = nome;
        this.musica = musica;
        this.numeroDeTemas = numeroDeTemas;
        this.ano = ano;
    }
}
