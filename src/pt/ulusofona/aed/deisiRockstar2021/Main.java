package pt.ulusofona.aed.deisiRockstar2021;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {



    //porque quando criamos uma class execute e pomos la as funções deixamos de passar nos teste de leitura. no entanto da quando esta no mainfunctions

    static String caminho = "";

    static int[][] estatisticasLinhas = new int[3][2];

    static ArrayList<Song> musicas = new ArrayList<>();

    static ArrayList<Song> artistasSemMusica = new ArrayList<>();

    static ArrayList<Song> detailsSemMusica = new ArrayList<>();

    static LinkedHashMap<String, Song> hashmusicas = new LinkedHashMap<>();


    public static void main(String[] args) {


        String textoIntroduzido;
        long start = System.currentTimeMillis();
        try {
            loadFiles();
        } catch (Exception c) {
            c.printStackTrace();
        }
        long end = System.currentTimeMillis();

        System.out.println(end - start + " ms");
        System.out.println(getParseInfo(caminho+"songs.txt"));
        System.out.println(getParseInfo(caminho+"song_artists.txt"));
        System.out.println(getParseInfo(caminho+"song_details.txt"));
        System.out.println("Welcome to DEISI Rockstar!\n");


        Scanner input = new Scanner(System.in);

        textoIntroduzido = input.nextLine();

        while (textoIntroduzido != null && !textoIntroduzido.equals("KTHXBYE")) {


            start = System.currentTimeMillis();

            String resultado = execute(textoIntroduzido);

            end = System.currentTimeMillis();

            System.out.println(resultado);
            System.out.println("(took " + (end - start) + " ms)\n");

            textoIntroduzido = input.nextLine();

        }

    }

    public static void loadFiles() throws IOException {
        //limpeza do array das estatisticas
        for (int[] estatisticasLinha : estatisticasLinhas) {
            Arrays.fill(estatisticasLinha, 0);
        }


        //limpeza do array das musicas
        musicas.clear();
        artistasSemMusica.clear();
        detailsSemMusica.clear();
        hashmusicas.clear();

        long inicio = System.currentTimeMillis();
        String nomeFicheiro = caminho + "songsGrandes.txt";
        try {
            FileReader ficheiro = new FileReader(nomeFicheiro);
            BufferedReader reader = new BufferedReader(ficheiro);
            LerFicheiros.lersong(reader);
            reader.close();
            long fim = System.currentTimeMillis();
            System.out.println("songs demorou " + (fim - inicio) + " ms");


            nomeFicheiro = caminho + "song_artistsGrandes.txt";
            inicio = System.currentTimeMillis();
            ficheiro = new FileReader(nomeFicheiro);
            reader = new BufferedReader(ficheiro);
            LerFicheiros.lerArtistas(reader);
            reader.close();
            fim = System.currentTimeMillis();
            System.out.println("artistas demorou " + (fim - inicio) + " ms");


            nomeFicheiro = caminho + "song_detailsGrandes.txt";
            inicio = System.currentTimeMillis();
            ficheiro = new FileReader(nomeFicheiro);
            reader = new BufferedReader(ficheiro);
            LerFicheiros.lerDetails(reader);
            reader.close();
            fim = System.currentTimeMillis();
            System.out.println("details demorou " + (fim - inicio) + " ms");

            musicas.addAll(hashmusicas.values());
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro durante a leitura.");
        }

    }

    public static ArrayList<Song> getSongs() {
        return musicas;
    } //complexa????????

    public static ParseInfo getParseInfo(String fileName) {
        ParseInfo estatisticas = new ParseInfo();

        if ((fileName == null || fileName.length() == 0) || (!fileName.equals(caminho+"songs.txt") && !fileName.equals(caminho+"song_artists.txt") && !fileName.equals(caminho+"song_details.txt"))) {
            return null;
        }
        switch (fileName) {
            case "songs.txt":
                estatisticas.numLinhasOk = estatisticasLinhas[0][1];
                estatisticas.numLinhasIgnoradas = estatisticasLinhas[0][0];
                break;
            case "song_details.txt":
                estatisticas.numLinhasOk = estatisticasLinhas[1][1];
                estatisticas.numLinhasIgnoradas = estatisticasLinhas[1][0];
                break;
            default:
                estatisticas.numLinhasOk = estatisticasLinhas[2][1];
                estatisticas.numLinhasIgnoradas = estatisticasLinhas[2][0];
                break;
        }
        return estatisticas;
    }

    public static String execute(String command) {

        String[] comando = command.split(" ");

        switch (comando[0]) {
            case "COUNT_SONGS_YEAR":
                return Execute.countSongs(Integer.parseInt(comando[1]));/*ler numero e dizer  total de temas musicais que foram lançados no ano X.*/

            case "COUNT_DUPLICATE_SONGS_YEAR":
                return Execute.countSongsDup(Integer.parseInt(comando[1]));/* ler numero e dizer  total de temas cujo título está repetido, que foram lançados no ano X*/

            case "GET_ARTISTS_FOR_TAG":
                return Execute.getArtistForTag(comando[1]);/* a lista de artistas associadas a uma certa tag ordenados alfabeticamente.*/

            case "GET_MOST_DANCEABLE":
                return Execute.getDanceable(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]), Integer.parseInt(comando[3]));/* String contendo várias linhas separadas por \n e respeitando a seguinte sintaxe:“<Nome tema> : <Ano> : <Dançabilidade>”*/

            case "GET_ARTISTS_ONE_SONG":
                return Execute.getArtistOneSong(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));

            case "GET_TOP_ARTISTS_WITH_SONGS_BETWEEN":
                return Execute.getArtists(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]), Integer.parseInt(comando[3]));

            case "SONG_MADE_YEAR_WITH_MORE_THAN_X_DURACTION":
                return Execute.mostPopularSONG(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));
            default:
                return execute2(command);// complexa ??
        }
    }

    public static String execute2(String command) {

        String[] comando = command.split(" ");

        switch (comando[0]) {
            case "MOST_FREQUENT_WORDS_IN_ARTIST_NAME":
                return Mainfuctions.getCaracter(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]), Integer.parseInt(comando[3]));

            case "GET_UNIQUE_TAGS":
                return Mainfuctions.getUniqueTag();

            case "GET_UNIQUE_TAGS_IN_BETWEEN_YEARS":
                return Mainfuctions.getUniqueTagYear(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]));

            case "ADD_TAGS":
                String[] parametros = Mainfuctions.splitArtistasTags(command);
                return Mainfuctions.addTag(parametros[1], parametros[0]);

            case "REMOVE_TAGS":
                String[] parametros2 = Mainfuctions.splitArtistasTags(command);
                return Mainfuctions.removeTag(parametros2[1], parametros2[0]);

            case "CLEANUP":
                return Mainfuctions.cleanUp();

            case "GET_RISING_STARS":
                return Mainfuctions.getRisingStars(Integer.parseInt(comando[1]), Integer.parseInt(comando[2]), comando[3]);

            default:
                return "Illegal command. Try again";
        }
    }

    public static int getTypeOfSecondParameter() {
        return 1;
    }

    public static String getCreativeQuery() {
        return "SONG_MADE_YEAR_WITH_MORE_THAN_X_DURACTION";
    }

    public static String getVideoUrl() {
        return null;
    }

}