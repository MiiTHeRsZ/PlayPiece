
pessoa = {
}
contato = {

}
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


let inpSobrenome = document.getElementById("sobrenome")
inpSobrenome.addEventListener("change", async () => {
    emailProfi()
})

async function emailProfi() {
    let users = await fetch("http://localhost:8080/usuario").then(response => response.json())
    let id = 0;
    users.forEach(user => {
        id = Number(user.id) + 1
    })
    let nome1 = document.getElementById("nome").value.charAt(0).toLowerCase()
    let sobre = document.getElementById("sobrenome").value.toLowerCase()
    document.getElementById("email_profissional").value = `${nome1}${sobre}.pp${id}@playpiece.com`
}

let inpCpf = document.getElementById("cpf")
inpCpf.addEventListener("change", async () => {

    document.getElementById("celular_principal").value = null
    document.getElementById("celular_principal").removeAttribute("readonly", false)
    document.getElementById("celular_adicional").value = null
    document.getElementById("celular_adicional").removeAttribute("readonly", false)
    document.getElementById("telefone_fixo").value = null
    document.getElementById("telefone_fixo").removeAttribute("readonly", false)
    document.getElementById("nome").value = null
    document.getElementById("nome").removeAttribute("readonly", false)
    document.getElementById("sobrenome").value = null
    document.getElementById("sobrenome").removeAttribute("readonly", false)
    document.getElementById("email_pessoal").value = null
    document.getElementById("email_pessoal").removeAttribute("readonly", false)
    document.getElementById("cep").value = null
    document.getElementById("cep").removeAttribute("readonly", false)
    document.getElementById("endereco").value = null
    document.getElementById("endereco").removeAttribute("readonly", false)
    document.getElementById("email_profissional").value = null

    let cpf = inpCpf.value
    let result = await fetch(`http://localhost:8080/pessoa/search?cpf=${cpf}`).then(response => response.json())
    if (result[0] != null) {

        contato = {
            "celularPrincipal": result[0].contato.celularPrincipal,
            "celular_adicional": result[0].contato.celular_adicional,
            "telefoneFixo": result[0].contato.telefoneFixo
        }
        pessoa = {
            "id": result[0].id,
            "nome": result[0].nome,
            "cpf": result[0].cpf,
            "email": result[0].email,
            "cep": result[0].cep,
            "endereco": result[0].endereco,
            "contato": contato,
            "fotoPerfil": result[0].fotoPerfil,
            "ativo": result[0].ativo
        }

        let nome = pessoa.nome.split(" ")

        document.getElementById("celular_principal").value = contato.celularPrincipal
        document.getElementById("celular_principal").setAttribute("readonly", true)
        document.getElementById("celular_adicional").value = contato.celular_adicional
        document.getElementById("celular_adicional").setAttribute("readonly", true)
        document.getElementById("telefone_fixo").value = contato.telefoneFixo
        document.getElementById("telefone_fixo").setAttribute("readonly", true)
        document.getElementById("nome").value = nome[0]
        document.getElementById("nome").setAttribute("readonly", true)
        document.getElementById("sobrenome").value = nome[1]
        document.getElementById("sobrenome").setAttribute("readonly", true)
        document.getElementById("cpf").value = pessoa.cpf
        document.getElementById("email_pessoal").value = pessoa.email
        document.getElementById("email_pessoal").setAttribute("readonly", true)
        document.getElementById("cep").value = pessoa.cep
        document.getElementById("cep").setAttribute("readonly", true)
        document.getElementById("endereco").value = pessoa.endereco
        document.getElementById("endereco").setAttribute("readonly", true)

        emailProfi()
    } else {

        contato = {
            "celularPrincipal": null,
            "celular_adicional": null,
            "telefoneFixo": null
        }
        pessoa = {
            "nome": null,
            "cpf": null,
            "email": null,
            "cep": null,
            "endereco": null,
            "contato": null,
            "fotoPerfil": null,
            "ativo": false
        }

    }
})

const botaoSalvar = document.getElementById("btn-salvar");
botaoSalvar.addEventListener("click", async (e) => {
    e.preventDefault()

    let cargo = {
        "id": document.getElementById("cargo").value
    }
    contato = {
        "celularPrincipal": document.getElementById("celular_principal").value,
        "celular_adicional": document.getElementById("celular_adicional").value,
        "telefoneFixo": document.getElementById("telefone_fixo").value
    }

    if (pessoa.id == null) {

        pessoa = {
            "nome": document.getElementById("nome").value + " " + document.getElementById("sobrenome").value,
            "cpf": document.getElementById("cpf").value,
            "email": document.getElementById("email_pessoal").value,
            "cep": document.getElementById("cep").value,
            "endereco": document.getElementById("endereco").value,
            "contato": contato,
            "fotoPerfil": null,
            "ativo": true
        }
    }

    let usuario = {
        "pessoa": pessoa,
        "cargo": cargo,
        "salario": document.getElementById("salario").value,
        "ativo": true,
        "emailUsuario": document.getElementById("email_profissional").value,
    }

    const result = await fetch("http://localhost:8080/usuario", {
        method: "POST",
        headers: {
            'Content-Type':
                'application/json;charset=utf-8'
        },
        body: JSON.stringify(usuario),

    })

    console.log(result.status)

    if (result.status == 201) {

        alert("Usuário criado com sucesso!\nCriando senha")
        const createSenha = await fetch(`http://localhost:8080/acesso/newAccess?emailUsuario=${usuario.emailUsuario}`, {
            method: "POST",
            headers: {
                'Content-Type':
                    'application/json;charset=utf-8'
            },
            body: JSON.stringify(document.getElementById("cpf").value.toString().hashCode()),

        })
        if (createSenha.status == 201) {
            alert("Senha criada com sucesso!")
            window.close()
        } else {
            document.querySelector("body").style = "background-color:#ffcbcb;"
            alert("Falha ao criar senha\nTente novamente")
        }
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