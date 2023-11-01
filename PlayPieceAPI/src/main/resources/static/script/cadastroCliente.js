
function menu() {
    let carrinho = sessionStorage.getItem('carrinho');

    if (carrinho != "" && carrinho != null && carrinho != undefined) {
        let cont = 0;

        if (carrinho.length > 3) {
            carrinho.split(",").forEach(item => {
                cont++;
            });
        } else {
            cont++;
        }

        document.getElementById("notificacaoCarrinho").innerHTML = cont;
        document.getElementById("notificacaoCarrinho").style.display = "inline";
    }
}
menu();

var jaCpf = null

document.getElementById("cpf").onchange = async (e) => {
    const checkCpf = await (await fetch(`/cliente/search?cpf=${document.getElementById("cpf").value}`)).status == 200 ? true : false
    if (!checkCpf) {
        document.getElementById("err-cpf").style.display = "none"
        verificaInformacao(e.target.id);
        jaCpf = false
    } else {
        document.getElementById("err-cpf").innerHTML = "CPF já cadastrado"
        document.getElementById("cpf").classList.add('fail')
        document.getElementById("err-cpf").style.display = "block"
        jaCpf = true
    }
}
document.getElementById("nome").onchange = (e) => {
    if (!verificaNome(e.target.id)) {
        alert("Preencha o campo 'Nome' com nome e sobrenome\ncontendo ao menos 3 letras cada!.");
        verificaInformacao();
    }
}
document.getElementById("email").onchange = (e) => {
    if (e.target.value !== "") {
        verificaInformacao(e.target.id);
    }
}
document.getElementById("genero").onchange = (e) => {
    if (e.target.value !== "") {
        verificaInformacao(e.target.id);
    }
}
document.getElementById("cep-faturamento").onchange = () => {
    buscarDadosCep("cep-faturamento");
}
document.getElementById("cep-entrega").onchange = () => {
    buscarDadosCep("cep-entrega");
}
document.getElementById("senha").onchange = (e) => {
    if (e.target.value !== "") {
        verificaInformacao(e.target.id);
    }
}
document.getElementById("confirmaSenha").onchange = (e) => {
    if (e.target.value !== "") {
        verificaInformacao(e.target.id);
    }
}

document.getElementById("isEntrega").addEventListener("change", () => {
    if (document.getElementById("isEntrega").checked) {
        document.getElementById("cep-entrega").value = document.getElementById("cep-faturamento").value
        document.getElementById("cep-entrega").setAttribute("readonly", "true")
        document.getElementById("logradouro-entrega").value = document.getElementById("logradouro-faturamento").value
        document.getElementById("logradouro-entrega").setAttribute("readonly", "true")
        document.getElementById("numero-entrega").value = document.getElementById("numero-faturamento").value
        document.getElementById("numero-entrega").setAttribute("readonly", "true")
        document.getElementById("complemento-entrega").value = document.getElementById("complemento-faturamento").value
        document.getElementById("complemento-entrega").setAttribute("readonly", "true")
        document.getElementById("bairro-entrega").value = document.getElementById("bairro-faturamento").value
        document.getElementById("bairro-entrega").setAttribute("readonly", "true")
        document.getElementById("cidade-entrega").value = document.getElementById("cidade-faturamento").value
        document.getElementById("cidade-entrega").setAttribute("readonly", "true")
        document.getElementById("uf-entrega").value = document.getElementById("uf-faturamento").value
        document.getElementById("uf-entrega").setAttribute("readonly", "true")
    } else {
        document.getElementById("cep-entrega").value = ""
        document.getElementById("cep-entrega").removeAttribute("readonly")
        document.getElementById("logradouro-entrega").value = ""
        document.getElementById("numero-entrega").value = ""
        document.getElementById("numero-entrega").removeAttribute("readonly")
        document.getElementById("complemento-entrega").value = ""
        document.getElementById("complemento-entrega").removeAttribute("readonly")
        document.getElementById("bairro-entrega").value = ""
        document.getElementById("cidade-entrega").value = ""
        document.getElementById("uf-entrega").value = ""
    }
})

var hojeSemForm = new Date();
const hoje = {
    "dia": hojeSemForm.getDate() < 10 ? "0" + hojeSemForm.getDate() : hojeSemForm.getDate(),
    "mes": Number(hojeSemForm.getMonth() < 10 ? "0" + hojeSemForm.getMonth() : hojeSemForm.getMonth()) + 1,
    "ano": hojeSemForm.getFullYear(),
    "string": ""
}
hoje.string = hoje.ano + "-" + hoje.mes + "-" + hoje.dia

document.getElementById("dt_nasc").setAttribute("max", hoje.string)

async function verificaInformacao(input) {
    let senha = document.getElementById("senha").value
    let confirmaSenha = document.getElementById("confirmaSenha").value
    let nomeret = verificaNome();
    let cpf = document.getElementById("cpf").value
    let cpfret = validaCPF(cpf)
    let emailret = await verificaEmail();
    let genero = document.getElementById("genero").value;
    document.getElementById("senha").value

    let ret = verificarSenhas(senha, confirmaSenha)

    // verifica se as senhas informadas são validas ou não
    if (ret) {
        document.getElementById("confirmaSenha").classList.remove("fail")
    } else {
        document.getElementById("confirmaSenha").classList.add('fail')
    }

    if (cpfret && !jaCpf) {
        document.getElementById("cpf").classList.remove("fail")
        document.getElementById("err-cpf").style.display = "none"
    } else {
        document.getElementById("cpf").classList.add('fail')
        if (!jaCpf) {
            document.getElementById("err-cpf").innerHTML = "CPF inválido"
        }
        document.getElementById("err-cpf").style.display = "inline"
    }

    if (emailret) {
        document.getElementById("email").classList.remove("fail")
    } else {
        document.getElementById("email").classList.add('fail')
    }

    // verifica se as informações senha e cpf foram preechidas corratamente ou não para liberar o botao de salvar
    if (ret && nomeret && cpfret && emailret && genero != 0) {
        document.getElementById("btn-salvar").removeAttribute("disabled")
        document.getElementById("btn-salvar").style.cursor = 'pointer'
    } else {
        document.getElementById("btn-salvar").setAttribute("disabled", "true")
        document.getElementById("btn-salvar").style.cursor = 'not-allowed'
    }
}

async function verificaEmail() {
    let email = document.getElementById("email").value;
    let comp = null

    comp = await fetch(`/cliente/search?email=${email}`).then((data) => {
        const alert = document.querySelector(".mail");
        if (data.status == 404) {
            alert.style.display = "none"
            return false
        } else {
            const alert = document.querySelector(".mail");
            alert.style.display = "inline"
            return data.json();
        }
    })

    if (email == comp.email)
        return false

    return true
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
            "cep": document.getElementById("cep-faturamento").value,
            "logradouro": document.getElementById("logradouro-faturamento").value,
            "numero": document.getElementById("numero-faturamento").value,
            "complemento": document.getElementById("complemento-faturamento").value,
            "bairro": document.getElementById("bairro-faturamento").value,
            "cidade": document.getElementById("cidade-faturamento").value,
            "uf": document.getElementById("uf-faturamento").value,
            "padrao": true,
            "ativo": true
        },
        "senha": document.getElementById("senha").value,
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
        location.href = "./loginCliente.html"
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
    let alert = document.querySelector(".alert-senha")
    if (senha !== confirmaSenha || confirmaSenha == "" || confirmaSenha.length < 8 || confirmaSenha.length > 25) {
        if (confirmaSenha == "" || senha == "") {
            return true
        }
        else if (senha !== confirmaSenha) {
            alert.style.display = "inline"
            alert.innerHTML = "Senhas não batem"
            return false
        } else if (confirmaSenha.length < 8 || confirmaSenha.length > 25) {
            alert.style.display = "inline"
            alert.innerHTML = "Senha deve conter de 8 até 25 caracteres"
            return false
        }
    } else {
        alert.innerHTML = ""
        alert.style.display = "none"
        return true
    }
}

function validaCPF(cpf) {
    if (cpf == "") {
        return true
    }
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


// // nova função assincrona getJson
// async function getJson(url) {
//     fetch(url)
//         .then(response => {
//             const statusCode = response.status;
//             if (response.ok) {
//                 return response.json();
//             } else {
//                 throw new Error("Erro na requisição. Código de Status: " + statusCode);
//             }
//         })
//         .then(responseData => {
//             console.log(responseData);
//         })
//         .catch(error => {
//             console.error(error);
//         });
// }

// função getJson antiga
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
                }
                reject(erro);
            }
        }
        xhr.send();
    });
}

function getValidCep(input) {
    return new Promise((resolve, reject) => {
        const numeroCep = document.getElementById(input).value;
        console.log(numeroCep);

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

async function buscarDadosCep(input) {
    try {
        const alert = document.querySelector(`.alert-${input}`);
        if (input === "cep-faturamento") {
            const valorCep = await getValidCep(input);
            console.log(valorCep);
            const dadosArray = await getJson(`https://viacep.com.br/ws/${valorCep}/json/`);
            if (dadosArray.erro === true) {
                alert.innerHTML = "CEP inválido!";
                alert.style.display = "inline";
            } else {
                alert.innerHTML = "";
                alert.style.display = "none";
                document.getElementById('logradouro-faturamento').value = dadosArray.logradouro;
                document.getElementById('bairro-faturamento').value = dadosArray.bairro;
                document.getElementById('cidade-faturamento').value = dadosArray.localidade;
                document.getElementById('uf-faturamento').value = dadosArray.uf;
            }
        } else if (input === "cep-entrega") {
            valorCep = await getValidCep(input);
            dadosArray = await getJson(`https://viacep.com.br/ws/${valorCep}/json/`);
            if (dadosArray.erro === true) {
                alert.innerHTML = "CEP inválido!";
                alert.style.display = "inline";
            } else {
                alert.innerHTML = "";
                alert.style.display = "none";
                document.getElementById('logradouro-entrega').value = dadosArray.logradouro;
                document.getElementById('bairro-entrega').value = dadosArray.bairro;
                document.getElementById('cidade-entrega').value = dadosArray.localidade;
                document.getElementById('uf-entrega').value = dadosArray.uf;
            }
        }


    } catch (erro) {
        console.log("Erro na busca do CEP: " + erro.message + "\nVerifique se o CEP fornecido é realmente válido");
    }
}
