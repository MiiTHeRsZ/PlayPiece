const urlParams = new URLSearchParams(window.location.search);

pagina = Number(urlParams.get('pag'));
totalPaginas = sessionStorage.getItem("pageTotalNumber") == undefined ? 1 : Number(sessionStorage.getItem("pageTotalNumber"));
if (pagina <= 0 || pagina > totalPaginas) {
    location.href = "index.html?pag=1"
}

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


    let produtos = await fetch(`/produto/active?page=${Number(pagina) - 1}`).then(response => response.json())
    if (sessionStorage.getItem("pageTotalNumber") == undefined || Number(sessionStorage.getItem("pageTotalNumber")) != produtos.totalPages) {
        totalPaginas = produtos.totalPages;
        sessionStorage.setItem("pageTotalNumber", totalPaginas);
    }


    if (totalPaginas == 1) {
        document.getElementById("btn-proximo").setAttribute("disabled", true)
        document.getElementById("btn-voltar").setAttribute("disabled", true)
    } else if (pagina == 1) {
        document.getElementById("btn-proximo").removeAttribute("disabled")
        document.getElementById("btn-voltar").setAttribute("disabled", true)
    } else if (pagina > 1 && pagina < totalPaginas) {
        document.getElementById("btn-proximo").removeAttribute("disabled")
        document.getElementById("btn-voltar").removeAttribute("disabled")
    } else {
        document.getElementById("btn-proximo").setAttribute("disabled", true)
        document.getElementById("btn-voltar").removeAttribute("disabled")
    }

    produtos.content.forEach((produto, index) => {
        let imagens = produto.listaImagens;
        let imagemPrincipal;
        let link;
        let newLink;
        if (imagens != null) {

            imagens.forEach(image => {
                if (image.padrao) {
                    imagemPrincipal = image.caminho;
                }
            });
            if (imagemPrincipal != null) {
                link = imagemPrincipal.split("/")
                newLink = "./" + link[5] + "/" + link[6] + "/" + link[7] + "/" + link[8]
            } else {
                newLink = "./images/semImagem.webp"
            }
        } else {
            newLink = "./images/semImagem.webp"
        }

        if (produto.ativo) {
            let card = document.createElement("div")
            card.className = "card"
            card.innerHTML = `
                <a href="./pages/produto.html?id=${produto.produtoId}"><img src="${newLink}" class="card-img-top" alt="..."></a>
                <hr>
                <div class="card-body">
                    <a href="./pages/produto.html?id=${produto.produtoId}"><h5 class="card-title">${produto.nome}</h5></a>
                    <p class="card-text">R$ ${parseFloat(produto.preco).toFixed(2).replace(".", ",")}</p>
                </div>
                <div class="btn-space">
                    <a href="./pages/produto.html?id=${produto.produtoId}" class="btn btn-warning">Detalhes</a>
                    <button class="btn btn-warning" onclick="adicionarAoCarrinho(${produto.produtoId})">Adicionar ao carrinho</button>
                </div>
            `
            container_cards.appendChild(card)
        }
    });

    for (let index = 0; index < Number(totalPaginas); index++) {

        let divSquare = document.createElement("div")
        divSquare.setAttribute("id", "square")

        let pageSpace = document.createElement("a")
        pageSpace.setAttribute("href", "index.html?pag=" + (Number(index) + 1))
        pageSpace.setAttribute("id", "pg-num")
        pageSpace.textContent = Number(index) + 1

        if (Number(index) + 1 == pagina) {
            divSquare.setAttribute("class", "pgAtual")
            pageSpace.setAttribute("class", "pgAtual")
        }

        divSquare.appendChild(pageSpace)

        document.getElementById("btn-proximo").insertAdjacentHTML("beforebegin", divSquare.outerHTML)
    }

}

function nextPage() {
    location.href = `?pag=${Number(pagina) + 1}`
}

function previousPage() {
    location.href = `?pag=${Number(pagina) - 1}`
}

async function adicionarAoCarrinho(idProduto) {
    let carrinho = sessionStorage.getItem('carrinho');

    if (carrinho != "" && carrinho != null && carrinho != undefined) {
        let carrinhoFinal = "";
        let check = false;
        let cont = 0;

        if (carrinho.length > 3) {
            for (const item of carrinho.split(",")) {
                prod = item.split("-");
                if (Number(prod[0]) != idProduto) {
                    carrinhoFinal += `${item},`;
                } else {
                    var result = await alterQuantidadeItem(prod[0], (Number(prod[1]) + 1))
                    if (result === true) {
                        carrinhoFinal += `${prod[0]}-${(Number(prod[1]) + 1)},`;
                    } else {
                        carrinhoFinal += `${item},`;

                    }
                    check = true;
                }
                cont++;
            };
        } else {
            prod = carrinho.split("-");
            if (Number(prod[0]) != idProduto) {
                carrinhoFinal += `${carrinho},`;
            } else {
                var result = await alterQuantidadeItem(prod[0], (Number(prod[1]) + 1))
                if (result === true) {
                    carrinhoFinal += `${prod[0]}-${(Number(prod[1]) + 1)},`;
                } else {
                    carrinhoFinal += `${item},`;
                }
                check = true;
            }
            cont++;
        }
        if (!check) {
            carrinhoFinal += `${idProduto}-1,`;
            cont++;
        }
        carrinhoFinal = carrinhoFinal.slice(0, -1);

        sessionStorage.setItem('carrinho', carrinhoFinal);

        document.getElementById("notificacaoCarrinho").innerHTML = cont;
    } else {
        sessionStorage.setItem('carrinho', `${idProduto}-1`);
        document.getElementById("notificacaoCarrinho").innerHTML = 1;
    }
    document.getElementById("notificacaoCarrinho").style.display = "inline";
}

async function alterQuantidadeItem(idProd, novaQuant) {
    let prod;
    const result = await fetch(`/produto/${idProd}`).then(data => data.json())

    if (novaQuant > result.quantidade) {
        alert("Quantidade indisponível em estoque");
        return false;
    } else {
        return true;
    }
}

getProducts();