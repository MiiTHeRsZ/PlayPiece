const urlParams = new URLSearchParams(window.location.search);
group = urlParams.get('group')

if (group == 2) {
    document.getElementById("mostrarTbUsuarios").style.display = "none"
    document.getElementById("btnCadastroUsuario").style.display = "none"
    document.getElementById("btnCadastroProduto").style.display = "none"
}

document.getElementById("mostrarTbUsuarios").addEventListener("click", () => {
    const tabela = document.getElementById("secaoTabelaUsuario");
    const tabelaProduto = document.getElementById("secaoTabelaProduto");

    if (tabelaProduto.style.display == "block") {
        tabelaProduto.style.display = "none"
    }
    if (tabela.style.display == "none") {
        createTbUsers();
        tabela.style.display = "block";
    } else {
        tabela.style.display = "none"
    }
});

async function createTbUsers() {

    const response = await fetch("http://localhost:8080/usuario").then((data) =>
        data.json()
    );

    clearTable()

    const tabela = document.getElementById("tabelaUser");

    //Criando os campos da tabela usuario
    for (var i = 0; i < response.length; i++) {

        let usuario = {
            "Id": response[i].id,
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
        alterar.innerHTML = `<a onclick="window.open('../pages/alterarUsuario.html?id=${usuario.Id}','name','width=500,height=1000')"> <button class = "alterarUsuario"> alterar </button> <a>`

        tr.appendChild(nome)
        tr.appendChild(email)
        tr.appendChild(status)
        tr.appendChild(grupo)
        tr.appendChild(alterar)
    }

    alterarStatus()

}

//Mostrar tabela Usuário // Henrick
document.getElementById("mostrarTbProdutos").addEventListener("click", () => {
    const tabela = document.getElementById("secaoTabelaProduto");
    const tabelaUsuario = document.getElementById("secaoTabelaUsuario");

    if (tabelaUsuario.style.display == "block") {
        tabelaUsuario.style.display = "none"
    }
    if (tabela.style.display == "none") {
        createTbProducts();
        tabela.style.display = "block";
    } else {
        tabela.style.display = "none"
    }
});

async function createTbProducts() {

    const response = await fetch("http://localhost:8080/produto").then((data) =>
        data.json()
    );

    clearTable()

    const tabela = document.getElementById("tabelProduct");

    //Criando os campos da tabela produto
    for (var i = 0; i < response.length; i++) {

        let produto = {
            "Id": response[i].id,
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
        alterar.innerHTML = `<a onclick="window.open('../pages/alterarProduto.html?id=${produto.Id}&group=${group}','name','width=500,height=1000')"> <button class = "alterarProduto"> alterar </button> <a>`
        if (group == 2) {
            status.firstChild.setAttribute("disabled", "true")
        }

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
//Mostrar tabela produto // Henrick


/* Filtrar por Nome - Usuário */

document.getElementById("pesquisarPorNome").addEventListener("click", () => {
    const nome = document.getElementById("txtNome").value;
    const tabela = document.getElementById("secaoTabelaUsuario");
    clearTable()
    pesquisarPorNome(nome);
    tabela.style.display = "block";
});

async function pesquisarPorNome(nome) {

    const response = await fetch(`http://localhost:8080/usuario/search?nome=${nome}`).then((data) =>
        data.json()
    );

    const tabela = document.getElementById("tabelaUser");

    //Criando os campos da tabela usuario
    for (var i = 0; i < response.length; i++) {
        let usuario = {
            "Id": response[i].id,
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
        alterar.innerHTML = `<a onclick="window.open('../pages/alterarUsuario.html?id=${usuario.Id}','name','width=500,height=1000')"> <button class = "alterarUsuario"> alterar </button> <a>`

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
    const nome = document.getElementById("txtNomeProduto").value;
    const tabela = document.getElementById("secaoTabelaProduto");
    clearTable()
    pesquisarPorNomeProduto(nome);
    tabela.style.display = "block";
});

async function pesquisarPorNomeProduto(nome) {

    const response = await fetch(`http://localhost:8080/produto/search?nome=${nome}`).then((data) =>
        data.json()
    );

    const tabela = document.getElementById("tabelProduct");

    //Criando os campos da tabela produto
    for (var i = 0; i < response.length; i++) {

        let produto = {
            "Id": response[i].id,
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
        alterar.innerHTML = `<a onclick="window.open('../pages/alterarProduto.html?id=${produto.Id}','name','width=500,height=1000')"> <button class = "alterarProduto"> alterar </button> <a>`

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

function alterarStatus() {
    let changeStatusButton = document.querySelectorAll(".changeStatusButton")

    if (document.getElementById("secaoTabelaUsuario").style.display == "block") {
        changeStatusButton.forEach(element => {
            element.addEventListener("click", async () => {
                await fetch(`http://localhost:8080/usuario/${element.value}`, { method: 'DELETE' })
                clearTable()
                createTbUsers()
            })
        });
    } else if (document.getElementById("secaoTabelaProduto").style.display == "block") {
        changeStatusButton.forEach(element => {
            element.addEventListener("click", async () => {
                await fetch(`http://localhost:8080/produto/${element.value}`, { method: 'DELETE' })
                clearTable()
                createTbProducts()
            })
        });
    }

}

function clearTable() {
    const tr = document.querySelectorAll(".linhaTabela")

    tr.forEach(linha => {
        linha.innerHTML = ""
    })
}