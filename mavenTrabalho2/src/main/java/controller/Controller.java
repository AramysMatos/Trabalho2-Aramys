package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import eel418_2016_1.DTOs.RespostaDTO;
import eel418_2016_1.DAO.BiblioPDFDAO;
import eel418_2016_1.DTOs.RespostaCompletaDTO;

public class Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Não é um conjunto de pares nome-valor,
        // então tem que ler como se fosse um upload de arquivo...
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        request.getInputStream(), "UTF8"));
        String textoDoJson = br.readLine();
        System.out.println("teste");
        JsonObject jsonObjectDeJava = null;
        // Ler e fazer o parsing do String para o "objeto json" java
        try ( //Converte o string em "objeto json" java
                // Criar um JsonReader.
                JsonReader readerDoTextoDoJson
                = Json.createReader(new StringReader(textoDoJson))) {
            // Ler e fazer o parsing do String para o "objeto json" java
            jsonObjectDeJava = readerDoTextoDoJson.readObject();
            // Acabou, então fechar o reader.
        } catch (Exception e) {
            e.printStackTrace();
        }

        RespostaDTO dto = new RespostaDTO();
        
        System.out.println("teste1");
        // System.out.println( request.getParameter("idBuscar"));
        // Agora é só responder...
        switch (jsonObjectDeJava.getString("funcao")) {
            case "buscar": {
               
               RespostaCompletaDTO respostabusca = (new BiblioPDFDAO()).buscarListaPorPalavraDoTitulo(jsonObjectDeJava); 
               String respostabuscafinal = "";
                
               respostabuscafinal = respostabusca.toString();
               
               System.out.println(respostabuscafinal);
               
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter out = response.getWriter();
                
                out.print(respostabuscafinal);
                
                out.flush();
               
                //----------------------------------------------------------------------
                break;
            }
            
            case "novo": {
                
               String respostanovo = ""; 
               respostanovo = (new BiblioPDFDAO()).salvarNovo(jsonObjectDeJava);  
                System.out.println("novo");
               dto.setPatrimonio(jsonObjectDeJava.getString("patrimonio"));
                              response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(dto.toString());
            out.flush();
                //----------------------------------------------------------------------
                break;
            }
            
            case "deletar": {
                
               String respostanovo = ""; 
               respostanovo = (new BiblioPDFDAO()).deletar(jsonObjectDeJava);  
                
               dto.setPatrimonio( jsonObjectDeJava.getString("patrimonio"));
              
                //----------------------------------------------------------------------
               response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(dto.toString());
            out.flush();
                break;
            }
             case "atualizar": {
               
               String respostanovo = ""; 
               respostanovo = (new BiblioPDFDAO()).atualizar(jsonObjectDeJava);  
                
               dto.setPatrimonio("Servidor recebeu:" + jsonObjectDeJava.getString("funcao"));
               dto.setTitulo("Servidor recebeu:" + respostanovo);
               dto.setAutoria("Servidor recebeu:" + jsonObjectDeJava.getString("patrimonio"));
               dto.setVeiculo("Servidor recebeu:");
               dto.setDatapublicacao("Servidor recebeu:");
               
               response.setContentType("application/json;charset=UTF-8");
               PrintWriter out = response.getWriter();
               out.print(dto.toString());
               out.flush();
               
                //----------------------------------------------------------------------
                break;
               
               
             
            }
            
        }

            

          

            

        }

        // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
        /**
         * Handles the HTTP <code>GET</code> method.
         *
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Handles the HTTP <code>POST</code> method.
         *
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
