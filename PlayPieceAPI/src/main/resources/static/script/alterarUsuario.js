const urlParams = new URLSearchParams(window.location.search);
idUsuario = urlParams.get('id')
console.log(idUsuario)

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

    console.log(result)
    contato = {
        "celularPrincipal": result.pessoa.contato.celularPrincipal,
        "celular_adicional": result.pessoa.contato.celular_adicional,
        "telefoneFixo": result.pessoa.contato.telefoneFixo
    }
    pessoa = {
        "id": result.pessoa.id,
        "nome": result.pessoa.nome,
        "cpf": result.pessoa.cpf,
        "email": result.pessoa.email,
        "cep": result.pessoa.cep,
        "endereco": result.pessoa.endereco,
        "contato": contato,
        "fotoPerfil": result.pessoa.fotoPerfil,
        "ativo": result.pessoa.ativo
    }

    let nome = pessoa.nome.split(" ")

    document.getElementById("celular_principal").value = contato.celularPrincipal
    document.getElementById("celular_adicional").value = contato.celular_adicional
    document.getElementById("telefone_fixo").value = contato.telefoneFixo
    document.getElementById("nome").value = nome[0]
    document.getElementById("sobrenome").value = nome[1]
    document.getElementById("cpf").value = pessoa.cpf
    document.getElementById("email_pessoal").value = pessoa.email
    document.getElementById("cep").value = pessoa.cep
    document.getElementById("endereco").value = pessoa.endereco
    document.getElementById("email_profissional").value = result.emailUsuario
    document.getElementById("cargo").value = result.cargo.id
    document.getElementById("salario").value = Number.parseFloat(result.salario).toFixed(2)

}

getUser()

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

    let usuario = {
        "pessoa": pessoa,
        "cargo": cargo,
        "salario": document.getElementById("salario").value,
        "ativo": true,
        "emailUsuario": document.getElementById("email_profissional").value,
    }

    const result = await fetch(`http://localhost:8080/usuario/${idUsuario}`, {
        method: "PATCH",
        headers: {
            'Content-Type':
                'application/json;charset=utf-8'
        },
        body: JSON.stringify(usuario),

    })
    if (result.status == 200) {

        alert("Usuário atualizado com sucesso!")
    } else {
        document.querySelector("body").style = "background-color:#ffcbcb;"
        alert("Falha ao atualizar usuário\nTente novamente")
    }

})
