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

async function carregarDados() {
    const dadosPedido = await fetch(`/carrinho/${idCliente}`).then(response => response.json());
    const SSFrete = JSON.parse(sessionStorage.getItem("dadosFrete"));
    /* const SSFrete = {
        idEnderecoEntrega: idCep,
        valorFrete: freteEscolhido
    } */
    const dadosFrete = await fetch(`/endereco/${SSFrete.idEndEntrega}`).then(response => response.json());
    let dadosBoleto = "";
    let dadosCartao = "";
    if (sessionStorage.getItem("dadosBoleto") != null && sessionStorage.getItem("dadosBoleto") != undefined) {
        dadosBoleto = JSON.parse(sessionStorage.getItem("dadosBoleto"));
        /* const dadosBoleto = {
            numero: numeroBoleto,
            vencimento: vencimento,
        }; */
    } else {
        dadosCartao = JSON.parse(sessionStorage.getItem("dadosCartao"));
        /* const dadosCartao = {
            numeroCartao: numCart,
            cvvCartao: cvvCart,
            nomeCartao: nomeCart,
            validadeCartao: vencCart
        }; */
    }
}