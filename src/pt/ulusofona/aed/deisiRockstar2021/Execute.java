package pt.ulusofona.aed.deisiRockstar2021;

import java.util.ArrayList;
import java.util.Comparator;

public class Execute {

    public static String countSongs(int year) {
        int count = 0;

        for (Song musica : Main.musicas) {
            if (musica.temaMusical.anoLancamento == year) {
                count++;
            }
        }
        return "" + count;
    }

    public static String countSongsDup(int year) {

        ArrayList<Song> musicaAno = new ArrayList<>();
        ArrayList<String> musica = new ArrayList<>();
        int ano;

        int duplicado = 0;
        for (int i = 0; i < Main.musicas.size(); i++) {
            ano = Main.musicas.get(i).temaMusical.anoLancamento;
            if (ano == year) {
                musicaAno.add(Main.musicas.get(i));
            }

        }


        for (Song song : musicaAno) {
            if (musica.contains(song.temaMusical.titulo)) {

                duplicado++;
            } else {
                musica.add(song.temaMusical.titulo);
            }
        }


        return "" + duplicado;
    }

    public static String getDanceable(int anoMin, int anoMax, int result) {


        Main.musicas.sort(Comparator.comparingDouble((Song song) -> song.temaMusical.grauDancabilidade).reversed());

        StringBuilder text = new StringBuilder();
        int musicasprintln = 0;
        int idDasMusicas = 0;
        int ano;

        while (musicasprintln < result && idDasMusicas != Main.musicas.size()) {
            ano = Main.musicas.get(idDasMusicas).temaMusical.anoLancamento;
            if (ano >= anoMin && ano <= anoMax) {
                text.append(Main.musicas.get(idDasMusicas).temaMusical.titulo).append(" : ").append(ano).append(" : ").append(Main.musicas.get(idDasMusicas).temaMusical.grauDancabilidade).append("\n");
                musicasprintln++;
            }
            idDasMusicas++;

        }


        /* resultado String separadas por \n pela sintaxe Nome tema +":"+ ano + ":" + dancabilidade */
        return text.toString();
    }

    public static String getArtistOneSong(int inicio, int fim) {

        StringBuilder texto = new StringBuilder();
        boolean existeNomeArtista;
        int ano;
        ArrayList<OcorrenciasDeUmaString> getArtistOneSong = new ArrayList<>();

        if (inicio >= fim) {
            return "Invalid period";
        }


        for (int i = 0; i < Main.musicas.size(); i++) {
            ano = Main.musicas.get(i).temaMusical.anoLancamento;
            if (ano >= inicio && ano <= fim) {
                for (int j = 0; Main.musicas.get(i).temaMusical.artistas != null && j < Main.musicas.get(i).temaMusical.artistas.length; j++) {
                    if (getArtistOneSong.size() == 0) {
                        getArtistOneSong.add(new OcorrenciasDeUmaString(Main.musicas.get(i).temaMusical.artistas[j].nome, Main.musicas.get(i).temaMusical.titulo, 1, Main.musicas.get(i).temaMusical.anoLancamento));
                    }
                    existeNomeArtista = Mainfuctions.existeOcorrencia(i, j, getArtistOneSong);
                    if (!existeNomeArtista) {
                        getArtistOneSong.add(new OcorrenciasDeUmaString(Main.musicas.get(i).temaMusical.artistas[j].nome, Main.musicas.get(i).temaMusical.titulo, 1, Main.musicas.get(i).temaMusical.anoLancamento));

                    }

                }
            }
        }

        getArtistOneSong.sort(Comparator.comparing((OcorrenciasDeUmaString nomes) -> nomes.nome));
        int count = 0;
        for (OcorrenciasDeUmaString ocorrenciasDeUmaString : getArtistOneSong) {
            if (ocorrenciasDeUmaString.numeroDeTemas == 1) {
                texto.append(ocorrenciasDeUmaString.nome).append(" | ").append(ocorrenciasDeUmaString.musica).append(" | ").append(ocorrenciasDeUmaString.ano).append("\n");
                count++;
            }
        }

        System.out.println(count);




        /*ArrayList<OcorrenciasDeUmaString> getArtistOneSong=new ArrayList<>();
        int ano;
        boolean entrou;

        if (inicio >= fim) {
            return "Invalid period";
        }

        for (int i=0;i<Main.musicas.size();i++){
            ano=Main.musicas.get(i).temaMusical.anoLancamento;
            if (ano>=inicio && ano<=fim) {
                for (int j = 0;Main.musicas.get(i).temaMusical.artistas!=null&& j < Main.musicas.get(i).temaMusical.artistas.length; j++) {
                    if (getArtistOneSong.size() == 0) {
                        getArtistOneSong.add(new OcorrenciasDeUmaString(Main.musicas.get(i).temaMusical.artistas[j].nome,Main.musicas.get(i).temaMusical.titulo, 1,Main.musicas.get(i).temaMusical.anoLancamento));
                    }
                    entrou= Mainfuctions.existeOcorrencia(i,j,getArtistOneSong);
                    if (!entrou){
                        getArtistOneSong.add(new OcorrenciasDeUmaString(Main.musicas.get(i).temaMusical.artistas[j].nome,Main.musicas.get(i).temaMusical.titulo, 1,Main.musicas.get(i).temaMusical.anoLancamento));

                    }
                }
            }
        }
        Mainfuctions.removeNotUniqueSongs(getArtistOneSong);
        getArtistOneSong.sort(Comparator.comparing((OcorrenciasDeUmaString nomes) -> nomes.nome));
        for (OcorrenciasDeUmaString ocorrenciasDeUmaString : getArtistOneSong) {
            texto.append(ocorrenciasDeUmaString.nome).append(" | ").append(ocorrenciasDeUmaString.musica).append(" | ").append(ocorrenciasDeUmaString.ano).append("\n");
        }
*/

        return texto.toString();
    }

    public static String getArtists(int result, int temaMin, int temaMax) {
        StringBuilder texto = new StringBuilder();
        ArrayList<OcorrenciasDeUmaString> getArtist = new ArrayList<>();
        Mainfuctions.vezesQueUmArtistaAparece(getArtist);
        getArtist.sort(Comparator.comparingInt((OcorrenciasDeUmaString temas) -> temas.numeroDeTemas).reversed());
        int countMusicas=0;
        for(OcorrenciasDeUmaString ocorrencia :getArtist ){
            if (countMusicas==result){
                break;
            }
            if(ocorrencia.numeroDeTemas>=temaMin && ocorrencia.numeroDeTemas<=temaMax){
                texto.append(ocorrencia.nome).append(" ").append(ocorrencia.numeroDeTemas).append("\n");
                countMusicas++;
            }
        }
        if(texto.equals("")){
            texto.append("No results");
        }
        return texto.toString();
    }

    public static String getArtistForTag(String tag) {

        tag = tag.toUpperCase();
        StringBuilder texto = new StringBuilder();
        boolean first = true;

        for (Song musica : Main.musicas) {
            for (int f = 0; musica.temaMusical.artistas != null && f < musica.temaMusical.artistas.length; f++) {
                if (musica.temaMusical.artistas[f].tag.contains(tag)) {
                    if (first) {
                        texto.append(musica.temaMusical.artistas[f].nome);
                        first = false;
                    } else {
                        texto.append(";").append(musica.temaMusical.artistas[f].nome);
                    }
                }
            }
        }
        if (texto.length() == 0) {
            return "No results";
        }

        return texto.toString();
    }

    public static String mostPopularSONG(int anoMusica, int duracaoMin) {

        int ano, duracao;
        String texto = "";
        ArrayList<SpecialQuerry> songs = new ArrayList<>();
        Main.musicas.sort(Comparator.comparingDouble((Song song) -> song.temaMusical.duracaoTema).reversed());

        for (int i = 0; i < Main.musicas.size() && Main.musicas.get(i).temaMusical.artistas != null; i++) {
            ano = Main.musicas.get(i).temaMusical.anoLancamento;
            duracao = Main.musicas.get(i).temaMusical.duracaoTema;
            if (ano == anoMusica && duracao >= duracaoMin) {
                songs.add(new SpecialQuerry(Main.musicas.get(i).temaMusical.titulo, Main.musicas.get(i).temaMusical.artistas, Main.musicas.get(i).temaMusical.duracaoTema, Main.musicas.get(i).temaMusical.artistas.length));
            }
        }


        songs.sort(Comparator.comparingInt((SpecialQuerry artistas) -> artistas.numeroArtistas));


        if (songs.size() == 0) {
            texto = "No results";
        }

        return texto;
    }

}
