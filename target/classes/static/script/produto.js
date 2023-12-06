const urlParams = new URLSearchParams(window.location.search);
id = urlParams.get('id');

function getCookie(nome) {
    return Cookies.get(nome)
}

function setCookie(nome, info, exdays) {
    Cookies.set(nome, info, exdays)
}

function checkCookie(nome) {
    var sessaoId = getCookie(nome);
    if (sessaoId != undefined) {
        document.getElementById("perfil").href = `./pages/perfilCliente.html`;
    }
}
checkCookie('sessaoId')

var idCliente = getCookie('sessaoId')

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

const container = document.getElementById("container");

async function getProduct() {
    let produto = await fetch(`/produto/${id}`).then(response => response.json())

    let conteudo = document.createElement("div")

    conteudo.innerHTML = `
    <section class="carouselProdutos">
    <div id="carouselExample" class="carousel slide">
        <div class="carousel-inner">
        
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample"
            data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExample"
            data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
    </section>
    <section id="detalhes">
        <h1 id="nomeProduto">${produto.nome}</h1>
        <h4 for="avaliacao">Avaliação:</h4>
        <div class="avalicao-box">
            <meter id="avaliacao" min="0" max="5" value="${produto.avaliacao}">
            </meter><span> ${produto.avaliacao}</span>
        </div>
        <div id="div-valor">
            <p id="valor">R$ ${parseFloat(produto.preco).toFixed(2).replace(".", ",")}</p>
            <button onclick="adicionarAoCarrinho(${produto.produtoId})" class="btn btn-warning">Adicionar ao carrinho</button>
        </div>
        <h4 for="descricao">Descrição:</h4>
        <p style="white-space:pre-line" id="descricao">${produto.descricao}</p>
        </section>
        `;

    container.appendChild(conteudo)
    gerarCarousel(produto.listaImagens)
    criarEstrelas(produto)
}

async function gerarCarousel(imagens) {

    let img;
    if (imagens.length != 0) {

        imagens.forEach(imagem => {
            let link = imagem.caminho.split("/");
            let newLink = "../" + link[5] + "/" + link[6] + "/" + link[7] + "/" + link[8];
            img = document.createElement("div")
            img.className = "carousel-item"
            img.innerHTML = `
            <img src="${newLink}" class="d-block w-100" alt="...">
            `
            document.getElementsByClassName("carousel-inner")[0].appendChild(img)
        });


    } else {
        img = document.createElement("div")
        img.className = "carousel-item"
        img.innerHTML = `
        <img src="../images/semImagem.webp" class="d-block w-100" alt="...">
        `
        document.getElementsByClassName("carousel-inner")[0].appendChild(img)
    }

    document.getElementsByClassName("carousel-inner")[0].getElementsByClassName("carousel-item")[0].setAttribute("class", "carousel-item active");
}

function criarEstrelas(produto) {
    let avaliacao = produto.avaliacao.toFixed(1)
    avaliacao = avaliacao.toString()
    let valores = avaliacao.split('.')
    let estrelas = [0, 0, 0, 0, 0]
    if (Number(valores[1]) == 5) {
        valores[1] = 1
    } else {
        valores[1] = 0
    }

    const meter = document.querySelector("meter");
    let count = 0

    while (count < Number(valores[0])) {
        estrelas[count] = 10
        count++
    }
    if (count < estrelas.length) {
        while (count < estrelas.length) {
            if (Number(valores[1] == 1)) {
                estrelas[count] = 5
                count = 5
            }
            count++
        }
    }

    estrelas.forEach(item => {
        if (item == 10) {
            let estrela = document.createElement("i")
            estrela.setAttribute("class", "material-icons")
            estrela.innerHTML = "star"
            meter.appendChild(estrela)
        } else if (item == 5) {
            let estrela = document.createElement("i")
            estrela.setAttribute("class", "material-icons")
            estrela.innerHTML = "star_half"
            meter.appendChild(estrela)
        } else if (item == 0) {
            let estrela = document.createElement("i")
            estrela.setAttribute("class", "material-icons")
            estrela.innerHTML = "star_border"
            meter.appendChild(estrela)
        }
    })

}

getProduct()

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