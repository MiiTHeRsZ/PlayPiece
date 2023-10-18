document.getElementById("cpf").onchange = async () => {
    const checkCpf = await (await fetch(`/cliente/search?cpf=${document.getElementById("cpf").value}`)).status == 200 ? true : false
    if (!checkCpf) {
        document.getElementById("err-cpf").style.display = "none"
        verificaInformacao();
    } else {
        document.getElementById("err-cpf").style.display = "block"
    }
}
document.getElementById("nome").onchange = () => {
    if (!verificaNome()) {
        alert("Preencha o campo 'Nome' com nome e sobrenome\ncontendo ao menos 3 letras cada!.");
    }
    verificaInformacao();
}
document.getElementById("email").onchange = () => {
    verificaInformacao();
}
document.getElementById("genero").onchange = () => {
    verificaInformacao();
}
document.getElementById("cep").onchange = () => {
    buscarDadosCep();
}
document.getElementById("senha").onchange = () => {
    verificaInformacao();
}
document.getElementById("confirmaSenha").onchange = () => {
    verificaInformacao();
}

var hojeSemForm = new Date();
const hoje = {
    "dia": hojeSemForm.getDate() < 10 ? "0" + hojeSemForm.getDate() : hojeSemForm.getDate(),
    "mes": Number(hojeSemForm.getMonth() < 10 ? "0" + hojeSemForm.getMonth() : hojeSemForm.getMonth()) + 1,
    "ano": hojeSemForm.getFullYear(),
    "string": ""
}
hoje.string = hoje.ano + "-" + hoje.mes + "-" + hoje.dia

document.getElementById("dt_nasc").setAttribute("max", hoje.string)

function verificaInformacao() {
    let senha = document.getElementById("senha").value
    let confirmaSenha = document.getElementById("confirmaSenha").value
    let nomeret = verificaNome();
    let cpf = document.getElementById("cpf").value
    let cpfret = validaCPF(cpf)
    let emailret = verificaEmail();
    let genero = document.getElementById("genero").value;
    document.getElementById("senha").value.hashCode()

    let ret = verificarSenhas(senha, confirmaSenha)

    // verifica se as senhas informadas são validas ou não
    if (ret) {
        document.getElementById("confirmaSenha").style.border = '2px solid gray'
    } else {
        document.getElementById("confirmaSenha").style.border = '2px solid red'
    }

    // verifica se as informações senha e cpf foram preechidas corratamente ou não para liberar o botao de salvar
    if (ret && nomeret && cpfret && emailret && genero != 0) {
        document.getElementById("btn-salvar").removeAttribute("disabled")
        document.getElementById("btn-salvar").style.cursor = 'pointer'
    } else {
        document.getElementById("btn-salvar").setAttribute("disabled", "true")
    }
}

async function verificaEmail() {
    let email = document.getElementById("email").value;

    await fetch(`/cliente/search?email=${email}`).then((response) => {
        const alert = document.querySelector(".mail");
        if (response.status == 404) {
            alert.style.display = "none"
            alert.innerHTML = ""
            return false;
        } else {
            const alert = document.querySelector(".mail");
            alert.style.display = "inline"
            alert.innerHTML = "E-mail já cadastrado"
            return true;
        }
    })
}

let showPassIcon = document.querySelector("#showPassword")
showPassIcon.addEventListener("click", () => {
    if (showPassIcon.getAttribute("class") == "fa-solid fa-eye-slash") {
        showPassIcon.removeAttribute("class")
        showPassIcon.setAttribute("class", "fa-solid fa-eye")
        let passInput = document.querySelectorAll(".passInput")
        for (let i = 0; i < passInput.length; i++) {
            passInput[i].setAttribute("type", "text")
        }

    }
    else {
        showPassIcon.removeAttribute("class")
        showPassIcon.setAttribute("class", "fa-solid fa-eye-slash")
        let passInput = document.querySelectorAll(".passInput")
        for (let i = 0; i < passInput.length; i++) {
            passInput[i].setAttribute("type", "password")
        }
    }
})

const botaoSalvar = document.getElementById("btn-salvar");
botaoSalvar.addEventListener("click", async (e) => {
    e.preventDefault()

    let cliente = {
        "cpf": document.getElementById("cpf").value,
        "nome": document.getElementById("nome").value,
        "dt_nascimento": document.getElementById("dt_nasc").value,
        "genero": document.getElementById("genero").value,
        "email": document.getElementById("email").value,
        "enderecoFaturamento": {
            "cep": document.getElementById("cep").value,
            "logradouro": document.getElementById("logradouro").value,
            "numero": document.getElementById("numero").value,
            "complemento": document.getElementById("complemento").value,
            "bairro": document.getElementById("bairro").value,
            "cidade": document.getElementById("cidade").value,
            "uf": document.getElementById("uf").value,
            "padrao": true,
            "ativo": true
        },
        "senha": document.getElementById("senha").value.hashCode(),
        "ativo": true
    }
    const result = await fetch("/cliente", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(cliente),
    })

    if (result.status == 201) {

        alert("Cliente criado com sucesso!")
        window.close()
    } else {
        document.querySelector("body").style = "background-color:#ffcbcb;"
        alert("Falha ao cadastrar cliente\nTente novamente")
    }
})

String.prototype.hashCode = function () {
    var hash = 0,
        i, chr;
    if (this.length === 0) return hash;
    for (i = 0; i < this.length; i++) {
        chr = this.charCodeAt(i);
        hash = ((hash << 5) - hash) + chr;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
}

function verificarSenhas(senha, confirmaSenha) {
    let alert = document.querySelector(".alert")
    if (senha !== confirmaSenha || confirmaSenha == null || confirmaSenha.length < 8 || confirmaSenha.length > 25) {
        if (confirmaSenha == "") {
        }
        else if (senha !== confirmaSenha) {
            alert.style.display = "inline"
            alert.innerHTML = "Senhas não batem"
        } else if (confirmaSenha.length < 8 || confirmaSenha.length > 25) {
            alert.style.display = "inline"
            alert.innerHTML = "Senha deve conter de 8 até 25 caracteres"
        }
        return false
    } else {
        alert.innerHTML = ""
        alert.style.display = "none"
        return true
    }
}

function validaCPF(cpf) {
    var Soma = 0
    var Resto

    var strCPF = String(cpf).replace(/[^\d]/g, '')

    if (strCPF.length !== 11)
        return false

    if ([
        '00000000000',
        '11111111111',
        '22222222222',
        '33333333333',
        '44444444444',
        '55555555555',
        '66666666666',
        '77777777777',
        '88888888888',
        '99999999999',
    ].indexOf(strCPF) !== -1)
        return false

    for (i = 1; i <= 9; i++)
        Soma = Soma + parseInt(strCPF.substring(i - 1, i)) * (11 - i);

    Resto = (Soma * 10) % 11

    if ((Resto == 10) || (Resto == 11))
        Resto = 0

    if (Resto != parseInt(strCPF.substring(9, 10)))
        return false

    Soma = 0

    for (i = 1; i <= 10; i++)
        Soma = Soma + parseInt(strCPF.substring(i - 1, i)) * (12 - i)

    Resto = (Soma * 10) % 11

    if ((Resto == 10) || (Resto == 11))
        Resto = 0

    if (Resto != parseInt(strCPF.substring(10, 11)))
        return false

    return true
}

function getJson(url) {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.onload = function () {
            if (xhr.status >= 200 && xhr.status < 400) {
                resolve(JSON.parse(xhr.responseText));
            } else {
                const erro = {
                    "codigo": xhr.status,
                    "mensagem": 'Erro na requisição'
                };
                reject(erro);
            }
        };
        xhr.send();
    });
}

function getValidCep() {
    return new Promise((resolve, reject) => {
        const numeroCep = document.getElementById("cep").value;

        if (numeroCep.length === 8) {
            resolve(numeroCep);
        } else {
            reject(new Error("CEP inválido"));
        }
    });
}

function verificaNome() {
    let nome = document.getElementById("nome").value.split(" ");
    let nomeValido;
    nome.forEach(palavra => {
        if (palavra.length < 3 || nome.length < 2 || nomeValido == false) {
            nomeValido = false;
        } else {
            nomeValido = true;
        }
    });
    return nomeValido;
}

async function buscarDadosCep() {
    try {
        const alert = document.querySelector(".alert-cep");

        const valorCep = await getValidCep();
        const dadosArray = await getJson(`https://viacep.com.br/ws/${valorCep}/json/`);
        if (dadosArray.erro === true) {
            alert.innerHTML = "CEP inválido!";
            alert.style.display = "inline";
        } else {
            alert.innerHTML = "";
            alert.style.display = "none";
            document.getElementById('logradouro').value = dadosArray.logradouro;
            document.getElementById('bairro').value = dadosArray.bairro;
            document.getElementById('cidade').value = dadosArray.localidade;
            document.getElementById('uf').value = dadosArray.uf;
        }
    } catch (erro) {
        console.log("Erro na busca do CEP: " + erro.message + "\nVerifique se o CEP fornecido é realmente válido");
    }
}

// {
//     "cep": "04775-170",
//     "logradouro": "Rua Waldemar Gomes Lingoanoti",
//     "complemento": "",
//     "bairro": "Jardim Marabá(Zona Sul)",
//     "localidade": "São Paulo",
//     "uf": "SP",
//     "ibge": "3550308",
//     "gia": "1004",
//     "ddd": "11",
//     "siafi": "7107"
// }