function getCookie(nome) {
    return Cookies.get(nome)
}
function setCookie(nome, info, exdays) {
    Cookies.set(nome, info, exdays)
}

idCliente = getCookie('sessaoId')

const botaoNovoEndereco = document.getElementById("novo-endereco");

botaoNovoEndereco.addEventListener("click", () => {
    document.getElementById("divEndereco").style.display = "none"
    document.getElementById("divEnderecoNovo").style.display = "block"

});

document.getElementById("cep-novo").addEventListener("focusout", () => {
    buscarDadosCep()
})

function getJson(url) {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.onload = function () {
            if (xhr.status >= 200 && xhr.status < 400) {
                resolve(JSON.parse(xhr.responseText));
            } else {
                const erro = {
                    codigo: xhr.status,
                    mensagem: 'Erro na requisição'
                };
                reject(erro);
            }
        };
        xhr.send();
    });
}

async function buscarDadosCep() {
    const cepOK = true
    const endereco = await getJson(`https://viacep.com.br/ws/${document.getElementById("cep-novo").value}/json/`);
    document.getElementById('logradouro-novo').value = endereco.logradouro;
    document.getElementById('logradouro-novo').setAttribute("readonly", "true")
    document.getElementById('bairro-novo').value = endereco.bairro;
    document.getElementById('bairro-novo').setAttribute("readonly", "true")
    document.getElementById('cidade-novo').value = endereco.localidade;
    document.getElementById('cidade-novo').setAttribute("readonly", "true")
    document.getElementById('uf-novo').value = endereco.uf;
    document.getElementById('uf-novo').setAttribute("readonly", "true")
}

document.getElementById("salvar-novo-endereco").addEventListener("click", async (e) => {
    e.preventDefault();

    const cliente = await fetch(`/cliente/${idCliente}`).then((data) => data.json())

    let endereco = {
        "id": 0,
        "idCliente": Number(cliente.id),
        "cep": document.getElementById("cep-novo").value,
        "logradouro": document.getElementById("logradouro-novo").value,
        "numero": Number(document.getElementById("numero-novo").value),
        "complemento": document.getElementById("complemento-novo").value,
        "bairro": document.getElementById("bairro-novo").value,
        "cidade": document.getElementById("cidade-novo").value,
        "uf": document.getElementById("uf-novo").value,
        "padrao": false,
        "ativo": true
    }

    const result = await fetch(`/endereco/${cliente.id}`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(endereco),
    })

    if (result.status == 200 || result.status == 201) {
        alert("Endereço criado com sucesso!");
        window.location.reload();
    } else {
        alert("Falha ao criar o endereço\nTente novamente!");
    }
});

document.getElementById("cancelar-novo-endereco").addEventListener("click", async (e) => {
    e.preventDefault();
    document.getElementById("cep-novo").value = null
    document.getElementById("logradouro-novo").value = null
    document.getElementById("numero-novo").value = null
    document.getElementById("complemento-novo").value = null
    document.getElementById("bairro-novo").value = null
    document.getElementById("cidade-novo").value = null
    document.getElementById("uf-novo").value = null

    document.getElementById("divEndereco").style.display = "block"
    document.getElementById("divEnderecoNovo").style.display = "none"

});