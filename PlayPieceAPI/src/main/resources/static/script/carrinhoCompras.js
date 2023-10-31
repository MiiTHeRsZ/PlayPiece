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
        let preco = 0;

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
                item.setAttribute("class", "itemProduto");
                let nomeProduto = document.createElement("td");
                nomeProduto.setAttribute("class", "nomeProduto");
                let quantidade = document.createElement("td");
                let precoUnitario = document.createElement("td");
                let precoTotal = document.createElement("td");
                precoTotal.setAttribute("class", "precoTotalProduto");
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

                subtotal();
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
            item.setAttribute("class", "itemProduto");
            let nomeProduto = document.createElement("td");
            nomeProduto.setAttribute("class", "nomeProduto");
            let quantidade = document.createElement("td");
            let precoUnitario = document.createElement("td");
            let precoTotal = document.createElement("td");
            precoTotal.setAttribute("class", "precoTotalProduto");
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

            subtotal();
        }
    }
}
criaCarrinho();

function subtotal() {
    let precosTotais = document.querySelectorAll(".precoTotalProduto");
    let total = 0.0;

    precosTotais.forEach(item => {
        total += Number(item.textContent.replace(",", "."));
    });

    let subtotal = document.getElementById("subtotalTabela");
    subtotal.textContent = `${parseFloat(total).toFixed(2).replace(".", ",")}`;
}

function limparCarrinho() {
    const tr = document.querySelectorAll(".itemTabela")

    tr.forEach(linha => {
        linha.innerHTML = ""
    })
}

function subtrairProduto(idProduto) {
    let carrinho = sessionStorage.getItem("carrinho");
    let carrinhoFinal = "";

    if (carrinho != null && carrinho != "" && carrinho != undefined) {
        carrinho.split(",").forEach(item => {
            prod = item.split("-");
            if (Number(prod[0]) != idProduto) {
                carrinhoFinal += `${item},`;
            } else {
                if (Number(prod[1]) > 1) {
                    carrinhoFinal += `${prod[0]}-${(Number(prod[1]) - 1)},`;
                } else {
                    carrinhoFinal += `${item},`;
                    alert("Não pode diminuir mais a quantidade desse produto\nClique em remover do carrinho, se for o caso!")
                }
            }
        });
    }

    carrinhoFinal = carrinhoFinal.slice(0, -1);

    sessionStorage.setItem('carrinho', carrinhoFinal);

    criaCarrinho();
}

function adicionarProduto(idProduto) {
    let carrinho = sessionStorage.getItem("carrinho");
    let carrinhoFinal = "";

    if (carrinho != null && carrinho != "" && carrinho != undefined) {
        carrinho.split(",").forEach(item => {
            prod = item.split("-");
            if (Number(prod[0]) != idProduto) {
                carrinhoFinal += `${item},`;
            } else {
                carrinhoFinal += `${prod[0]}-${(Number(prod[1]) + 1)},`;
            }
        });
    }

    carrinhoFinal = carrinhoFinal.slice(0, -1);

    sessionStorage.setItem('carrinho', carrinhoFinal);

    criaCarrinho();
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

async function calcularFrete() {
    const enderecoEntrega = document.getElementById("enderecoEntrega").value;
    const resultado = await fetch(`https://maps.googleapis.com/maps/api/distancematrix/json?destinations=${enderecoEntrega}&origins=04696000&units=meters&key=AIzaSyAyOwTAOLLcGW4nLqehdC0J8vPYS4Noj8w`);
    const quantidadeProdutos = document.getElementById("produtosTabela").lastChild.querySelector(".itemProduto").textContent;
    const distancia = resultado.rows[0].elements[0].distance.value * .001;

    const frete01 = (quantidadeProdutos * 500 + .91) * distancia;
    const frete02 = (quantidadeProdutos * 500 + .61) * distancia;
    const frete03 = (quantidadeProdutos * 500 + .31) * distancia;

    document.getElementById("frete1").innerHTML = frete01;
    document.getElementById("frete2").innerHTML = frete02;
    document.getElementById("frete3").innerHTML = frete03;

    document.getElementById("opcoesFrete").display = "inline";
}