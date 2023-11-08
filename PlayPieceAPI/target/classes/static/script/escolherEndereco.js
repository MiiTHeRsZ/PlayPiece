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

let dados;

const conectAPI = async () => {
    dados = await fetch(`/cliente/${idCliente}`).then(data => data.json());
    preecheDados();
}
conectAPI();

const preecheDados = async () => {

    dados.listaEndereco.forEach(endereco => {
        let opcao = document.createElement("option");
        opcao.value = endereco.id;
        if (endereco.padrao) {
            opcao.toggleAttribute("selected");
            document.getElementById("cep").value = endereco.cep;
            document.getElementById("logradouro").value = endereco.logradouro;
            document.getElementById("numero").value = endereco.numero;
            document.getElementById("complemento").value = endereco.complemento;
            document.getElementById("bairro").value = endereco.bairro;
            document.getElementById("cidade").value = endereco.cidade;
            document.getElementById("uf").value = endereco.uf;
        }
        if (!endereco.ativo) {
            opcao.toggleAttribute("disabled");
        }
        opcao.textContent = endereco.logradouro + ", N°" + endereco.numero;
        if (endereco.padrao) {
            opcao.textContent += ` \u2B50`
        }
        document.getElementById("enderecoEntrega").appendChild(opcao);
    });

    calcularFrete();
}

document.getElementById("enderecoEntrega").addEventListener("change", () => {
    
    dados.listaEndereco.forEach(endereco => {
        
        if (document.getElementById("enderecoEntrega").value == endereco.id) {
            document.getElementById("cep").value = endereco.cep;
            document.getElementById("logradouro").value = endereco.logradouro;
            document.getElementById("numero").value = endereco.numero;
            document.getElementById("complemento").value = endereco.complemento;
            document.getElementById("bairro").value = endereco.bairro;
            document.getElementById("cidade").value = endereco.cidade;
            document.getElementById("uf").value = endereco.uf;
        }
        
    });

    document.querySelectorAll("input[type='radio']").forEach(async item => {
        if (item.checked) {
            item.checked = false;
            document.getElementById('selecionar-endereco').disabled = true;
        }
    });
    
    calcularFrete();
});

document.getElementById("selecionar-endereco").addEventListener("click", () => {
    const idCep = document.getElementById('enderecoEntrega').value;
    const fretes = document.querySelectorAll('input[type="radio"]');
    let freteEscolhido = 0;

    for (let index = 0; index < fretes.length; index++) {
        if (fretes[index].checked) {
            freteEscolhido = fretes[index].value;
        }
    }

    const dadosFrete = {
        idEndEntrega: idCep,
        valorFrete: freteEscolhido
    }

    const resp = window.confirm("Deseja selecionar o endereço atual?");
    if (resp == 1) {
        sessionStorage.setItem("endEntrega", JSON.stringify(dadosFrete));
        window.open("./escolherPagamento.html", "_self");
    }
});

const botaoNovoEndereco = document.getElementById("novo-endereco");

botaoNovoEndereco.addEventListener("click", () => {
    document.getElementById("endEntrega").style.display = "none"
    document.getElementById("endEntregaNovo").style.display = "block"
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

    document.getElementById("endEntrega").style.display = "block"
    document.getElementById("endEntregaNovo").style.display = "none"

});

async function calcularFrete() {

    const cep = document.getElementById("cep").value;

    if (cep.length == 8) {
        const consultaCep = await fetch(`https://viacep.com.br/ws/${cep}/json/`).then(data => data.json());
        if (consultaCep.erro) {
            alert("CEP inválido!");
        } else {
            let distancia;
            const service = new google.maps.DistanceMatrixService();
            const origin = "04696000";
            const destination = cep;
            service.getDistanceMatrix(
                {
                    origins: [origin],
                    destinations: [destination],
                    travelMode: google.maps.TravelMode.DRIVING,
                    unitSystem: google.maps.UnitSystem.METRIC,
                    avoidHighways: false,
                    avoidTolls: false,
                }, callback);

            async function callback(response, status) {
                if (status == "OK") {
                    distancia = response.rows[0].elements[0].distance.value * .001;

                    const quantidadeProdutos = await fetch(`/carrinho/search?cliente=${idCliente}`).then(data => data.json().itens.length);

                    const frete01 = (quantidadeProdutos * .5 + .91) * distancia;
                    const frete02 = (quantidadeProdutos * .5 + .61) * distancia;
                    const frete03 = (quantidadeProdutos * .5 + .31) * distancia;

                    document.getElementById("frete1").innerHTML = `R$ ${parseFloat(frete01).toFixed(2).replace(".", ",")}`;
                    document.getElementById("frete01").value = parseFloat(frete01).toFixed(2);
                    document.getElementById("frete2").innerHTML = `R$ ${parseFloat(frete02).toFixed(2).replace(".", ",")}`;
                    document.getElementById("frete02").value = parseFloat(frete02).toFixed(2);
                    document.getElementById("frete3").innerHTML = `R$ ${parseFloat(frete03).toFixed(2).replace(".", ",")}`;
                    document.getElementById("frete03").value = parseFloat(frete03).toFixed(2);
                } else {
                    alert("rip")
                }
            }
        }
    } else {
        alert("CEP inválido!");
    }
}

document.getElementById("frete01").addEventListener("change", verificaSelecao);
document.getElementById("frete02").addEventListener("change", verificaSelecao);
document.getElementById("frete03").addEventListener("change", verificaSelecao);

function verificaSelecao() {
    let ver = false;
    document.querySelectorAll("input[type='radio']").forEach(async item => {
        if (item.checked) {
            ver = true;
        }
    });
    if (ver) {
        document.getElementById('selecionar-endereco').disabled = false;
    } else {
        alert('Escolha um dos Fretes!');
    }
}