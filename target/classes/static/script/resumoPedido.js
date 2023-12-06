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
}
menu();

function desconectar() {
    Cookies.remove('sessaoId');
    Cookies.remove('nome');
    sessionStorage.removeItem("carrinho");
    window.open("../index.html", "_self");
}
let dados;
const conectAPI = async () => {
    dados = await fetch(`/cliente/${idCliente}`).then(data => data.json());
    preecheDados();
}

conectAPI();

const endEntrega = JSON.parse(sessionStorage.getItem("endEntrega"));

const pagamento = sessionStorage.getItem('pagamento');

let totalPago = parseFloat(endEntrega.valorFrete);

async function carregarDados() {
    const dadosPedido = await fetch(`/carrinho/search?cliente=${idCliente}`).then(response => response.json());

    const tabela = document.getElementById("produtosTabela");

    let quantidadeItens = 0;
    dadosPedido.itens.forEach((item, index) => {


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
        if (item.produto.listaImagens.length != 0) {
            item.produto.listaImagens.forEach(imagem => {
                if (imagem.padrao) {
                    let caminho = imagem.caminho;
                    let link = caminho.split("/");
                    newLink = "../" + link[5] + "/" + link[6] + "/" + link[7] + "/" + link[8];
                }
                if (imagem == null) {
                    newLink = "../images/semImagem.webp"
                }
            });
        } else {
            newLink = "../images/semImagem.webp"
        }

        prod.textContent = ++quantidadeItens;
        imagem.innerHTML = `<img src="${newLink}" style="width: 30px; height: 30px"></img>`;
        nomeProduto.textContent = `${item.produto.nome}`;
        quantidade.innerHTML = `${item.quantidade}`;
        precoUnitario.textContent = `R$ ${parseFloat(item.produto.preco).toFixed(2).replace(".", ",")}`;
        precoTotal.textContent = `R$ ${parseFloat(item.produto.preco * item.quantidade).toFixed(2).replace(".", ",")}`;

        tr.appendChild(prod);
        tr.appendChild(imagem)
        tr.appendChild(nomeProduto);
        tr.appendChild(quantidade);
        tr.appendChild(precoUnitario);
        tr.appendChild(precoTotal);

        let subTotalPago = 0;
        subTotalPago += (item.produto.preco * item.quantidade);
        if (index == 0) {
            totalPago += parseFloat(subTotalPago);
            document.getElementById("subTotal").textContent += `R$ ${parseFloat(subTotalPago).toFixed(2).replace(".", ",")}`;
            document.getElementById("valorTotal").textContent += `R$ ${parseFloat(totalPago).toFixed(2).replace(".", ",")}`;
        }
    });

    document.getElementById("freteFinal").textContent += `R$ ${parseFloat(endEntrega.valorFrete).toFixed(2).replace(".", ",")}`;

    dados.listaEndereco.map(endereco => {
        if (endereco.enderecoId == endEntrega.idEndEntrega) {
            document.getElementById("enderecoEntrega").textContent = `${endereco.logradouro}, n° ${endereco.numero} - ${endereco.cep}, ${endereco.complemento}${endereco.complemento.length > 0 ? " - " : ""} ${endereco.bairro}, ${endereco.cidade}, ${endereco.uf}`;
        }
    });

    document.getElementById("pagamentoOpc").textContent = pagamento == "BO" ? "Boleto" : "Cartão de Crédito";
}
carregarDados()

const preecheDados = async () => {

    dados.listaEndereco.map(endereco => {
        let opcao = document.createElement("option");
        opcao.value = endereco.enderecoId;
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
}

async function finalizarPedido() {

    var statusFinal;
    const checkout = await fetch(`/pedido/import?cliente=${idCliente}&endereco=${endEntrega.idEndEntrega}&frete=${endEntrega.valorFrete}&modoPagamento=${pagamento}`, {
        method: "POST",
        headers: { 'Content-Type': 'application/json' },
        body: ""
    }).then(
        data => {
            statusFinal = data.status
            return data.json()
        }
    );
    console.log(checkout);
    if (statusFinal == 200 || statusFinal == 201) {
        alert(`Pedido ${(checkout.pedidoId.toString()).padStart(6, "0")} realizado com sucesso!`);
        sessionStorage.removeItem("pagamento")
        sessionStorage.removeItem("carrinho")
        sessionStorage.removeItem("endEntrega")
        window.open("../index.html", "_self");
    } else {
        alert("Erro ao realizar o pedido!");
    }
}