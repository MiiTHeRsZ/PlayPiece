const urlParams = new URLSearchParams(window.location.search);
idPedido = urlParams.get('id');

function setCookie(nome, info, exdays) {
    Cookies.set(nome, info, exdays)
}

function getCookie(nome) {
    return Cookies.get(nome)
}

function checkCookie(nome) {
    var sessaoId = getCookie(nome);
    if (sessaoId != undefined) {
        document.getElementById("perfil").href = `./perfilCliente.html`;
    }
}
checkCookie('sessaoId');

var idCliente = getCookie('sessaoId');

function menu() {
    let nome_perfil = document.getElementById("nome-perfil");
    let login_perfil = document.getElementById("login-perfil");
    let sair = document.getElementById("sair");

    if (idCliente == undefined) {
        nome_perfil.innerHTML = "Seja Bem-Vindo(a)!";
        login_perfil.innerHTML = "Login";
        login_perfil.href = "./loginCliente.html";
        sair.style.display = 'none';
    } else {
        nome_perfil.innerHTML = `Olá, ${getCookie("nome")}!`;
        login_perfil.innerHTML = "Perfil";
        login_perfil.href = "./perfilCliente.html";
        sair.style.display = '';
    }

    let carrinho = sessionStorage.getItem('carrinho');

    if (carrinho != "" && carrinho != null && carrinho != undefined) {
        let cont = 0;

        if (carrinho.length > 3) {
            carrinho.split(",").forEach(item => {
                cont++;
            });
        } else {
            cont++;
        }

        document.getElementById("notificacaoCarrinho").innerHTML = cont;
        document.getElementById("notificacaoCarrinho").style.display = "inline";
    }
}
menu();

function desconectar() {
    Cookies.remove('sessaoId');
    Cookies.remove('nome');
    sessionStorage.removeItem("carrinho");
    window.location.reload();
}

async function carregarDados() {
    const dadosPedido = await fetch(`/pedido/search?id=${idPedido}`).then(response => response.json());

    const tabela = document.getElementById("produtosTabela");

    let quantidadeItens = 0;
    dadosPedido.itens.forEach(item => {
        let tr = document.createElement("tr");
        tr.setAttribute("class", "itemTabela");
        tabela.appendChild(tr);

        let prod = document.createElement("td");
        prod.setAttribute("class", "itemProduto");
        let imagem = document.createElement("td");
        imagem.setAttribute("class", "imagemProduto");
        let nomeProduto = document.createElement("td");
        nomeProduto.setAttribute("class", "nomeProduto");
        let quantidade = document.createElement("td");
        quantidade.setAttribute("class", "quantidadeProduto")
        let precoUnitario = document.createElement("td");
        precoUnitario.setAttribute("class", "precoUnitarioProduto")
        let precoTotal = document.createElement("td");
        precoTotal.setAttribute("class", "precoTotalProduto");

        let newLink;
        item.produto.listaImagens.forEach(imagem => {
            if (imagem.padrao) {
                let caminho = imagem.caminho;
                let link = caminho.split("/");
                newLink = "../" + link[5] + "/" + link[6] + "/" + link[7] + "/" + link[8];
            }
        });

        prod.textContent = ++quantidadeItens;
        imagem.innerHTML = `<img src="${newLink}" style="width: 30px; height: 30px"></img>`;
        nomeProduto.textContent = `${item.produto.nome}`;
        quantidade.innerHTML = `${item.quantidade}`;
        precoUnitario.textContent = `${parseFloat(item.valorUnitario).toFixed(2).replace(".", ",")}`;
        precoTotal.textContent = `${parseFloat(item.valorTotal).toFixed(2).replace(".", ",")}`;

        tr.appendChild(prod);
        tr.appendChild(imagem)
        tr.appendChild(nomeProduto);
        tr.appendChild(quantidade);
        tr.appendChild(precoUnitario);
        tr.appendChild(precoTotal);
    });

    document.getElementById("frete").value = `R$ ${parseFloat(endEntrega.valorFrete).toFixed(2).replace(".", ",")}`;

    dados.listaEndereco.forEach(endereco => {
        if (endereco.enderecoId == endEntrega.idEndEntrega) {
            document.getElementById("cep").value = endereco.cep;
            document.getElementById("logradouro").value = endereco.logradouro;
            document.getElementById("numero").value = endereco.numero;
            document.getElementById("complemento").value = endereco.complemento;
            document.getElementById("bairro").value = endereco.bairro;
            document.getElementById("cidade").value = endereco.cidade;
            document.getElementById("uf").value = endereco.uf;
        }
    });

    document.getElementById("pagamentoOpc").textContent = pagamento == "BO" ? "Boleto" : "Cartão de Crédito";
}
carregarDados()