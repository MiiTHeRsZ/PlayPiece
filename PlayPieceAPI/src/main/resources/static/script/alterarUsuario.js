// responsavel por coletar o id do usuario de url aberta
const urlParams = new URLSearchParams(window.location.search);
idUsuario = urlParams.get('id')

// função responsavel por listar os cargos existentes
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
getCargos();


// função responsavel por listar os usuarios existentes
async function getUser() {

    // busca os dados de um usuario em especifico e converte em JSON
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
    // varieavel que coleta o valor armazenado dentro do input que tem o id cpf
    let cpf = document.getElementById("cpf").value
    //variavel responsavel por chamar a função que valida o cpf
    let ret = validaCPF(cpf)

    // permissão para salvar o não o cpf alterado
    if (ret) {
        document.getElementById("cpf").style.border = '2px solid gray'
        document.getElementById("btn-salvar").removeAttribute("disabled")
    } else {
        document.getElementById("cpf").style.border = '2px solid red'
        document.getElementById("btn-salvar").setAttribute("disabled", "true")
    }
}

// evento ao clicar no botão salvar. Salva todas as informações que foram alteradas. com exceção do email que não pode ser alterado
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

// função responsavel por validar se o CPF fornecido é válido ou não
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

//=====================================================================================
//========================|| alteração de senha ||=====================================
//=====================================================================================

let altSenha = document.getElementById("alterarSenha")
let overlay = document.querySelector(".overlay") // bloqueia a navegação no "fundo" (tela de alterar usuario)
altSenha.addEventListener("click", async (e) => {
    e.preventDefault() // responsavel por não recarregar a pagina
    let senhaPage = document.getElementById("senhaPage")
    if (senhaPage.style.display === "none") {
        senhaPage.style.display = "block"
        overlay.style.display = "block"
    } else {
        senhaPage.style.display = "none"
        overlay.style.display = "none"
    }
})

let btnCancel = document.getElementById("btn-cancelarSenha")
btnCancel.onclick = () => {
    document.getElementById("senha").value = ""
    document.getElementById("confirmar_senha").value = ""
    document.querySelector(".alert").innerHTML = ""
    senhaPage.style.display = "none"
    overlay.style.display = "none"
}

document.getElementById("senha").onchange = () => {

    let senha1 = document.getElementById("senha").value
    let senha2 = document.getElementById("confirmar_senha").value

    let ret = verificarSenhas(senha1, senha2)

    if (ret) {
        document.getElementById("confirmar_senha").style.border = '2px solid gray'
        document.getElementById("btn-salvarSenha").removeAttribute("disabled")
    } else {
        document.getElementById("confirmar_senha").style.border = '2px solid red'
        document.getElementById("btn-salvarSenha").setAttribute("disabled", "true")
    }

}
document.getElementById("confirmar_senha").onchange = () => {

    let senha1 = document.getElementById("senha").value
    let senha2 = document.getElementById("confirmar_senha").value

    let ret = verificarSenhas(senha1, senha2)

    if (ret) {
        document.getElementById("confirmar_senha").style.border = '2px solid gray'
        document.getElementById("btn-salvarSenha").removeAttribute("disabled")
    } else {
        document.getElementById("confirmar_senha").style.border = '2px solid red'
        document.getElementById("btn-salvarSenha").setAttribute("disabled", "true")
    }

}

// função responsavel por verificar e validar a senha
function verificarSenhas(senha1, senha2) {
    let alert = document.querySelector(".alert")
    if (senha1 !== senha2 || senha2 == null || senha2.length < 8 || senha2.length > 25) {
        if (senha2 == "") {
        } else if (senha1 !== senha2) {
            alert.style.display = "inline"
            alert.innerHTML = "Senhas não batem!!"
        } else if (senha2.length < 8 || senha2.length > 25) {
            alert.style.display = "inline"
            alert.innerHTML = "Senha deve conter de 8 até 25 caracteres!!"
        }
        return false
    } else {
        alert.innerHTML = ""
        alert.style.display = "none"
        return true
    }
}

// botão que salva a alteração da senha na tela "alterar senha"
let botao = document.getElementById("btn-salvarSenha")
botao.addEventListener("click", async (e) => {
    e.preventDefault()

    let senha1 = document.getElementById("senha").value
    let senha2 = document.getElementById("confirmar_senha").value


    if (senha1.value !== senha2.value) {
        alert("Senhas não batem")
    } else {
        const user = await fetch(`http://localhost:8080/usuario/${idUsuario}`).then(data => data.json())
        let senhaCripto = senha1.hashCode()

        usuario = {
            "id": user.id,
            "nome": user.nome,
            "cpf": user.cpf,
            "cargo": user.cargo,
            "emailUsuario": user.emailUsuario,
            "senha": senhaCripto,
            "ativo": user.ativo
        }

        const result = await fetch(`http://localhost:8080/usuario/${idUsuario}`, {
            method: "PUT", headers: {
                'Content-Type':
                    'application/json;charset=utf-8'
            },
            body: JSON.stringify(usuario),
        })
        if (result.status == 200) {

            alert("Senha atualizada com sucesso!")
            senhaPage.style.display = "none"
            overlay.style.display = "none"
        } else {
            document.querySelector("body").style = "background-color:#ffcbcb;"
            alert("Falha ao atualizar senha\nTente novamente")
        }
    }
})

// ícone do olho, responsavel por deixar a senha oculta ou não
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

// função responsavel por criar um hashcode da senha fornecida pelo usuario
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
