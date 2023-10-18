const urlParams = new URLSearchParams(window.location.search);
email = urlParams.get('email');
let dados;

document.getElementById("logo").href += "?email=" + email;

document.getElementById("sair-perfil").addEventListener("click", (e) => {
    e.preventDefault();

    const resp = window.confirm("Deseja encerrar a sessão?");

    resp == 1 ? window.open("../index.html", "_self") : "";
});

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
            document.getElementById("definir-endereco-padrao").toggleAttribute("disabled");
            document.getElementById("excluir-endereco").toggleAttribute("disabled");
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

            if (endereco.padrao) {
                document.getElementById("definir-endereco-padrao").toggleAttribute("disabled");
                document.getElementById("excluir-endereco").toggleAttribute("disabled");
            } else {
                document.getElementById("definir-endereco-padrao").removeAttribute("disabled");
                document.getElementById("excluir-endereco").removeAttribute("disabled");
            }
        }

    });

});

document.getElementById("alterar-info").addEventListener("click", async (e) => {
    e.preventDefault();

    if (document.getElementById("senha").value === document.getElementById("confirmaSenha").value) {

        let cliente = {
            "cpf": document.getElementById("cpf").value,
            "nome": document.getElementById("nome").value,
            "dt_nascimento": document.getElementById("dt_nasc").value,
            "genero": document.getElementById("genero").value,
            "email": document.getElementById("email").value,
            "enderecoFaturamento": dados.enderecoFaturamento,
            "listaEndereco": dados.listaEndereco,
            "senha": document.getElementById("senha").value.length == 0 ? dados.senha : document.getElementById("senha").value.hashCode(),
            "ativo": true
        }

        const result = await fetch(`/cliente/${document.getElementById("idCliente").innerHTML}`, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(cliente),
        })

        if (result.status == 200 || result.status == 201) {
            alert("Dados atualizados com sucesso!");
        } else {
            alert("Falha ao atualizar os dados\nTente novamente!");
        }
    } else {
        alert("Senhas não coincidem!\nCaso não deseje alterar a senha,\ngaranta que os campos de 'senha' estejam vazios!");
    }
});

document.getElementById("definir-endereco-padrao").addEventListener("click", async (e) => {
    e.preventDefault();

    const result = await fetch(`/endereco/${document.getElementById("enderecoEntrega").value}`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: '',
    })

    if (result.status == 200 || result.status == 201) {
        alert("Endereço padrão definido com sucesso!");
        window.location.reload();
    } else {
        alert("Falha ao definir o endereço padrão\nTente novamente!");
    }
});

document.getElementById("excluir-endereco").addEventListener("click", async (e) => {
    e.preventDefault();

    const result = await fetch(`/endereco/${document.getElementById("enderecoEntrega").value}`, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: '',
    })

    if (result.status == 200 || result.status == 201) {
        alert("Endereço desabilitado com sucesso!");
        window.location.reload();
    } else {
        alert("Falha ao desabilitar o endereço\nTente novamente!");
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