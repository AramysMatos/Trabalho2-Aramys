package eel418_2016_1.DAO;

import eel418_2016_1.DTOs.RespostaCompletaDTO;
import eel418_2016_1.DTOs.RespostaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.json.JsonObject;
import utils.Utils;

public class BiblioPDFDAO extends BaseDAO {

//------------------------------------------------------------------------------    
    public RespostaCompletaDTO buscarListaPorPalavraDoTitulo(JsonObject dados) {
        RespostaCompletaDTO listaRefsDTO = new RespostaCompletaDTO();
        RespostaDTO umaRefDTO = null;
        String modopatrimonio2 = "";

        String[] palavrasDaBuscaTitulo = null;
        String[] palavrasDaBuscaVeiculo = null;
        String[] palavrasDaBuscaAutoria = null;
        String modoautoria2 = "";
        String modotitulo2 = "";
        String modoveiculo2 = "";
        int limiteTituloSuperior = 0;
        int limiteAutoriaSuperior = 0;
        int limiteVeiculoSuperior = 0;
        boolean controle = true;

        JsonObject modotitulo = dados.getJsonObject("titulo");
        if (!(modotitulo == null)) {
            modotitulo2 = modotitulo.getString("modo");
            palavrasDaBuscaTitulo = extrairPalavrasDaBusca(dados, "titulo");
            limiteTituloSuperior = palavrasDaBuscaTitulo.length;
            controle = false;
        }

        JsonObject modoautoria = dados.getJsonObject("autoria");
        System.out.println(modoautoria);
        if (!(modoautoria == null)) {
            modoautoria2 = modoautoria.getString("modo");
            palavrasDaBuscaAutoria = extrairPalavrasDaBusca(dados, "autoria");
            limiteAutoriaSuperior = palavrasDaBuscaAutoria.length;
            controle = false;
        }

        JsonObject modoveiculo = dados.getJsonObject("veiculo");
        if (!(modoveiculo == null)) {
            modoveiculo2 = modoveiculo.getString("modo");
            palavrasDaBuscaVeiculo = extrairPalavrasDaBusca(dados, "veiculo");
            limiteVeiculoSuperior = palavrasDaBuscaVeiculo.length;
            controle = false;
        }
        if (controle == false) {
            //  String[] palavrasDaBuscaPalchave = extrairPalavrasDaBusca(dados, "palchave");
            String inicioSelectExterno
                    = "SELECT T1.patrimonio, T1.titulo, T1.autoria, T1.veiculo, (count(*)) AS nrohits \n"
                    + "FROM dadoscatalogo T1 \n";

            String finalSelectExterno
                    = "GROUP BY T1.patrimonio, T1.titulo, T1.autoria ORDER BY nrohits DESC, titulo ASC;";

            String inner = "";
            String sqlveiculo = "";
            String sqlautoria = "";
            String sqltitulo = "";
            String marcador1 = "";
            String marcador2 = "";
            String marcador3 = "";

            if (!modotitulo2.equals("")) {

                sqltitulo = "( " + prepararComandoSQL(palavrasDaBuscaTitulo, "titulo") + ")";
                inner += "INNER JOIN palavrastitulonormal T2 ON(T1.patrimonio = T2.patrimonio) \n";
                if ((!modoautoria2.equals("")) || (!modoveiculo2.equals(""))) {
                    if (modotitulo2.equals("E")) {
                        marcador1 = " and (";
                        marcador3 = ")";
                    } else {
                        marcador1 = " or (";
                        marcador3 = ")";
                    }
                }

            }

            if (!modoautoria2.equals("")) {

                sqlautoria = " (" + prepararComandoSQL(palavrasDaBuscaAutoria, "autoria") + ") ";
                inner += "INNER JOIN palavrasautorianormal T3 ON(T1.patrimonio = T3.patrimonio) \n";
                if (!modoveiculo2.equals("")) {
                    if (modoautoria2.equals("E")) {
                        marcador2 = " and (";
                        marcador3 += ")";
                    } else if (modoautoria2.equals("E")) {
                        marcador2 = " and (";
                        marcador3 += ")";
                    } else {
                        marcador2 = " or (";
                        marcador3 += ")";
                    }
                }
            }

            if (!modoveiculo2.equals("")) {

                sqlveiculo = " (" + prepararComandoSQL(palavrasDaBuscaVeiculo, "veiculo") + ")";
                inner += "INNER JOIN palavrasveiculonormal T4 ON(T1.patrimonio = T4.patrimonio) \n";
            }

            String preparedStatement = inicioSelectExterno + inner + "WHERE \n" + sqltitulo + marcador1 + sqlautoria + marcador2 + sqlveiculo + marcador3 + finalSelectExterno;
            System.out.println(preparedStatement);

            try (Connection conexao = getConnection()) {
                PreparedStatement comandoSQL = conexao.prepareStatement(preparedStatement);

                for (int i = 0; i < limiteTituloSuperior; i++) {
                    comandoSQL.setString(i + 1, palavrasDaBuscaTitulo[i]);
                }
                System.out.println("resposta do teste: " + limiteAutoriaSuperior);
                for (int i = limiteTituloSuperior; i < limiteTituloSuperior + limiteAutoriaSuperior; i++) {
                    comandoSQL.setString(i + 1, palavrasDaBuscaAutoria[i - limiteTituloSuperior]);
                }
                for (int i = limiteAutoriaSuperior + limiteTituloSuperior; i < limiteAutoriaSuperior + limiteTituloSuperior + limiteVeiculoSuperior; i++) {
                    comandoSQL.setString(i + 1, palavrasDaBuscaVeiculo[i - (limiteAutoriaSuperior + limiteTituloSuperior)]);
                }

                ResultSet rs = comandoSQL.executeQuery();
                while (rs.next()) {
                    umaRefDTO = new RespostaDTO();
                    umaRefDTO.setPatrimonio(Long.toString(rs.getLong("patrimonio")));
                    umaRefDTO.setTitulo(rs.getString("titulo"));
                    umaRefDTO.setAutoria(rs.getString("autoria"));
                    umaRefDTO.setAutoria(rs.getString("veiculo"));
                    listaRefsDTO.addResposta(umaRefDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try (Connection conexao = getConnection()) {
                PreparedStatement comandoSQL = conexao.prepareStatement(
                        "SELECT * FROM dadoscatalogo  WHERE patrimonio=?;"
                );
                comandoSQL.setInt(1, Integer.parseInt(dados.getString("patrimonio")));
                ResultSet rs = comandoSQL.executeQuery();

                while (rs.next()) {
                    umaRefDTO = new RespostaDTO();
                    umaRefDTO.setPatrimonio(Long.toString(rs.getLong("patrimonio")));
                    umaRefDTO.setTitulo(rs.getString("titulo"));
                    umaRefDTO.setAutoria(rs.getString("autoria"));
                    umaRefDTO.setAutoria(rs.getString("veiculo"));
                    listaRefsDTO.addResposta(umaRefDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return listaRefsDTO;
    }
//------------------------------------------------------------------------------    

    private String[] extrairPalavrasDaBusca(JsonObject dados, String tipo) {
        JsonObject dados2 = dados.getJsonObject(tipo);
        String busca = dados2.getString("texto");
        busca = Utils.removeDiacriticals(busca);
        String[] temp = busca.split(" ");
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].trim();
        }
        return temp;
    }
//------------------------------------------------------------------------------   

    private String[] extrairPalavrasDaBusca2(JsonObject dados, String tipo) {
        String busca = dados.getString(tipo);
        busca = Utils.removeDiacriticals(busca);
        String[] temp = busca.split(" ");
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].trim();
        }
        return temp;
    }
//------------------------------------------------------------------------------    

    private String prepararComandoSQL(String[] palavrasDaBusca, String tipo) {
        /*
Exemplo:
        
SELECT T1.patrimonio, T1.titulo, T1.autoria, (count(*)) AS nrohits
FROM dadoscatalogo T1
INNER JOIN palavrastitulonormal T2 ON(T1.patrimonio = T2.patrimonio) WHERE

T2.palavra_titulo_normal LIKE 'MAVEN'        
OR        
T2.palavra_titulo_normal LIKE 'STYLES'
        
GROUP BY T1.patrimonio, T1.titulo, T1.autoria ORDER BY nrohits DESC, titulo ASC;        
         */

        String baseComando = "";

        if (tipo.equals("titulo")) {
            baseComando = "T2.palavra_titulo_normal LIKE ? \n";
        }

        if (tipo.equals("autoria")) {
            baseComando = "T3.palavra_autoria_normal LIKE ? \n";
        }

        if (tipo.equals("veiculo")) {
            baseComando = "T4.palavra_veiculo_normal LIKE ? \n";
        }

        String comando = "";
        for (int i = 0; i < palavrasDaBusca.length; i++) {
            comando = comando + baseComando;
            if (i < (palavrasDaBusca.length - 1)) {
                comando = comando + "OR \n";
            }
        }

        return comando;
    }
//------------------------------------------------------------------------------

    public String salvarNovo(JsonObject dados) {
        ResultSet rst = null;
        long patrimonio = 0L;
        String titulo = dados.getString("titulo").trim();
        titulo = titulo.replaceAll("\\s+", " ");

        try (Connection conexao = getConnection()) {
            // BEGIN TRANSACTION
            conexao.setAutoCommit(false);
            // PRIMEIRA TABELA
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "INSERT INTO dadoscatalogo (titulo,autoria,veiculo) VALUES(?,?,?) "
                    + "RETURNING patrimonio;");

            comandoSQL.setString(1, titulo);
            comandoSQL.setString(2, dados.getString("autoria"));
            comandoSQL.setString(3, dados.getString("veiculo"));
            rst = comandoSQL.executeQuery();
            rst.next();
            patrimonio = rst.getLong("patrimonio");
            // SEGUNDA TABELA
            String[] palavrasDaBusca = extrairPalavrasDaBusca2(dados, "titulo");
            for (String cadaPalavra : palavrasDaBusca) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrastitulonormal (palavra_titulo_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }
            // TERCEIRA TABELA
            String[] palavrasDaBusca2 = extrairPalavrasDaBusca2(dados, "autoria");
            for (String cadaPalavra : palavrasDaBusca2) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrasautorianormal (palavra_autoria_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }
            // QUARTA TABELA
            String[] palavrasDaBusca3 = extrairPalavrasDaBusca2(dados, "veiculo");
            for (String cadaPalavra : palavrasDaBusca3) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrasveiculonormal (palavra_veiculo_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }
            // COMMIT TRANSACTION
            conexao.commit();
            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{\"patrimonio\":\"" + "Registro: " + Long.toString(patrimonio) + " Salvo com sucesso!\"}";
    }

//------------------------------------------------------------------------------
    public String deletar(JsonObject dados) {
        ResultSet rst = null;
        long patrimonio = 0L;
        String strchave = "";
        int intchave = 0;

        try (Connection conexao = getConnection()) {
            // BEGIN TRANSACTION
            // conexao.setAutoCommit(false);
            // PRIMEIRA TABELA 
            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "DELETE FROM dadoscatalogo WHERE patrimonio=?;"
            );

            comandoSQL.setInt(1, Integer.parseInt(dados.getString("patrimonio")));

            rst = comandoSQL.executeQuery();
            rst.next();
            patrimonio = rst.getLong("patrimonio");

            // SEGUNDA TABELA                  
            comandoSQL = conexao.prepareStatement(
                    "DELETE FROM palavrastitulonormal WHERE patrimonio=?;");
            comandoSQL.setInt(1, Integer.parseInt(dados.getString("patrimonio")));
            rst = comandoSQL.executeQuery();
            // TERCEIRA TABELA                  
            comandoSQL = conexao.prepareStatement(
                    "DELETE FROM palavrasAUTORIAnormal WHERE patrimonio=?;");
            comandoSQL.setInt(1, Integer.parseInt(dados.getString("patrimonio")));
            rst = comandoSQL.executeQuery();
            // QUARTA TABELA                  
            comandoSQL = conexao.prepareStatement(
                    "DELETE FROM palavrasVEICULOnormal WHERE patrimonio=?;");
            comandoSQL.setInt(1, Integer.parseInt(dados.getString("patrimonio")));
            rst = comandoSQL.executeQuery();
            // COMMIT TRANSACTION
            conexao.commit();
            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{\"patrimonio\":\"" + "Registro" + Long.toString(patrimonio) + " Apagado com sucesso! \"}";
    }

//------------------------------------------------------------------------------
    public String atualizar(JsonObject dados) {
        ResultSet rst = null;
        long patrimonio = 0L;
        String titulo = dados.getString("titulo").trim();
        titulo = titulo.replaceAll("\\s+", " ");

        try (Connection conexao = getConnection()) {
            // BEGIN TRANSACTION
            conexao.setAutoCommit(false);
            // PRIMEIRA TABELA

            PreparedStatement comandoSQL = conexao.prepareStatement(
                    "UPDATE dadoscatalogo SET titulo=?, autoria=?, veiculo=? WHERE patrimonio=?;"
            );

            comandoSQL.setInt(4, Integer.parseInt(dados.getString("patrimonio")));
            comandoSQL.setString(1, titulo);
            comandoSQL.setString(2, dados.getString("autoria"));
            comandoSQL.setString(3, dados.getString("veiculo"));
            rst = comandoSQL.executeQuery();
            rst.next();
            patrimonio = rst.getLong("patrimonio");
            // SEGUNDA TABELA
            String[] palavrasDaBusca = extrairPalavrasDaBusca2(dados, "titulo");
            comandoSQL = conexao.prepareStatement(
                    "DELETE FROM palavrastitulonormal WHERE patrimonio=?;");
            comandoSQL.setInt(1, Integer.parseInt(dados.getString("patrimonio")));
            rst = comandoSQL.executeQuery();
            for (String cadaPalavra : palavrasDaBusca) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrastitulonormal (palavra_titulo_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }
            // TERCEIRA TABELA
            String[] palavrasDaBusca2 = extrairPalavrasDaBusca2(dados, "autoria");
            comandoSQL = conexao.prepareStatement(
                    "DELETE FROM palavrasAUTORIAnormal WHERE patrimonio=?;");
            comandoSQL.setInt(1, Integer.parseInt(dados.getString("patrimonio")));
            rst = comandoSQL.executeQuery();
            for (String cadaPalavra : palavrasDaBusca2) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrasautorianormal (palavra_autoria_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }
            // QUARTA TABELA
            String[] palavrasDaBusca3 = extrairPalavrasDaBusca2(dados, "veiculo");
            comandoSQL = conexao.prepareStatement(
                    "DELETE FROM palavrasVEICULOnormal WHERE patrimonio=?;");
            comandoSQL.setInt(1, Integer.parseInt(dados.getString("patrimonio")));
            rst = comandoSQL.executeQuery();
            for (String cadaPalavra : palavrasDaBusca3) {
                comandoSQL = conexao.prepareStatement(
                        "INSERT INTO palavrasveiculonormal (palavra_veiculo_normal,patrimonio) "
                        + "VALUES(?,?);");
                comandoSQL.setString(1, cadaPalavra);
                comandoSQL.setLong(2, patrimonio);
                comandoSQL.executeUpdate();
            }
            // COMMIT TRANSACTION
            conexao.commit();
            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{\"patrimonio\":\"" + Long.toString(patrimonio) + "\"}";
    }

//------------------------------------------------------------------------------
}
