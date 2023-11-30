function getCookie(nome) {
    return Cookies.get(nome)
}
function setCookie(nome, info, exdays) {
    Cookies.set(nome, info, exdays)
}

group = getCookie("cargo")
jobSessao = getCookie("jobSession")

checkSessao()

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

let estoq = () => {
    let cont = 1
    let code = "2";
    for (cont = 1; cont <= 16; cont++) {
        code = String(code).hashCode()
    }
    return code
}

// group 2 => estoquista
if (group == estoq()) {
    document.getElementById("mostrarTbUsuarios").style.display = "none"
    document.getElementById("btnCadastroUsuario").style.display = "none"
    document.getElementById("btnCadastroProduto").style.display = "none"
}

// botão que mostra/ oculta a tabela de usuarios
document.getElementById("mostrarTbUsuarios").addEventListener("click", () => {
    checkSessao()
    const tabela = document.getElementById("secaoTabelaUsuario");
    const tabelaProduto = document.getElementById("secaoTabelaProduto");
    const tabelaPedido = document.getElementById("secaoTabelaPedido");

    if (tabelaProduto.style.display == "block" || tabelaPedido.style.display == "block") {
        tabelaProduto.style.display = "none";
        tabelaPedido.style.display = "none";
    }
    if (tabela.style.display == "none") {
        createTbUsers();
        tabela.style.display = "block";
    } else {
        tabela.style.display = "none"
    }
});

async function createTbUsers() {

    const response = await fetch("/usuario").then((data) =>
        data.json()
    );

    clearTable()

    const tabela = document.getElementById("tabelaUser");

    //Criando os campos da tabela usuario
    for (var i = 0; i < response.length; i++) {

        let usuario = {
            "Id": response[i].usuarioId,
            "Nome": response[i].nome,
            "Email": response[i].emailUsuario,
            "Status": response[i].ativo,
            "Grupo": response[i].cargo.nome
        }

        let tr = document.createElement("tr")
        tr.setAttribute("class", "linhaTabela")
        tabela.appendChild(tr)

        let nome = document.createElement("td")
        let email = document.createElement("td")
        let status = document.createElement("td")
        let grupo = document.createElement("td")
        let alterar = document.createElement("td")

        nome.textContent = `${usuario.Nome}`
        email.textContent = `${usuario.Email}`
        status.innerHTML = `<button class = "changeStatusButton" id="changeStatusButton" value=${usuario.Id}> ${usuario.Status ? "Ativo" : "Inativo"} </button>`
        grupo.textContent = `${usuario.Grupo}`
        alterar.innerHTML = `<a onclick="window.open('../pages/alterarUsuario.html?id=${usuario.Id}','name','width=1500,height=1000')"> <button class = "alterarUsuario"> alterar </button> <a>`

        tr.appendChild(nome)
        tr.appendChild(email)
        tr.appendChild(status)
        tr.appendChild(grupo)
        tr.appendChild(alterar)
    }

    alterarStatus()

}

// botão que mostra/oculta a tabela de produtos
document.getElementById("mostrarTbProdutos").addEventListener("click", () => {
    checkSessao()
    const tabela = document.getElementById("secaoTabelaProduto");
    const tabelaUsuario = document.getElementById("secaoTabelaUsuario");
    const tabelaPedido = document.getElementById("secaoTabelaPedido");

    if (tabelaUsuario.style.display == "block" || tabelaPedido.style.display == "block") {
        tabelaUsuario.style.display = "none";
        tabelaPedido.style.display = "none";
    }
    if (tabela.style.display == "none") {
        createTbProducts();
        tabela.style.display = "block";
    } else {
        tabela.style.display = "none"
    }
});

// função responsavel por "criar" a tabela de produtos, onde todas as colunas ficarão amostra
async function createTbProducts() {

    const response = await fetch("/produto").then((data) =>
        data.json()
    );

    clearTable()

    const tabela = document.getElementById("tabelProduct");

    //Criando os campos da tabela produto
    for (var i = 0; i < response.length; i++) {

        let produto = {
            "Id": response[i].produtoId,
            "Nome": response[i].nome,
            "Avaliacao": response[i].avaliacao,
            "Descricao": response[i].descricao,
            "Preco": response[i].preco,
            "Quantidade": response[i].quantidade,
            "Status": response[i].ativo
        }

        let tr = document.createElement("tr")
        tr.setAttribute("class", "linhaTabela")
        tabela.appendChild(tr)

        let nome = document.createElement("td")
        let avaliacao = document.createElement("td")
        let preco = document.createElement("td")
        let quantidade = document.createElement("td")
        let status = document.createElement("td")
        let alterar = document.createElement("td")

        nome.innerHTML = `<a href="./produto.html?id=${produto.Id}" target="_blank">${produto.Nome}</a>`
        avaliacao.textContent = `${produto.Avaliacao}`
        preco.textContent = `${parseFloat(produto.Preco).toFixed(2).replace(".", ",")}`
        quantidade.textContent = `${produto.Quantidade}`
        status.innerHTML = `<button class = "changeStatusButton" value=${produto.Id}> ${produto.Status ? "Ativo" : "Inativo"} </button>`
        alterar.innerHTML = `<a onclick="window.open('../pages/alterarProduto.html?id=${produto.Id}&group=${group}','name','width=1500,height=1000')"> <button class = "alterarProduto"> alterar </button> <a>`
        if (group == 2) {
            status.firstChild.setAttribute("disabled", "true")
        }

        tr.appendChild(nome)
        tr.appendChild(avaliacao)
        tr.appendChild(preco)
        tr.appendChild(quantidade)
        tr.appendChild(status)
        tr.appendChild(alterar)
    }
    alterarStatus()
}

// Responsavel por filtrar a lista de usuários por Nome 
document.getElementById("pesquisarPorNome").addEventListener("click", () => {
    checkSessao()
    const nome = document.getElementById("txtNome").value;
    const tabela = document.getElementById("secaoTabelaUsuario");
    clearTable()
    pesquisarPorNome(nome);
    tabela.style.display = "block";
});

async function pesquisarPorNome(nome) {
    const response = await fetch(`/usuario/search?nome=${nome}`).then((data) =>
        data.json()
    );

    const tabela = document.getElementById("tabelaUser");

    //Criando os campos da tabela usuario
    for (var i = 0; i < response.length; i++) {
        let usuario = {
            "Id": response[i].usuarioId,
            "Nome": response[i].nome,
            "Email": response[i].emailUsuario,
            "Status": response[i].ativo,
            "Grupo": response[i].cargo.nome
        }

        let tr = document.createElement("tr")
        tr.setAttribute("class", "linhaTabela")
        tabela.appendChild(tr)

        let nome = document.createElement("td")
        let email = document.createElement("td")
        let status = document.createElement("td")
        let grupo = document.createElement("td")
        let alterar = document.createElement("td")

        nome.textContent = `${usuario.Nome}`
        email.textContent = `${usuario.Email}`
        status.innerHTML = `<button class = "changeStatusButton" value=${usuario.Id}> ${usuario.Status ? "Ativo" : "Inativo"} </button>`
        grupo.textContent = `${usuario.Grupo}`
        alterar.innerHTML = `<a onclick="window.open('../pages/alterarUsuario.html?id=${usuario.Id}','name','width=1500,height=1000')"> <button class = "alterarUsuario"> alterar </button> <a>`

        tr.appendChild(nome)
        tr.appendChild(email)
        tr.appendChild(status)
        tr.appendChild(grupo)
        tr.appendChild(alterar)
    }

    alterarStatus()
}

/* Filtrar por Nome - Produto */

document.getElementById("pesquisarPorNomeProduto").addEventListener("click", () => {
    checkSessao()
    const nome = document.getElementById("txtNomeProduto").value;
    const tabela = document.getElementById("secaoTabelaProduto");
    clearTable()
    pesquisarPorNomeProduto(nome);
    tabela.style.display = "block";
});

async function pesquisarPorNomeProduto(nome) {

    const response = await fetch(`/produto/search?nome=${nome}`).then((data) =>
        data.json()
    );

    const tabela = document.getElementById("tabelProduct");

    //Criando os campos da tabela produto
    for (var i = 0; i < response.length; i++) {

        let produto = {
            "Id": response[i].produtoId,
            "Nome": response[i].nome,
            "Avaliacao": response[i].avaliacao,
            "Descricao": response[i].descricao,
            "Preco": response[i].preco,
            "Quantidade": response[i].quantidade,
            "Status": response[i].ativo
        }

        let tr = document.createElement("tr")
        tr.setAttribute("class", "linhaTabela")
        tabela.appendChild(tr)

        let nome = document.createElement("td")
        let avaliacao = document.createElement("td")
        let descricao = document.createElement("td")
        let preco = document.createElement("td")
        let quantidade = document.createElement("td")
        let status = document.createElement("td")
        let alterar = document.createElement("td")

        nome.textContent = `${produto.Nome}`
        avaliacao.textContent = `${produto.Avaliacao}`
        descricao.textContent = `${produto.Descricao}`
        preco.textContent = `${produto.Preco}`
        quantidade.textContent = `${produto.Quantidade}`
        status.innerHTML = `<button class = "changeStatusButton" value=${produto.Id}> ${produto.Status ? "Ativo" : "Inativo"} </button>`
        alterar.innerHTML = `<a onclick="window.open('../pages/alterarProduto.html?id=${produto.Id}','name','width=1500,height=1000')"> <button class = "alterarProduto"> alterar </button> <a>`

        tr.appendChild(nome)
        tr.appendChild(avaliacao)
        tr.appendChild(descricao)
        tr.appendChild(preco)
        tr.appendChild(quantidade)
        tr.appendChild(status)
        tr.appendChild(alterar)
    }

    alterarStatus()

}

// função responsavel por inativar ou ativar uma pessoa e/ou um produto
function alterarStatus() {
    let changeStatusButton = document.querySelectorAll(".changeStatusButton")

    if (document.getElementById("secaoTabelaUsuario").style.display == "block") {
        changeStatusButton.forEach(element => {
            element.addEventListener("click", async () => {
                await fetch(`/usuario/${element.value}`, { method: 'DELETE' })
                clearTable()
                createTbUsers()
            })
        });
    } else if (document.getElementById("secaoTabelaProduto").style.display == "block") {
        changeStatusButton.forEach(element => {
            element.addEventListener("click", async () => {
                await fetch(`/produto/${element.value}`, { method: 'DELETE' })
                clearTable()
                createTbProducts()
            })
        });
    } else if (document.getElementById("secaoTabelaPedido").style.display == "block") {
        changeStatusButton.forEach(element => {
            element.addEventListener("click", async () => {
                let ped = {
                    "id": parseInt(element.value),
                    "sigla": `${document.getElementsByClassName(`pedido${parseInt(element.value)}`)[0].value}`
                }
                await fetch(`/pedido/update`, {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(ped)
                })
                clearTable()
                createTbPedidos()
            })
        });
    }

}

// função que limpa conteudo da tabela
function clearTable() {
    const tr = document.querySelectorAll(".linhaTabela")

    tr.forEach(linha => {
        linha.innerHTML = ""
    })
}

async function checkSessao() {
    let cargoId = getCookie("cargo")
    let sessaoCode = getCookie("jobSession")
    if (sessaoCode != undefined) {
        let funcionario = await fetch(`/usuario/${parseInt(sessaoCode)}`).then(data => data.json())
        let cont = 1
        let cargo = funcionario.cargo.cargoId;
        for (cont = 1; cont <= 16; cont++) {
            cargo = String(cargo).hashCode()
        }

        if (cargo != cargoId) {
            alert("Sessão inválida")
            Cookies.remove("jobSession")
            Cookies.remove("cargo")
            location.href = "./loginBackoffice.html"
        }
    } else {
        alert("Inicie sua sessão")
        Cookies.remove("jobSession")
        Cookies.remove("cargo")
        location.href = "./loginBackoffice.html"
    }
}

document.getElementById("mostrarTbPedidos").addEventListener("click", () => {
    checkSessao()
    const tabela = document.getElementById("secaoTabelaPedido");
    const tabelaProdutos = document.getElementById("secaoTabelaProduto");
    const tabelaUsuarios = document.getElementById("secaoTabelaUsuario");

    if (tabelaProdutos.style.display == "block" || tabelaUsuarios.style.display == "block") {
        tabelaProdutos.style.display = "none";
        tabelaUsuarios.style.display = "none";
    }
    if (tabela.style.display == "none") {
        createTbPedidos();
        tabela.style.display = "block";
    } else {
        tabela.style.display = "none";
    }
});

async function createTbPedidos() {

    const response = await fetch("/pedido").then((data) =>
        data.json()
    );

    clearTable()

    const tabela = document.getElementById("tabelaPedido");

    //Criando os campos da tabela pedido
    for (var i = 0; i < response.length; i++) {

        let pedido = {
            "Id": response[i].pedidoId,
            "Data": response[i].dataPedido,
            "ValorTotal": response[i].valorTotal,
            "Status": response[i].statusPagamento,
        }

        let tr = document.createElement("tr")
        tr.setAttribute("class", "linhaTabela")
        tabela.appendChild(tr)

        let pedidoId = document.createElement("td")
        let data = document.createElement("td")
        let valorTotal = document.createElement("td")
        let status = document.createElement("td")
        let alterar = document.createElement("td")

        pedidoId.textContent = `${response[i].pedidoId}`
        data.textContent = `${(response[i].dataPedido).slice(0, 10).split("-").reverse().join(" - ")}`
        valorTotal.textContent = `R$ ${parseFloat(response[i].valorTotal).toFixed(2).replace(".", ",")}`

        status.innerHTML = `<select name="status" class="status pedido${response[i].pedidoId}">
            <option value="AP" ${response[i].statusPagamento == "AP" ? "selected" : ""}>Aguardando Pagamento</option>
            <option value="PR" ${response[i].statusPagamento == "PR" ? "selected" : ""}>Pagamento Rejeitado</option>
            <option value="PS" ${response[i].statusPagamento == "PS" ? "selected" : ""}>Pagamento Com Sucesso</option>
            <option value="AR" ${response[i].statusPagamento == "AR" ? "selected" : ""}>Aguardando Retirada</option>
            <option value="ET" ${response[i].statusPagamento == "ET" ? "selected" : ""}>Em Trânsito</option>
            <option value="EN" ${response[i].statusPagamento == "EN" ? "selected" : ""}>Entregue</option>
        </select>`;
        alterar.innerHTML = `<button value=${response[i].pedidoId}" class="btn btn-primary changeStatusButton">Mudar Status</button>`

        tr.appendChild(pedidoId)
        tr.appendChild(data)
        tr.appendChild(valorTotal)
        tr.appendChild(status)
        tr.appendChild(alterar)
    }

    alterarStatus()

}