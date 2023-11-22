function setCookie(nome, info, exdays) {
    Cookies.set(nome, info, exdays)
}

function getCookie(nome) {
    return Cookies.get(nome)
}

var idCliente = getCookie('sessaoId');
if (idCliente != undefined) {
    fetch(`/carrinho/clean?codCliente=${idCliente}`)
}
