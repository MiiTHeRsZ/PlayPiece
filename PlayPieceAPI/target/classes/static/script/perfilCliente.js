const urlParams = new URLSearchParams(window.location.search);
email = urlParams.get('email');
let dados;

const conectAPI = async () => {
    dados = await fetch(`/cliente/search?email=${email}`).then(data => data.json());
    preecheDados();
}
conectAPI();

const preecheDados = async () => {
    document.getElementById("nomeCliente").innerHTML = dados.nome;
    document.getElementById("idCliente").innerHTML = dados.id;

    document.getElementById("cpf").value = dados.cpf;
    document.getElementById("nome").value = dados.nome;
    document.getElementById("dt_nasc").value = dados.dt_nascimento;
    document.getElementById("genero").value = dados.genero;
    document.getElementById("email").value = dados.email;

    document.getElementById("cepFat").value = dados.enderecoFaturamento.cep;
    document.getElementById("logradouroFat").value = dados.enderecoFaturamento.logradouro;
    document.getElementById("numeroFat").value = dados.enderecoFaturamento.numero;
    document.getElementById("complementoFat").value = dados.enderecoFaturamento.complemento;
    document.getElementById("bairroFat").value = dados.enderecoFaturamento.bairro;
    document.getElementById("cidadeFat").value = dados.enderecoFaturamento.cidade;
    document.getElementById("ufFat").value = dados.enderecoFaturamento.uf;

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
            document.getElementById("excluir-endereco").toggleAttribute("disabled");
            document.getElementById("definir-endereco-padrao").toggleAttribute("disabled")
            document.getElementById("enderecoEntrega").value = endereco.id
        }
        opcao.textContent = endereco.logradouro + ", N°" + endereco.numero + " " + endereco.complemento;
        document.getElementById("enderecoEntrega").appendChild(opcao);

    });
}

function limpaOptions() {
    let options = document.querySelectorAll("#enderecoEntrega option")
    options.forEach(item => {
        item.remove()
    })
}

document.getElementById("enderecoEntrega").addEventListener("change", async () => {

    let endereco = await fetch(`/endereco/${document.getElementById("enderecoEntrega").value}`).then(data => data.json());
    let cliente = await fetch(`/cliente/search?email=${email}`).then(data => data.json());

    if (document.getElementById("enderecoEntrega").value == endereco.id) {
        document.getElementById("cep").value = endereco.cep;
        document.getElementById("logradouro").value = endereco.logradouro;
        document.getElementById("numero").value = endereco.numero;
        document.getElementById("complemento").value = endereco.complemento;
        document.getElementById("bairro").value = endereco.bairro;
        document.getElementById("cidade").value = endereco.cidade;
        document.getElementById("uf").value = endereco.uf;

        if (endereco.padrao) {
            document.getElementById("excluir-endereco").toggleAttribute("disabled");
            document.getElementById("definir-endereco-padrao").toggleAttribute("disabled")
        } else {
            document.getElementById("excluir-endereco").removeAttribute("disabled");
            document.getElementById("definir-endereco-padrao").removeAttribute("disabled");
        }

        // if (document.getElementById("enderecoEntrega").value == cliente.enderecoFaturamento.id) {
        //     document.getElementById("definir-endereco-faturamento").toggleAttribute("disabled")
        // } else {
        //     document.getElementById("definir-endereco-faturamento").removeAttribute("disabled");
        // }
    }
});

document.getElementById("alterar-info").addEventListener("click", async (e) => {
    e.preventDefault();

    let cliente = await fetch(`/cliente/search?email=${email}`).then(data => data.json());
    cliente.nome = document.getElementById("nome").value
    cliente.dt_nascimento = document.getElementById("dt_nasc").value
    cliente.genero = document.getElementById("genero").value

    const result = await fetch(`/cliente/${cliente.id}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(cliente),
    })

    if (result.status == 201) {

        alert("Dados atualizados com sucesso!")
    } else {
        alert("Falha ao atualizar os dados\nTente novamente!")
    }
});

document.getElementById("novo-endereco").addEventListener("click", async (e) => {
    e.preventDefault();

    let endereco = {
        "cep": document.getElementById("cep").value,
        "logradouro": document.getElementById("logradouro").value,
        "numero": document.getElementById("numero").value,
        "complemento": document.getElementById("complemento").value,
        "bairro": document.getElementById("bairro").value,
        "cidade": document.getElementById("cidade").value,
        "uf": document.getElementById("uf").value,
        "padrao": false,
        "ativo": true
    }

    const result = await fetch(`/endereco/${document.getElementById("idCliente").innerHTML}`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(endereco),
    })

    if (result.status == 201) {

        alert("Endereço criado com sucesso!")
    } else {
        alert("Falha ao criar o endereço\nTente novamente!")
    }
});

document.getElementById("definir-endereco-padrao").addEventListener("click", async (e) => {
    e.preventDefault();

    let endereco = {
        "id": document.getElementById("enderecoEntrega").value,
        "padrao": true
    }

    const result = await fetch(`/endereco/${document.getElementById("enderecoEntrega").value}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(endereco),
    })

    if (result.status == 201) {

        alert("Endereço padrão definido com sucesso!")
        limpaOptions()
        conectAPI()
    } else {
        alert("Falha ao definir o endereço padrão\nTente novamente!")
    }
});

// document.getElementById("definir-endereco-faturamento").addEventListener("click", async (e) => {
//     e.preventDefault();

//     let cliente = await fetch(`/cliente/search?email=${email}`).then(data => data.json())
//     console.log(cliente);
//     cliente.enderecoFaturamento.id = document.getElementById("enderecoEntrega").value

//     const result = await fetch(`/cliente/${cliente.id}`, {
//         method: "PUT",
//         headers: {
//             'Content-Type': 'application/json;charset=utf-8'
//         },
//         body: JSON.stringify(cliente),
//     })

//     if (result.status == 201) {

//         alert("Endereço de faturamento definido com sucesso!")
//         limpaOptions()
//         conectAPI()
//     } else {
//         alert("Falha ao definir o endereço de faturamento\nTente novamente!")
//     }
// });

document.getElementById("alterar-senha").addEventListener("click", async (e) => {
    e.preventDefault();

    let cliente = {
        "id": document.getElementById("idCliente").innerHTML,
        "senha": document.getElementById("senha").value.hashCode()
    }

    const result = await fetch("/cliente", {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(cliente),
    })

    if (result.status == 201) {

        alert("Senha atualizada com sucesso!")
        window.close()
    } else {
        alert("Falha ao atualizar a senha\nTente novamente!")
    }
});

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