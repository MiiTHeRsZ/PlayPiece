const urlParams = new URLSearchParams(window.location.search);
id = urlParams.get('id');
try {
    email = urlParams.get('email');
} catch (error) {
    console.log(error);
}

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

document.getElementById("perfil").addEventListener("click", async () => {
    let check = false
    if (document.getElementById("menuBox").style.display == "none") {

        if (idCliente == undefined) {
            document.querySelector("#box").innerHTML = `<div class="Deslogado boxInfo">
            <h5 class="nome">Seja Bem-Vindo(a)!</h5>
            <a href="./pages/loginCliente.html">Login/Cadastro</a>
            <a href="./pages/loginBackoffice.html">Backoffice</a>
            </div>`
            check = true
        }
        else {

            const cliente = await fetch(`/cliente/${idCliente}`).then((data) => data.json())
            let nome = cliente.nome.split(" ")
            document.querySelector("#box").innerHTML = `<div class="Logado boxInfo">
            <h5 class="nome">Eai, ${nome[0]}!</h5>
            <a href="./pages/perfilCliente.html">Meu perfil</a>
            <a href="" id="sair-perfil">Sair</a>
            </div>`

            check = true

            document.getElementById("sair-perfil").addEventListener("click", (e) => {
                e.preventDefault();

                const resp = window.confirm("Deseja encerrar a sessão?");
                if (resp == 1) {
                    Cookies.remove('sessaoId');
                    window.open("../index.html", "_self")
                }
            });

        }

        document.addEventListener('click', (event) => {
            const box = document.getElementById('menuBox');
            console.log(event.target.className);

            if (!document.getElementById("box").contains(event.target) && event.target.className != 'fi fi-br-user') {
                console.log(document.getElementById("menuBox").style.display);
                console.log("esconde");
                box.style.display = 'none';
            }
        });

        document.getElementById("menuBox").style.display = "block"
        console.log("mosta");

    }
    else {
        document.getElementById("menuBox").style.display = "none"
    }
})

if (email != null) {
    document.getElementById("perfil").href = `./perfilCliente.html?email=${email}`;
    document.getElementById("logo").href += `?email=${email}`;
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
            <button disabled>Adicionar ao carrinho</button>
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
    imagens.forEach(imagem => {
        let link = imagem.caminho.split("/")
        let newLink = "../" + link[5] + "/" + link[6] + "/" + link[7] + "/" + link[8]
        let img = document.createElement("div")
        img.className = "carousel-item"
        img.innerHTML = `
        <img src="${newLink}" class="d-block w-100" alt="...">
        `
        document.getElementsByClassName("carousel-inner")[0].appendChild(img)
    });
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