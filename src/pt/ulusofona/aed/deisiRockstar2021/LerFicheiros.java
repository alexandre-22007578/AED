package pt.ulusofona.aed.deisiRockstar2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;

public class LerFicheiros {
    // mudei alguns ifs porque tinhamos if(-...){bla bla} if(....){ bla bla} por isso juntei os
    public static void lersong(BufferedReader reader) throws IOException {

        HashSet<String> idsongs = new HashSet<>();
        String linha;
        while ((linha = reader.readLine()) != null) {

            //partir a linha no caractere separador
            String[] dados = linha.split("@");

            if (dados.length != 3) {
                Main.estatisticasLinhas[0][0]++;
                continue;
            }

            String id = dados[0].trim();
            String nome = dados[1].trim();

            int anoLancamento;
            try {
                anoLancamento = Integer.parseInt(dados[2].trim());
            } catch (NumberFormatException e) {
                Main.estatisticasLinhas[0][0]++;
                continue;
            }

            if (!idsongs.add(id) || (Integer.parseInt(dados[2].trim()) < 0 && Integer.parseInt(dados[2].trim()) > 2021)
                    || id.equals("") || nome.equals("")) {
                Main.estatisticasLinhas[0][0]++;
                continue;
            }
            int count = 0;
            while (nome.charAt(count) == '\"') {
                count++;
            }
            if (count != 0) {
                String[] nomesMusica = dados[1].split("\"");
                if (count == 3 || count == 1) {

                    nome = "\"" + nomesMusica[count] + "\"";
                } else {
                    nome = nomesMusica[count];
                }
            }
            Main.hashmusicas.put(id, new Song(new TemaMusical(id, nome, anoLancamento)));
            //musicas.add(new Song(new TemaMusical(id,nome,anoLancamento)));
            Main.estatisticasLinhas[0][1]++;
        }

    }

    public static void lerArtistas(BufferedReader reader) throws IOException {
        String linha;
        boolean ingnorada;
        while ((linha = reader.readLine()) != null) {
            ingnorada = false;
            //partir a linha no caractere separador
            String[] dados = linha.split("@");
            if (dados.length != 2) {
                Main.estatisticasLinhas[2][0]++;
                continue;
            }

            Artista[] artistasArray;
            String id = dados[0].trim();
            String[] nomes;
            boolean entrou = false;

            if (Main.hashmusicas.get(id) == null) {
                Main.estatisticasLinhas[2][0]++;
                continue;
            }

            //"[""The Kids' Crusade""]"

            dados[1] = dados[1].trim().replace("[", "").replace("]", "").replace("\"", "");
            nomes = dados[1].trim().split(",");
            for (int i = 0; i < nomes.length; i++) {


                if (nomes[0].equals("'")) {
                    nomes[0] = nomes[0].replace("'", "");
                }
            }
            int count = 0;
            while (nomes[count].equals("\"")) {
                count++;
            }
            if (count != 0) {
                String[] nomesMusica = dados[1].split("\"");
                if (count == 3 || count == 1) {

                    nomes[0] = "\"" + nomesMusica[count] + "\"";
                } else {
                    nomes[0] = nomesMusica[count];
                }
            }
            artistasArray = new Artista[nomes.length];
            for (int i = 0; i < nomes.length; i++) {
                nomes[i] = nomes[i].trim().split(",")[0];
                if (nomes[i].equals("\"''") || nomes[i].equals("''")) {
                    entrou = true;
                    break;
                }

                /*if (nomes[i].split("'").length == 1 || nomes[i].split("'")[1].equals("]\"")) {
                    nomes[i] = nomes[i].split("'")[0];
                } else {
                    nomes[i] = nomes[i].split("'")[1];
                }*/
                for (int k = 0; k < nomes.length; k++) {
                    if (nomes[k].charAt(0) == '\'') {
                        nomes[k] = nomes[k].replace("'", "");
                    }
                }

                artistasArray[i] = new Artista(nomes[i]);
            }
            if (entrou) {
                Main.estatisticasLinhas[2][0]++;
                continue;
            }

            for (Artista artista : artistasArray) {
                if (artista.nome.equals("")) {
                    ingnorada = true;
                    break;
                }
            }
            if (ingnorada || id.equals("")) {
                Main.estatisticasLinhas[2][0]++;
                continue;
            }
            if (Main.hashmusicas.get(id) == null) {
                Main.artistasSemMusica.add(new Song(new TemaMusical(id, artistasArray)));
            } else {
                Main.hashmusicas.get(id).temaMusical.artistas = artistasArray;
            }

            Main.estatisticasLinhas[2][1]++;
                /*De seguida seria necessário guardar o objecto Utilizador numa estrutura de dados apropriada
                 (p.e. array, lista, etc).*/
        }
    }

    public static void lerDetails(BufferedReader reader) throws IOException {
        String linha;
        HashSet<String> iddetails = new HashSet<>();
        while ((linha = reader.readLine()) != null) {
            //partir a linha no caractere separador
            String[] dados = linha.split("@");
            if (dados.length !=7) {
                Main.estatisticasLinhas[1][0]++;
                continue;
            }

            String id = dados[0].trim();
            int duracao = 1; // valor default
            int popularidade = 1; // valor default
            double dancabilidade = 1; // valor default
            double vivacidade = 1; // valor default
            double volumeMedio = 1; // valor default
            boolean letraExplicita = false; // valor default
            try {
                duracao = Integer.parseInt(dados[1].trim());
                letraExplicita = Integer.parseInt(dados[2].trim()) == 1;
                popularidade = Integer.parseInt(dados[3].trim());
                dancabilidade = Double.parseDouble(dados[4].trim());
                vivacidade = Double.parseDouble(dados[5].trim());
                volumeMedio = Double.parseDouble(dados[6].trim());

            } catch (NumberFormatException e) {
                Main.estatisticasLinhas[1][0]++;
                continue;
            }
            if (!iddetails.add(id) || Main.hashmusicas.get(id) == null || (Integer.parseInt(dados[2].trim()) != 1
                    && Integer.parseInt(dados[2].trim()) != 0) || (Integer.parseInt(dados[3].trim()) < 0 || id.equals(""))) {
                Main.estatisticasLinhas[1][0]++;
                continue;
            }
            if (Main.hashmusicas.get(id) == null) {
                Main.detailsSemMusica.add(new Song(new TemaMusical(id, duracao, letraExplicita, popularidade, dancabilidade, vivacidade, volumeMedio)));
            } else {
                Main.hashmusicas.get(id).temaMusical.duracaoTema = duracao;
                Main.hashmusicas.get(id).temaMusical.popularidade = popularidade;
                Main.hashmusicas.get(id).temaMusical.volumeMedio = volumeMedio;
                Main.hashmusicas.get(id).temaMusical.grauDancabilidade = dancabilidade;
                Main.hashmusicas.get(id).temaMusical.grauVivacidade = vivacidade;
            }

            Main.estatisticasLinhas[1][1]++;
                /*De seguida seria necessário guardar o objecto Utilizador numa estrutura de dados apropriada
                 (p.e. array, lista, etc).*/
        }
    }
}
