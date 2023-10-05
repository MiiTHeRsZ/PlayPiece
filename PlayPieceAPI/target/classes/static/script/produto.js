const urlParams = new URLSearchParams(window.location.search);
id = urlParams.get('id');

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
        <p id="descricao">${produto.descricao}</p>
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