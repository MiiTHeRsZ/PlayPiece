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
        document.getElementById("enderecoEntregaLogado").style.display = "none";
    } else {
        nome_perfil.innerHTML = `Olá, ${getCookie("nome")}!`;
        login_perfil.innerHTML = "Perfil";
        login_perfil.href = "./perfilCliente.html";
        sair.style.display = '';
        document.getElementById("enderecoEntrega").style.display = "none";
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

async function criarListaPedidos() {
    const listaPedidos = await fetch(`/pedidos/search?cliente=${idCliente}`).then(data => data.json());

    listaPedidos.forEach(pedido => {
        const tabela = document.getElementById("itensPedido");

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

        numero.textContent = `${pedido.numero}`;
        data.textContent = `${pedido.data}`;
        valor_total.textContent = `${pedido.valor_total}`;
        status.textContent = `${pedido.status}`;
        detalhes.textContent = `<button onclick="detalhePedido(${pedido.numero})"><i class="fi fi-br-list-check"></i></button>`;

        tr.appendChild(numero);
        tr.appendChild(data);
        tr.appendChild(valor_total);
        tr.appendChild(status);
        tr.appendChild(detalhes);

        if (pedido.status == 'Aguardando') {
            status.style.color = "#ff9800"
        } else if (pedido.status == 'Em preparo') {
            status.style.color = "#4caf50"
        } else if (pedido.status == 'Entregue') {
            status.style.color = "#2196F3"
        }
    });
}

criarListaPedidos();