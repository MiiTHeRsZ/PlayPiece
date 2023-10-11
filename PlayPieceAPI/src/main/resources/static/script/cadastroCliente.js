document.getElementById("cpf").onchange = () => {
    verificaInformacao();
}
document.getElementById("email").onchange = () => {
    verificaInformacao();
}
document.getElementById("senha").onchange = () => {
    verificaInformacao();
}
document.getElementById("confirmaSenha").onchange = () => {
    verificaInformacao();
}

function verificaInformacao() {
    let senha = document.getElementById("senha").value
    let confirmaSenha = document.getElementById("confirmaSenha").value
    let cpf = document.getElementById("cpf").value
    let cpfret = validaCPF(cpf)
    let emailret = verificaEmail();

    let ret = verificarSenhas(senha, confirmaSenha)

    // verifica se as senhas informadas são validas ou não
    if (ret) {
        document.getElementById("confirmaSenha").style.border = '2px solid gray'
    } else {
        document.getElementById("confirmaSenha").style.border = '2px solid red'
    }
    // verifica se as informações senha e cpf foram preechidas corratamente ou não para liberar o botao de salvar
    if (ret && cpfret && emailret) {
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
        "nome": document.getElementById("nome").value,
        "cpf": document.getElementById("cpf").value,
        "dt_nascimento": document.getElementById("dt_nasc").value,
        "genero": document.getElementById("genero").value,
        "email": document.getElementById("email").value,
        "enderencoFaturamento": {
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

        alert("Cliente criado com sucesso!\nCriando senha")
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