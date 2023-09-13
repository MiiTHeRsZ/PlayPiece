const urlParams = new URLSearchParams(window.location.search);
group = urlParams.get('group')

if (group == 2) {
    document.getElementById("mostrarTabela").style.display = "none"
}

document.getElementById("mostrarTabela").addEventListener("click", () => {
    const tabela = document.getElementById("secaoTabelaUsuario");
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

/* Filtrar por Nome */

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

function alterarStatus() {
    let changeStatusButton = document.querySelectorAll(".changeStatusButton")

    changeStatusButton.forEach(element => {
        element.addEventListener("click", async () => {
            await fetch(`http://localhost:8080/usuario/${element.value}`, { method: 'DELETE' })
            clearTable()
            createTbUsers()
        })
    });
}

function clearTable() {
    const tr = document.querySelectorAll(".linhaTabela")

    tr.forEach(linha => {
        linha.innerHTML = ""
    })
}