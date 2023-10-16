const urlParams = new URLSearchParams(window.location.search);
email = urlParams.get('email');

if (email != null) {
    document.getElementById("perfil").href = `./pages/perfilCliente.html?email=${email}`;
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