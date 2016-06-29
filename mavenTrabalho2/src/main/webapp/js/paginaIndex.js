
window.onload = iniciar;

var controle;

var dialogo;

function iniciar() {

    var objDialogo = function () {
        this.elementoDialogo = document.getElementById('idMsgDialogo3');
        this.escreverMensagem = function (classeDaCor, texto) {
            this.elementoDialogo.innerHTML = texto;
            this.elementoDialogo.setAttribute('class', classeDaCor);
        };
    };

    var estadoEditando = false;

    dialogo = new objDialogo();


    var ControleDeChecks = function () {
        this.patrimonio = document.getElementById('idcheckpatrimonio');
        this.tituloOU = document.getElementById('idchecktituloOU');
        this.tituloE = document.getElementById('idchecktituloE');
        this.autoriaOU = document.getElementById('idcheckautoriaOU');
        this.autoriaE = document.getElementById('idcheckautoriaE');
        this.veiculoOU = document.getElementById('idcheckveiculoOU');
        this.veiculoE = document.getElementById('idcheckveiculoE');
        this.datapublicacaoOU = document.getElementById('idcheckdatapublicacaoOU');
        this.datapublicacaoE = document.getElementById('idcheckdatapublicacaoE');
        this.palchaveOU = document.getElementById('idcheckpalchaveOU');
        this.palchaveE = document.getElementById('idcheckpalchaveE');

    };

    controle = new ControleDeChecks();

    controle.patrimonio.addEventListener("click",
            function () {
                controle.tituloE.checked = false;
                controle.tituloOU.checked = false;
                controle.autoriaE.checked = false;
                controle.autoriaOU.checked = false;
                controle.veiculoE.checked = false;
                controle.veiculoOU.checked = false;
                controle.datapublicacaoE.checked = false;
                controle.datapublicacaoOU.checked = false;
                controle.palchaveE.checked = false;
                controle.palchaveOU.checked = false;

            }
    );

    controle.tituloE.addEventListener("click",
            function () {
                controle.patrimonio.checked = false;
                if (controle.tituloE.checked) {
                    controle.tituloOU.checked = false;
                }
            }
    );

    controle.tituloOU.addEventListener("click",
            function () {
                controle.patrimonio.checked = false;
                if (controle.tituloOU.checked) {
                    controle.tituloE.checked = false;
                }
            }
    );

    controle.autoriaE.addEventListener("click",
            function () {
                controle.patrimonio.checked = false;
                if (controle.autoriaE.checked) {
                    controle.autoriaOU.checked = false;
                }
            }
    );

    controle.autoriaOU.addEventListener("click",
            function () {
                controle.patrimonio.checked = false;
                if (controle.autoriaOU.checked) {
                    controle.autoriaE.checked = false;
                }
            }
    );

    controle.veiculoE.addEventListener("click",
            function () {
                controle.patrimonio.checked = false;
                if (controle.veiculoE.checked) {
                    controle.veiculoOU.checked = false;
                }
            }
    );

    controle.veiculoOU.addEventListener("click",
            function () {
                controle.patrimonio.checked = false;
                if (controle.veiculoOU.checked) {
                    controle.veiculoE.checked = false;
                }
            }
    );
    controle.datapublicacaoOU.addEventListener("click",
            function () {
                controle.patrimonio.checked = false;
                if (controle.datapublicacaoOU.checked) {
                    controle.datapublicacaoE.checked = false;
                }
            }
    );
    controle.datapublicacaoE.addEventListener("click",
            function () {
                controle.patrimonio.checked = false;
                if (controle.datapublicacaoE.checked) {
                    controle.datapublicacaoOU.checked = false;
                }
            }
    );
    controle.palchaveOU.addEventListener("click",
            function () {
                controle.patrimonio.checked = false;
                if (controle.palchaveOU.checked) {
                    controle.palchaveE.checked = false;
                }
            }
    );

    controle.palchaveE.addEventListener("click",
            function () {
                controle.patrimonio.checked = false;
                if (controle.palchaveE.checked) {
                    controle.palchaveOU.checked = false;
                }
            }
    );

    document.getElementById('idLimparBusca').addEventListener("click",
            function () {
                document.getElementById('idpatrimonio2').value = "";
                document.getElementById('idtitulo2').value = "";
                document.getElementById('idautoria2').value = "";
                document.getElementById('idveiculo2').value = "";
                document.getElementById('iddatapublicacao21').value = "";
                document.getElementById('iddatapublicacao22').value = "";
                document.getElementById('idpalchave2').value = "";

            }
    );


    dialogo.escreverMensagem('dialogoVerde', 'Só leitura');

    function mudarAtributosEditando(estado) {
        if (estado) {
            document.getElementById('idpatrimonio3').removeAttribute("readonly");
            document.getElementById('idtitulo3').removeAttribute("readonly");
            document.getElementById('idautoria3').removeAttribute("readonly");
            document.getElementById('idveiculo3').removeAttribute("readonly");
            document.getElementById('idpalchave3').removeAttribute("readonly");
            document.getElementById('iddatapublicacao3').removeAttribute("readonly");
        } else {
            document.getElementById('idpatrimonio3').setAttribute("readonly", true);
            document.getElementById('idtitulo3').setAttribute("readonly", true);
            document.getElementById('idautoria3').setAttribute("readonly", true);
            document.getElementById('idveiculo3').setAttribute("readonly", true);
            document.getElementById('idpalchave3').setAttribute("readonly", true);
            document.getElementById('iddatapublicacao3').setAttribute("readonly", true);
        }
    }

    document.getElementById('idLimparCat').addEventListener("click",
            function () {
                document.getElementById('idpatrimonio3').value = "";
                document.getElementById('idtitulo3').value = "";
                document.getElementById('idautoria3').value = "";
                document.getElementById('idveiculo3').value = "";
                document.getElementById('iddatapublicacao3').value = "";
                document.getElementById('idpalchave3').value = "";

            }
    );

    document.getElementById('idEditar').addEventListener("click",
            function () {
                if (estadoEditando) {
//                document.getElementById('idDialogo').innerHTML = "Só leitura";
                    dialogo.escreverMensagem('dialogoVerde', 'Só leitura');
                    estadoEditando = false;
                } else {
//                document.getElementById('idDialogo').innerHTML = "Editando";
                    dialogo.escreverMensagem('dialogoVerde', 'Editando');
                    estadoEditando = true;
                }
                mudarAtributosEditando(estadoEditando);
            }
    );



    function dadosEscolhidos() {
        var resposta = {};
        resposta.funcao = "buscar";
        if (controle.patrimonio.checked) {
            resposta.patrimonio = document.getElementById('idpatrimonio2').value;
        } else {
            if (controle.tituloE.checked) {
                resposta.titulo = {};
                resposta.titulo.texto = document.getElementById('idtitulo2').value;
                resposta.titulo.modo = "E";
            } else if (controle.tituloOU.checked) {
                resposta.titulo = {};
                resposta.titulo.texto = document.getElementById('idtitulo2').value;
                resposta.titulo.modo = "OU";
            }
            if (controle.autoriaE.checked) {
                resposta.autoria = {};
                resposta.autoria.texto = document.getElementById('idautoria2').value;
                resposta.autoria.modo = "E";
            } else if (controle.autoriaOU.checked) {
                resposta.autoria = {};
                resposta.autoria.texto = document.getElementById('idautoria2').value;
                resposta.autoria.modo = "OU";
            }
           
            if (controle.veiculoE.checked) {
                resposta.veiculo = {};
                resposta.veiculo.texto = document.getElementById('idveiculo2').value;
                resposta.veiculo.modo = "E";
                
            } else if (controle.veiculoOU.checked) {
                resposta.veiculo = {};
                resposta.veiculo.texto = document.getElementById('idveiculo2').value;
                resposta.veiculo.modo = "OU";
            }
          /*  if (controle.palchaveE.checked) {
                resposta.palchave = {};
                resposta.palchave.texto = document.getElementById('idpalchave2').value;
                resposta.palchave.modo = "E";
            } else if (controle.palchaveOU.checked) {
                resposta.palchave = {};
                resposta.palchave.texto = document.getElementById('idpalchave2').value;
                resposta.palchave.modo = "OU";
            }*/
        }

        return resposta;
    }


    document.getElementById('idBuscar').addEventListener("click",
            function () {
                document.getElementById('idMsgDialogo2').innerHTML = "Busca realizada";
                fazerPedidoAJAX(dadosEscolhidos(), popularCamposComRespostaJSONBusca);

            }
    );

    document.getElementById('idExcluir').addEventListener("click",
            function() {
                
                var resposta = {};
                resposta.funcao = "deletar";
                resposta.patrimonio = document.getElementById('idpatrimonio3').value;
                fazerPedidoAJAX(resposta, popularCamposComRespostaJSONDeletar);
                document.getElementById('idMsgDialogo2').innerHTML = JSON.stringify(resposta);
                
            }
    );

    document.getElementById('idSalvarNovo').addEventListener("click",
            function() {

                var resposta = {};
                resposta.funcao = "novo";
                resposta.patrimonio = document.getElementById('idpatrimonio3').value;
                resposta.titulo = document.getElementById('idtitulo3').value;
                resposta.autoria = document.getElementById('idautoria3').value;
                resposta.veiculo = document.getElementById('idveiculo3').value;
                resposta.datapublicacao = document.getElementById('iddatapublicacao3').value = "";
                resposta.palchave = document.getElementById('idpalchave3').value;
                document.getElementById('idMsgDialogo3').innerHTML = JSON.stringify(resposta);
                fazerPedidoAJAX(resposta, popularCamposComRespostaJSON);
            }
    );

    document.getElementById('idSalvarAtual').addEventListener("click",
            function () {

                var resposta = {};
                resposta.funcao = "atualizar";
                resposta.patrimonio = document.getElementById('idpatrimonio3').value;
                resposta.titulo = document.getElementById('idtitulo3').value;
                resposta.autoria = document.getElementById('idautoria3').value;
                resposta.veiculo = document.getElementById('idveiculo3').value;
                resposta.datapublicacao = document.getElementById('iddatapublicacao3').value = "";
                resposta.palchave = document.getElementById('idpalchave3').value;
                document.getElementById('idMsgDialogo2').innerHTML = JSON.stringify(resposta);
                fazerPedidoAJAX(resposta, popularCamposComRespostaJSON);
            }
    );
    
    document.getElementsByClassName(consulta).addEventListener("click",
            function () {
                dialogo.escreverMensagem('dialogoVerde', 'Só leitura');

            }
    );



}



function popularCamposComRespostaJSON(objJSONresp) {

    document.getElementById('idMsgDialogo3').innerHTML = JSON.stringify(objJSONresp);
    //document.getElementById('idpatrimonio2').value = objJSONresp.patrimonio;
    //document.getElementById('idtitulo2').value = objJSONresp.titulo;
    //document.getElementById('idautoria2').value = objJSONresp.autoria;
    //document.getElementById('idveiculo2').value = objJSONresp.veiculo;


}

function popularCamposComRespostaJSONBusca(objJSONresp) {
    
    var texto= "";
    
    for ( i = 0; i< objJSONresp.arrayDeRespostas.length; i++){        
     texto += " <div classe=\"Consulta\" style=\" margin-left: 10px;\"> <br> Patrimonio:" + JSON.stringify(objJSONresp.arrayDeRespostas[i].patrimonio) + "<br> Título:" + JSON.stringify(objJSONresp.arrayDeRespostas[i].titulo) + "<br> Autoria:" + JSON.stringify(objJSONresp.arrayDeRespostas[i].autoria) + "<br> Veículo:" + JSON.stringify(objJSONresp.arrayDeRespostas[i].veiculo) + "<br> <hr> </div>";
    }
    
    document.getElementById('idTabelaResultados').innerHTML = texto;    
    document.getElementById('idNroRows').innerHTML = objJSONresp.nroRows;
    
    //document.getElementById('idpatrimonio2').value = objJSONresp.patrimonio;
    //document.getElementById('idtitulo2').value = objJSONresp.titulo;
    //document.getElementById('idautoria2').value = objJSONresp.autoria;
    //document.getElementById('idveiculo2').value = objJSONresp.veiculo;


}
;

function popularCamposComRespostaJSONDeletar(objJSONresp) {

    document.getElementById('idMsgDialogo3').innerHTML = "Elemento Deletado";
    document.getElementById('idpatrimonio3').value = "";
    document.getElementById('idtitulo3').value = "";
    document.getElementById('idautoria3').value = "";
    document.getElementById('idveiculo3').value = "";
    document.getElementById('iddatapublicacao3').value = "";
    document.getElementById('idpalchave3').value = "";


}
;

function fazerPedidoAJAX(objetoJSON, funcPopularPagina) {
    var stringJSON = JSON.stringify(objetoJSON);
    var objPedidoAJAX = new XMLHttpRequest();
    objPedidoAJAX.open("POST", "controller");
    objPedidoAJAX.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    // Prepara recebimento da resposta: tipo da resposta JSON!
    objPedidoAJAX.responseType = 'json';
    objPedidoAJAX.onreadystatechange =
            function () {
                dialogo.escreverMensagem('dialogoVerde', objPedidoAJAX.status);
                if (objPedidoAJAX.readyState === 4 && objPedidoAJAX.status === 200) {
                    // A resposta 'response' já é avaliada para json!
                    // Ao contraro da resposta responseText.
                    funcPopularPagina(objPedidoAJAX.response);

                }
                ;
            };
    // Envio do pedido
    objPedidoAJAX.send(stringJSON);

}
;








