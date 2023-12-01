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
}
menu();

function desconectar() {
    Cookies.remove('sessaoId');
    Cookies.remove('nome');
    sessionStorage.removeItem("carrinho");
    window.location.reload();
}

function gerarNumeroAleatorioBoleto() {
    const tamanho = 44;
    const crypto = window.crypto || window.msCrypto;
    const valoresAleatorios = new Uint32Array(tamanho);
    crypto.getRandomValues(valoresAleatorios);
    let numeroBoleto = '';

    for (let i = 0; i < tamanho; i++) {
        const digito = valoresAleatorios[i] % 10;
        numeroBoleto += digito.toString();
    }

    return numeroBoleto;
}

document.getElementById("boleto").addEventListener("change", verificaPagamento);
document.getElementById("cartao").addEventListener("change", verificaPagamento);

async function total() {
    const valorTotal = await fetch(`/carrinho/search?cliente=${idCliente}`).then(data => data.json());
    let total = 0;
    valorTotal.itens.forEach(item => {
        total += item.produto.preco * item.quantidade;
    })
    document.querySelectorAll(".valorTotal").forEach(item => item.textContent = `Total: R$ ${parseFloat(total).toFixed(2).replace(".", ",")}`);
}
total();

function verificaPagamento() {
    document.querySelectorAll("input[type='radio']").forEach(async item => {
        if (item.checked) {
            if (item.value == "boleto") {
                document.getElementById("numeroBoleto").textContent = gerarNumeroAleatorioBoleto();
                document.getElementById("validadeBoleto").textContent = new Date(new Date().getTime() + (10 * 24 * 60 * 60 * 1000)).toISOString().substr(0, 10);
                document.getElementById("infoBoleto").style.display = "inline";
                document.getElementById("infoCartao").style.display = "none";
            } else {
                document.getElementById("infoBoleto").style.display = "none";
                document.getElementById("infoCartao").style.display = "inline";
            }
        }
    });
}

document.getElementById("infoCartao").querySelectorAll("input").forEach((item, index) => {
    if (index < 2) {
        item.addEventListener('input', (event) => {
            if (event.target.value.length > event.target.maxLength) {
                event.target.value = event.target.value.slice(0, event.target.maxLength);
            }
        });
    }
});

document.getElementById("parcelas").addEventListener("change", () => {
    let parcelas = document.getElementById("parcelas").value;
    let valorParcela = parseFloat(valorTotal / parcelas);
    document.querySelectorAll(".valorTotal").forEach(item => item.textContent = `R$ ${valorParcela}`);
})

document.getElementById("confirmar-pagamento").addEventListener("click", () => {
    var boleto = false;
    var cartao = false;
    if (document.getElementById("boleto").checked) {
        boleto = true;
    } else if (document.getElementById("cartao").checked) {
        let numCart = document.getElementById("numeroCartao").value;
        let cvvCart = document.getElementById("cvvCartao").value;
        let nomeCart = document.getElementById("nomeCartao").value.split(" ");
        let vencCart = document.getElementById("validadeCartao").value;
        console.log(vencCart);

        if (numCart.length != 16 || cvvCart.length < 3 || nomeCart.length < 2 || nomeCart[0].length < 3 || nomeCart[1].length < 3 || vencCart == "") {
            alert("Preencha corretamente os campos!");
        } else {
            cartao = true;
        }
    }

    if (!boleto && !cartao) {
        alert("Selecione um método de pagamento!");
    } else {
        if (boleto) {
            sessionStorage.setItem("pagamento", "BO");
            window.location.href = "./resumoPedido.html";
        } else if (cartao) {
            sessionStorage.setItem("pagamento", "CC");
            window.open("./resumoPedido.html", "_self");
        }
    }
});