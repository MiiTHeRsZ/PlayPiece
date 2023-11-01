function setCookie(nome, info, exdays) {
    Cookies.set(nome, info, exdays)
}

function getCookie(nome) {
    return Cookies.get(nome)
}

function checkCookie(nome) {
    var sessaoId = getCookie(nome);
    if (sessaoId != undefined) {
        document.getElementById("perfil").href = `./pages/perfilCliente.html`;
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
        login_perfil.href = "./pages/loginCliente.html";
        sair.style.display = 'none';
    } else {
        nome_perfil.innerHTML = `Olá, ${getCookie("nome")}!`;
        login_perfil.innerHTML = "Perfil";
        login_perfil.href = "./pages/perfilCliente.html";
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

const container_cards = document.getElementById("produtos");

async function getProducts() {

    let produtos = await fetch('/produto').then(response => response.json())

    produtos.forEach(produto => {
        let imagens = produto.listaImagens;
        let imagemPrincipal;

        imagens.forEach(image => {
            if (image.padrao) {
                imagemPrincipal = image.caminho;
            }
        });

        if (produto.ativo) {
            let link = imagemPrincipal.split("/")
            let newLink = "./" + link[5] + "/" + link[6] + "/" + link[7] + "/" + link[8]
            let card = document.createElement("div")
            card.className = "card"
            card.innerHTML = `
            <a href="./pages/produto.html?id=${produto.id}"><img src="${newLink}" class="card-img-top" alt="..."></a>
                <hr>
                <div class="card-body">
                <a href="./pages/produto.html?id=${produto.id}"><h5 class="card-title">${produto.nome}</h5></a>
                    <p class="card-text">R$ ${parseFloat(produto.preco).toFixed(2).replace(".", ",")}</p>
                    <a href="./pages/produto.html?id=${produto.id}" class="btn btn-primary">Detalhes</a>
                </div>
            `
            container_cards.appendChild(card)
        }
    });
}

getProducts();