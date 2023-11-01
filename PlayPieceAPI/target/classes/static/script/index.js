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

var service = new google.maps.DistanceMatrixService();
async function testeAPI() {

    service.getDistanceMatrix(
        {
            origins: ["04671-071"],
            destinations: ["02042-030"],
            travelMode: 'DRIVING',
            transitOptions: {

                modes: ['BUS', 'TRAIN', 'SUBWAY'],
                routingPreference: 'FEWER_TRANSFERS'
            },
            unitSystem: google.maps.UnitSystem.METRIC,
            avoidHighways: false,
            avoidTolls: false,
        }, callback);

    function callback(response, status) {

        if (status == 'OK') {
            console.log("Distância: " + response.rows[0].elements[0].distance.text);
            console.log("Tempo estimado: " + response.rows[0].elements[0].duration.text);

        } else {
            console.log('O que aconteceu?');
        }
    };

}

testeAPI()