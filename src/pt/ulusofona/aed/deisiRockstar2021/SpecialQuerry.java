package pt.ulusofona.aed.deisiRockstar2021;

public class SpecialQuerry {
    String nomeMusica;
    Artista[] nomeArtista;
    int duracao;
    int numeroArtistas;

    public SpecialQuerry(String nomeMusica, Artista[] nomeArtista, int duracao,int numeroArtistas) {
        this.nomeMusica = nomeMusica;
        this.nomeArtista = nomeArtista;
        this.duracao = duracao;
        this.numeroArtistas=numeroArtistas;
    }
}
