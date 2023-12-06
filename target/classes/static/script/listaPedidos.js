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
    window.open("../index.html", "_self");
}

async function criarListaPedidos() {
    const listaPedidos = await fetch(`/pedido/search?cliente=${idCliente}`).then(data => data.json());

    listaPedidos.forEach(pedido => {
        const tabela = document.getElementById("itensPedidos");

        let tr = document.createElement("tr");
        tr.setAttribute("class", "itemTabela");
        tabela.appendChild(tr);

        let numero = document.createElement("td");
        numero.setAttribute("class", "numeroPedido");
        let data = document.createElement("td");
        data.setAttribute("class", "dataPedido");
        let valor_total = document.createElement("td");
        valor_total.setAttribute("class", "valor_totalPedido");
        let status = document.createElement("td");
        status.setAttribute("class", "statusPedido");
        let detalhes = document.createElement("td");
        detalhes.setAttribute("class", "detalhesPedido");

        let statusPed = "";

        switch (pedido.statusPagamento) {
            case "AP":
                statusPed = `Aguardando Pagamento`;
                break;
            case "PR":
                statusPed = `Pagamento Rejeitado`;
                break;
            case "PS":
                statusPed = `Pagamento Com Sucesso`;
                break;
            case "AR":
                statusPed = `Aguardando Retirada`;
                break;
            case "ET":
                statusPed = `Em Trânsito`;
                break;
            case "EN":
                statusPed = `Entregue`;
                break;
        }

        numero.textContent = `${pedido.pedidoId}`;
        data.textContent = `${(pedido.dataPedido).slice(0, 10).split("-").reverse().join(" - ")}`;
        valor_total.textContent = `R$ ${parseFloat(pedido.valorTotal).toFixed(2).replace(".", ",")}`;
        status.textContent = `${statusPed}`;
        detalhes.innerHTML = `<a href="./detalhesPedido.html?id=${pedido.pedidoId}" class="btn btn-warning"><i class="fi fi-br-list-check"></i></a>`;
        tr.appendChild(numero);
        tr.appendChild(data);
        tr.appendChild(valor_total);
        tr.appendChild(status);
        tr.appendChild(detalhes);

        switch (pedido.statusPagamento) {
            case "AP":
                status.style.color = "#f5cb67"
                status.style.fontWeight = "bold"
                break;
            case "PR":
                status.style.color = "red";
                break;
            case "PS":
                status.style.color = "springgreen"
                break;
            case "AR":
                status.style.color = "rgb(111 171 181)"
                break;
            case "ET":
                status.style.color = "blue"
                break;
            case "EN":
                status.style.color = "blueviolet"
                break;
        }

        status.style.fontWeight = "bold"
    });
}

criarListaPedidos();