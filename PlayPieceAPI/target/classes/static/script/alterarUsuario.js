const urlParams = new URLSearchParams(window.location.search);
idUsuario = urlParams.get('id')

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

async function getUser() {

    const result = await fetch(`http://localhost:8080/usuario/${idUsuario}`).then(response => response.json())

    usuario = {
        "id": result.id,
        "nome": result.nome,
        "cpf": result.cpf,
        "cargo": result.cargo,
        "emailUsuario": result.emailUsuario,
        "senha": result.senha,
        "ativo": result.ativo
    }
    document.getElementById("nome").value = result.nome
    document.getElementById("cpf").value = result.cpf
    document.getElementById("email_profissional").value = result.emailUsuario
    document.getElementById("cargo").value = result.cargo.id
}

getUser()

document.getElementById("cpf").onchange = () => {
    let cpf = document.getElementById("cpf").value
    let ret = validaCPF(cpf)

    if (ret) {
        document.getElementById("cpf").style.border = '2px solid gray'
        document.getElementById("btn-salvar").removeAttribute("disabled")
    } else {
        document.getElementById("cpf").style.border = '2px solid red'
        document.getElementById("btn-salvar").setAttribute("disabled", "true")
    }
}

const botaoSalvar = document.getElementById("btn-salvar");
botaoSalvar.addEventListener("click", async (e) => {
    e.preventDefault()

    let cargo = {
        "id": document.getElementById("cargo").value
    }

    usuario = {
        "id": usuario.id,
        "nome": document.getElementById("nome").value,
        "cpf": document.getElementById("cpf").value,
        "cargo": cargo,
        "emailUsuario": usuario.emailUsuario,
        "senha": usuario.senha,
        "ativo": usuario.ativo
    }

    const result = await fetch(`http://localhost:8080/usuario/${idUsuario}`, {
        method: "PUT",
        headers: {
            'Content-Type':
                'application/json;charset=utf-8'
        },
        body: JSON.stringify(usuario),

    })
    if (result.status == 200) {

        alert("Usuário atualizado com sucesso!")
        window.close()
    } else {
        document.querySelector("body").style = "background-color:#ffcbcb;"
        alert("Falha ao atualizar usuário\nTente novamente")
    }

})

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
