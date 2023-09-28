const urlParams = new URLSearchParams(window.location.search);
id = urlParams.get('id');

const container = document.getElementById("container");

async function getProduct() {
    let produto = await fetch(`http://localhost:8080/produto/${id}`).then(response => response.json())

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
        <meter id="avaliacao" min="0" max="5" value="${produto.avaliacao}"></meter><span> ${produto.avaliacao}</span>
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
}

async function gerarCarousel(imagens) {
    imagens.forEach(imagem => {
        let link = imagem.caminho.split("/")
        let newLink = "../" + link[4] + "/" + link[5] + "/" + link[6] + "/" + link[7]
        let img = document.createElement("div")
        img.className = "carousel-item"
        img.innerHTML = `
        <img src="${newLink}" class="d-block w-100" alt="...">
        `
        document.getElementsByClassName("carousel-inner")[0].appendChild(img)
    });
    document.getElementsByClassName("carousel-inner")[0].getElementsByClassName("carousel-item")[0].setAttribute("class", "carousel-item active");
}

getProduct()