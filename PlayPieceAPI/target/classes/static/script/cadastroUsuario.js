async function getCargos() {
    let responseCargo = await fetch("http://localhost:8080/cargo").then(response => response.json())
    let box = document.getElementById("cargo")
    responseCargo.forEach(cargo => {
        let option = document.createElement("option")
        option.setAttribute("value", `${cargo.id}`)
        option.innerHTML = `${cargo.nome}`
        box.appendChild(option)
    });
}
getCargos()

let inputNome = document.getElementById("nome")
inputNome.addEventListener("change", async () => {
    emailProfi()
})

async function emailProfi() {
    let users = await fetch("http://localhost:8080/usuario").then(response => response.json())
    let id = 0;
    users.forEach(user => {
        id = Number(user.id) + 1
    })
    let arrayNome = document.getElementById("nome").value.split(" ")
    let nome = arrayNome[0].charAt(0).toLowerCase()
    let sobre = arrayNome[arrayNome.length - 1].toLowerCase()
    document.getElementById("email_profissional").value = `${nome}${sobre}.pp${id}@playpiece.com`
}
document.getElementById("senha1").onchange = () => {

    let senha1 = document.getElementById("senha1").value
    let senha2 = document.getElementById("senha2").value
    let cpf = document.getElementById("cpf").value
    let cpfret = validaCPF(cpf)

    let ret = verificarSenhas(senha1, senha2)

    if (ret) {
        document.getElementById("senha2").style.border = '2px solid gray'
    } else {
        document.getElementById("senha2").style.border = '2px solid red'
    }
    if (ret && cpfret) {
        document.getElementById("btn-salvar").removeAttribute("disabled")
    } else {
        document.getElementById("btn-salvar").setAttribute("disabled", "true")
    }

}
document.getElementById("senha2").onchange = () => {

    let senha1 = document.getElementById("senha1").value
    let senha2 = document.getElementById("senha2").value
    let cpf = document.getElementById("cpf").value
    let cpfret = validaCPF(cpf)

    let ret = verificarSenhas(senha1, senha2)

    if (ret) {
        document.getElementById("senha2").style.border = '2px solid gray'
    } else {
        document.getElementById("senha2").style.border = '2px solid red'
    }
    if (ret && cpfret) {
        document.getElementById("btn-salvar").removeAttribute("disabled")
    } else {
        document.getElementById("btn-salvar").setAttribute("disabled", "true")
    }

}

function verificarSenhas(senha1, senha2) {
    if (senha1 !== senha2 || senha2 == null || senha2.length < 8 || senha2.length > 25) {
        return false
    } else {
        return true
    }
}

document.getElementById("cpf").onchange = () => {
    let cpf = document.getElementById("cpf").value
    let ret = validaCPF(cpf)
    let senha1 = document.getElementById("senha1").value
    let senha2 = document.getElementById("senha2").value
    let senharet = verificarSenhas(senha1, senha2)

    if (ret) {
        document.getElementById("cpf").style.border = '2px solid gray'
    } else {
        document.getElementById("cpf").style.border = '2px solid red'
    }

    if (ret && senharet) {
        document.getElementById("btn-salvar").removeAttribute("disabled")
    } else {
        document.getElementById("btn-salvar").setAttribute("disabled", "true")
    }
}

const botaoSalvar = document.getElementById("btn-salvar");
botaoSalvar.addEventListener("click", async (e) => {
    e.preventDefault()

    let cargo = {
        "id": document.getElementById("cargo").value
    }

    let usuario = {
        "nome": document.getElementById("nome").value,
        "cpf": document.getElementById("cpf").value,
        "cargo": cargo,
        "emailUsuario": document.getElementById("email_profissional").value,
        "senha": document.getElementById("senha1").value.hashCode(),
        "ativo": true
    }

    const result = await fetch("http://localhost:8080/usuario", {
        method: "POST",
        headers: {
            'Content-Type':
                'application/json;charset=utf-8'
        },
        body: JSON.stringify(usuario),

    })

    if (result.status == 201) {

        alert("Usuário criado com sucesso!\nCriando senha")
        window.close()
    } else {
        document.querySelector("body").style = "background-color:#ffcbcb;"
        alert("Falha ao cadastrar usuário\nTente novamente")
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