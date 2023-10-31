function setCookie(nome, info, exdays) {
    Cookies.set(nome, info, exdays)
}

function getCookie(nome) {
    return Cookies.get(nome)
}

function checkCookie(nome) {
    var sessaoId = getCookie(nome);
    if (sessaoId != undefined) {
        document.getElementById("perfil").href = `./perfilCliente.html`;
    }
}
checkCookie('sessaoId');

var idCliente = getCookie('sessaoId');

function menu() {
    let nome_perfil = document.getElementById("nome-perfil");
    let login_perfil = document.getElementById("login-perfil");
    let sair = document.getElementById("sair");

    if (idCliente == undefined) {
        nome_perfil.innerHTML = "Seja Bem-Vindo(a)!";
        login_perfil.innerHTML = "Login";
        login_perfil.href = "./loginCliente.html";
        sair.style.display = 'none';
    } else {
        nome_perfil.innerHTML = `Olá, ${getCookie("nome")}!`;
        login_perfil.innerHTML = "Perfil";
        login_perfil.href = "./perfilCliente.html";
        sair.style.display = '';
    }
}
menu();

function desconectar() {
    Cookies.remove('sessaoId');
    Cookies.remove('nome');
    window.location.reload();
}

async function criaCarrinho() {
    const carrinho = sessionStorage.getItem("carrinho");
    limparCarrinho();
    if (carrinho != "" && carrinho != null && carrinho != undefined) {
        let produtos = "";

        if (carrinho.length > 3) {
            let quantidadeItens = 0;
            carrinho.split(",").forEach(async info => {
                let prod = info.split("-");
                const produto = await fetch(`/produto/${prod[0]}`).then(data => data.json());

                const tabela = document.getElementById("produtosTabela");

                let tr = document.createElement("tr");
                tr.setAttribute("class", "itemTabela");
                tabela.appendChild(tr);

                let id = document.createElement("td");
                let item = document.createElement("td");
                let nomeProduto = document.createElement("td");
                nomeProduto.setAttribute("class", "nomeProduto");
                let quantidade = document.createElement("td");
                let precoUnitario = document.createElement("td");
                let precoTotal = document.createElement("td");
                let removerCarrinho = document.createElement("td");

                id.style.display = "none";
                id.textContent = `${produto.id}`;
                item.textContent = ++quantidadeItens;
                nomeProduto.textContent = `${produto.nome}`;
                quantidade.innerHTML = `<button class="btn-qntd" onclick="subtrairProduto(${produto.id})">-</button>${prod[1]}<button class="btn-qntd" onclick="adicionarProduto(${produto.id})">+</button>`;
                precoUnitario.textContent = `${parseFloat(produto.preco).toFixed(2).replace(".", ",")}`;
                precoTotal.textContent = `${parseFloat(produto.preco * Number(prod[1])).toFixed(2).replace(".", ",")}`;
                removerCarrinho.innerHTML = `<button onclick="removerItem(${produto.id})">Remover</button>`;

                tr.appendChild(id);
                tr.appendChild(item);
                tr.appendChild(nomeProduto);
                tr.appendChild(quantidade);
                tr.appendChild(precoUnitario);
                tr.appendChild(precoTotal);
                tr.appendChild(removerCarrinho);
            });
        } else {
            let quantidadeItens = 0;
            prod = carrinho.split("-");
            const produto = await fetch(`/produto/${prod[0]}`).then(data => data.json());

            const tabela = document.getElementById("produtosTabela");

            let tr = document.createElement("tr");
            tr.setAttribute("class", "itemTabela");
            tabela.appendChild(tr);

            let id = document.createElement("td");
            let item = document.createElement("td");
            let nomeProduto = document.createElement("td");
            nomeProduto.setAttribute("class", "nomeProduto");
            let quantidade = document.createElement("td");
            let precoUnitario = document.createElement("td");
            let precoTotal = document.createElement("td");
            let removerCarrinho = document.createElement("td");

            id.style.display = "none";
            id.textContent = `${produto.id}`;
            item.textContent = ++quantidadeItens;
            nomeProduto.textContent = `${produto.nome}`;
            quantidade.innerHTML = `<button class="btn-qntd" onclick="subtrairProduto(${produto.id})">-</button>${prod[1]}<button class="btn-qntd" onclick="adicionarProduto(${produto.id})">+</button>`;
            precoUnitario.textContent = `${parseFloat(produto.preco).toFixed(2).replace(".", ",")}`;
            precoTotal.textContent = `${parseFloat(produto.preco * Number(prod[1])).toFixed(2).replace(".", ",")}`;
            removerCarrinho.innerHTML = `<button onclick="removerItem(${produto.id})">Remover</button>`;

            tr.appendChild(id);
            tr.appendChild(item);
            tr.appendChild(nomeProduto);
            tr.appendChild(quantidade);
            tr.appendChild(precoUnitario);
            tr.appendChild(precoTotal);
            tr.appendChild(removerCarrinho);
        }
    }
}
criaCarrinho();

function limparCarrinho() {
    const tr = document.querySelectorAll(".itemTabela")

    tr.forEach(linha => {
        linha.innerHTML = ""
    })
}

function subtrairProduto(idProduto) {
    
}

function removerItem(idProduto) {
    let carrinho = sessionStorage.getItem('carrinho');
    let carrinhoFinal = "";

    carrinho.split(",").forEach(item => {
        prod = item.split("-");

        if (Number(prod[0]) != idProduto) {
            carrinhoFinal += `${item},`;
        }

        carrinhoFinal = carrinhoFinal.slice(0, -1);
        sessionStorage.setItem('carrinho', carrinhoFinal);
    });

    criaCarrinho();
}