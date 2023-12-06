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

function validarVazio() {
    let checkVazio = sessionStorage.getItem("carrinho")

    if (checkVazio == undefined || checkVazio == "") {
        document.getElementById("vazio-section").classList.add("display");
        document.getElementById("carrinho").style.display = "none";
        document.getElementById("totalCompra").style.display = "none";
    } else {
        document.getElementById("vazio-section").classList.remove("display");
        document.getElementById("carrinho").style.display = "block";
        document.getElementById("totalCompra").style.display = "flex";
    }
}

validarVazio()

function menu() {
    let nome_perfil = document.getElementById("nome-perfil");
    let login_perfil = document.getElementById("login-perfil");
    let sair = document.getElementById("sair");

    if (idCliente == undefined) {
        nome_perfil.innerHTML = "Seja Bem-Vindo(a)!";
        login_perfil.innerHTML = "Login";
        login_perfil.href = "./loginCliente.html";
        sair.style.display = 'none';
        document.getElementById("enderecoEntregaLogado").style.display = "none";
    } else {
        nome_perfil.innerHTML = `Olá, ${getCookie("nome")}!`;
        login_perfil.innerHTML = "Perfil";
        login_perfil.href = "./perfilCliente.html";
        sair.style.display = '';
        document.getElementById("enderecoEntrega").style.display = "none";
    }
}
menu();

function desconectar() {
    Cookies.remove('sessaoId');
    Cookies.remove('nome');
    sessionStorage.removeItem("carrinho");
    window.open("../index.html", "_self");
}

async function criaCarrinho() {
    const carrinho = sessionStorage.getItem("carrinho");
    let finalCart = [];
    limparCarrinho();
    if (carrinho != "" && carrinho != null && carrinho != undefined) {
        let preco = 0;
        carrinhoSplit = carrinho.split(",")
        carrinhoSplit.forEach(item => {
            let itemSplit = item.split("-")
            let prod = {
                "id": itemSplit[0],
                "quantidade": itemSplit[1]
            }

            finalCart.push(prod)
        })

        finalCart.sort(function (a, b) {
            return a.id < b.id ? -1 : a.id > b.id ? 1 : 0;
        })

        if (carrinho.length > 3) {
            let quantidadeItens = 0;
            for (info of finalCart) {
                const produto = await fetch(`/produto/${info.id}`).then(data => data.json());

                const tabela = document.getElementById("produtosTabela");

                let tr = document.createElement("tr");
                tr.setAttribute("class", "itemTabela");
                tabela.appendChild(tr);

                let id = document.createElement("td");
                id.setAttribute("class", "idProduto");
                let item = document.createElement("td");
                item.setAttribute("class", "itemProduto");
                let img = document.createElement("td");
                img.setAttribute("class", "imgProduto");
                let nomeProduto = document.createElement("td");
                nomeProduto.setAttribute("class", "nomeProduto");
                let quantidade = document.createElement("td");
                quantidade.setAttribute("class", "quantidadeProduto")
                let precoUnitario = document.createElement("td");
                precoUnitario.setAttribute("class", "precoUnitarioProduto")
                let precoTotal = document.createElement("td");
                precoTotal.setAttribute("class", "precoTotalProduto");
                let removerCarrinho = document.createElement("td");

                let newLink;
                let listaImagensProduto = produto.listaImagens
                if (listaImagensProduto.length != 0) {
                    listaImagensProduto.forEach(imagem => {
                        if (imagem.padrao) {
                            let caminho = imagem.caminho;
                            let link = caminho.split("/");
                            newLink = "../" + link[5] + "/" + link[6] + "/" + link[7] + "/" + link[8];
                        }
                        if (imagem == null) {
                            newLink = "../images/semImagem.webp"
                        }
                    })
                } else {
                    newLink = "../images/semImagem.webp"
                }

                id.style.display = "none";
                id.textContent = `${produto.produtoId}`;
                item.textContent = ++quantidadeItens;
                img.innerHTML = `<img src="${newLink}" style="width: 30px; height: 30px"></img>`;
                nomeProduto.textContent = `${produto.nome}`;
                quantidade.innerHTML = `<button class="btn btn-outline-secondary" onclick="subtrairProduto(${produto.produtoId})">-</button><p class="qntdProduto">${info.quantidade}</p><button class="btn btn-outline-secondary" onclick="adicionarProduto(${produto.produtoId})">+</button>`;
                precoUnitario.textContent = `${parseFloat(produto.preco).toFixed(2).replace(".", ",")}`;
                precoTotal.textContent = `${parseFloat(produto.preco * Number(info.quantidade)).toFixed(2).replace(".", ",")}`;
                removerCarrinho.innerHTML = `<button onclick="removerItem(${produto.produtoId})" class="btn btn-outline-danger">Remover</button>`;

                tr.appendChild(id);
                tr.appendChild(item);
                tr.appendChild(img);
                tr.appendChild(nomeProduto);
                tr.appendChild(quantidade);
                tr.appendChild(precoUnitario);
                tr.appendChild(precoTotal);
                tr.appendChild(removerCarrinho);

                subtotal();
            }
        } else {
            let quantidadeItens = 0;
            prod = carrinho.split("-");
            const produto = await fetch(`/produto/${prod[0]}`).then(data => data.json());

            const tabela = document.getElementById("produtosTabela");

            let tr = document.createElement("tr");
            tr.setAttribute("class", "itemTabela");
            tabela.appendChild(tr);

            let id = document.createElement("td");
            id.setAttribute("class", "idProduto");
            let item = document.createElement("th");
            item.setAttribute("class", "itemProduto");
            item.setAttribute("scope", "row");
            let img = document.createElement("td");
            img.setAttribute("class", "imgProduto");
            let nomeProduto = document.createElement("td");
            nomeProduto.setAttribute("class", "nomeProduto");
            let quantidade = document.createElement("td");
            quantidade.setAttribute("class", "quantidadeProduto")
            let precoUnitario = document.createElement("td");
            precoUnitario.setAttribute("class", "precoUnitarioProduto")
            let precoTotal = document.createElement("td");
            precoTotal.setAttribute("class", "precoTotalProduto");
            let removerCarrinho = document.createElement("td");
            removerCarrinho.setAttribute("class", "removerProduto")

            let newLink;
            let listaImagensProduto = produto.listaImagens
            console.log(listaImagensProduto.length);
            if (listaImagensProduto.length != 0) {
                listaImagensProduto.forEach(imagem => {
                    if (imagem.padrao) {
                        let caminho = imagem.caminho;
                        let link = caminho.split("/");
                        newLink = "../" + link[5] + "/" + link[6] + "/" + link[7] + "/" + link[8];
                    }

                    if (imagem == null) {
                        newLink = "../images/semImagem.webp"
                    }
                })
            } else {
                newLink = "../images/semImagem.webp"
            }

            id.style.display = "none";
            id.textContent = `${produto.produtoId}`;
            item.textContent = ++quantidadeItens;
            img.innerHTML = `<img src="${newLink}" style="width: 30px; height: 30px"></img>`;
            nomeProduto.textContent = `${produto.nome}`;
            quantidade.innerHTML = `<button class="btn-qntd" onclick="subtrairProduto(${produto.produtoId})">-</button><p class="qntdProduto">${prod[1]}</p><button class="btn-qntd" onclick="adicionarProduto(${produto.produtoId})">+</button>`;
            precoUnitario.textContent = `${parseFloat(produto.preco).toFixed(2).replace(".", ",")}`;
            precoTotal.textContent = `${parseFloat(produto.preco * Number(prod[1])).toFixed(2).replace(".", ",")}`;
            removerCarrinho.innerHTML = `<button onclick="removerItem(${produto.produtoId})">Remover</button>`;

            tr.appendChild(id);
            tr.appendChild(item);
            tr.appendChild(img);
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
preencheEnderecos();

async function preencheEnderecos() {
    const dados = await fetch(`/cliente/${idCliente}`).then(data => data.json());

    dados.listaEndereco.forEach(endereco => {
        let opcao = document.createElement("option");
        opcao.value = endereco.cep;
        if (endereco.padrao) {
            opcao.toggleAttribute("selected");
        }
        opcao.textContent = endereco.cep;
        if (endereco.padrao) {
            opcao.textContent += ` \u2B50`
        }
        document.getElementById("enderecoEntregaLogado").appendChild(opcao);
    });
}

function subtotal() {
    let precosTotais = document.querySelectorAll(".precoTotalProduto");
    let total = 0.0;

    precosTotais.forEach(item => {
        total += Number(item.textContent.replace(",", "."));
    });

    let subtotal = document.getElementById("subtotal");
    subtotal.textContent = `Subtotal: R$ ${parseFloat(total).toFixed(2).replace(".", ",")}`;

    document.getElementById("freteFinal").textContent = "Frete ainda não calculado";

    calcularTotal();
}

function limparCarrinho() {
    const tr = document.querySelectorAll(".itemTabela")

    tr.forEach(linha => {
        linha.innerHTML = ""
    })

    validarVazio()
}

async function subtrairProduto(idProduto) {
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
                    let carrinhoFinal = "";

                    carrinho.split(",").forEach(item => {
                        prod = item.split("-");

                        if (Number(prod[0]) != idProduto) {
                            carrinhoFinal += `${item},`;
                        }

                        carrinhoFinal = carrinhoFinal.slice(0, -1);
                        sessionStorage.setItem('carrinho', carrinhoFinal);

                        validarVazio()
                    });
                }
            }
        });
    }

    carrinhoFinal = carrinhoFinal.slice(0, -1);

    sessionStorage.setItem('carrinho', carrinhoFinal);

    criaCarrinho();
}

async function adicionarProduto(idProduto) {
    let carrinho = sessionStorage.getItem("carrinho");
    let carrinhoFinal = "";

    if (carrinho != null && carrinho != "" && carrinho != undefined) {
        for (const item of carrinho.split(",")) {
            prod = item.split("-");
            if (Number(prod[0]) != idProduto) {
                carrinhoFinal += `${item},`;
            } else {
                var result = await alterQuantidadeItem(prod[0], (Number(prod[1]) + 1))
                if (result === true) {
                    carrinhoFinal += `${prod[0]}-${(Number(prod[1]) + 1)},`;
                } else {
                    carrinhoFinal += `${item},`;
                }
            }
        }
    }

    carrinhoFinal = carrinhoFinal.slice(0, -1);

    sessionStorage.setItem('carrinho', carrinhoFinal);

    criaCarrinho();
}

async function alterQuantidadeItem(idProd, novaQuant) {
    let prod;
    const result = await fetch(`/produto/${idProd}`).then(data => data.json())

    if (novaQuant > result.quantidade) {
        alert("Quantidade indisponível em estoque");
        return false;
    } else {
        return true;
    }
}

function removerItem(idProduto) {
    debugger;
    let carrinho = sessionStorage.getItem('carrinho');
    let carrinhoFinal = "";
    let itens = carrinho.split(",")

    itens.forEach(item => {
        prod = item.split("-");

        if (Number(prod[0]) != idProduto) {
            carrinhoFinal += `${item},`;
        }

    });
    carrinhoFinal = carrinhoFinal.slice(0, -1);
    sessionStorage.setItem('carrinho', carrinhoFinal);
    validarVazio()
    criaCarrinho();
}

async function calcularFrete() {

    const cep = idCliente == undefined ? document.getElementById("enderecoEntrega").value : document.getElementById("enderecoEntregaLogado").value;

    if (cep.length == 8) {
        const consultaCep = await fetch(`https://viacep.com.br/ws/${cep}/json/`).then(data => data.json());
        if (consultaCep.erro) {
            alert("CEP inválido!");
        } else {
            let distancia;
            const service = new google.maps.DistanceMatrixService();
            const origin = "04696000";
            const destination = cep;
            service.getDistanceMatrix(
                {
                    origins: [origin],
                    destinations: [destination],
                    travelMode: google.maps.TravelMode.DRIVING,
                    unitSystem: google.maps.UnitSystem.METRIC,
                    avoidHighways: false,
                    avoidTolls: false,
                }, callback);

            function callback(response, status) {
                if (status == "OK") {
                    distancia = response.rows[0].elements[0].distance.value * .001;

                    const quantidadeProdutos = document.getElementById("produtosTabela").lastChild.querySelector(".itemProduto").textContent;

                    const frete01 = (quantidadeProdutos * .5 + .91) * distancia;
                    const frete02 = (quantidadeProdutos * .5 + .61) * distancia;
                    const frete03 = (quantidadeProdutos * .5 + .31) * distancia;

                    document.getElementById("frete1").innerHTML = `R$ ${parseFloat(frete01).toFixed(2).replace(".", ",")}`;
                    document.getElementById("frete01").value = parseFloat(frete01).toFixed(2);
                    document.getElementById("frete2").innerHTML = `R$ ${parseFloat(frete02).toFixed(2).replace(".", ",")}`;
                    document.getElementById("frete02").value = parseFloat(frete02).toFixed(2);
                    document.getElementById("frete3").innerHTML = `R$ ${parseFloat(frete03).toFixed(2).replace(".", ",")}`;
                    document.getElementById("frete03").value = parseFloat(frete03).toFixed(2);

                    document.getElementById("opcoesFrete").style.display = "inline";
                } else {
                    alert("rip")
                }
            }
        }
    } else {
        alert("CEP inválido!");
    }
}

function calcularTotal() {
    let sub = document.getElementById("subtotal").textContent.slice(13);
    let fretes = document.querySelectorAll('input[type="radio"]');
    let freteEscolhido = 0;

    for (let index = 0; index < fretes.length; index++) {
        if (fretes[index].checked) {
            freteEscolhido = fretes[index].value;
        }
    }

    let total = parseFloat(Number(sub.replace(",", ".")) + Number(freteEscolhido)).toFixed(2).replace(".", ",");

    document.getElementById("freteFinal").innerHTML = `Frete: R$ ${freteEscolhido}`;
    document.getElementById("valorTotal").innerHTML = `Total: R$ ${total}`;
}

const radioBtns = document.querySelectorAll('input[type="radio"]');

radioBtns.forEach(radioBtn => {
    radioBtn.addEventListener('click', () => {
        calcularTotal()
    });
});

function finalizarPedido() {
    if (idCliente == undefined) {
        location.href = "./loginCliente.html";
    } else {
        var tabela = document.getElementById("tabelaProdutos");
        var linhasTabela = tabela.querySelectorAll("tr");

        linhasTabela.forEach(async (linha, index) => {
            if (index != 0) {
                let idProduto = linha.querySelector(".idProduto").textContent;
                let qntdProduto = linha.querySelector(".qntdProduto").textContent;

                const item = await fetch(`/itemcarrinho/create?codProduto=${idProduto}&quantidade=${qntdProduto}&idCliente=${idCliente}`, {
                    method: "POST",
                    headers: { 'Content-Type': 'application/json' },
                    body: "",
                });

                if (item.status == 200 || item.status == 201) {
                    window.open("./escolherEndereco.html", "_self");
                } else {
                    alert(`Erro ao adicionar produtos ao carrinho!`);
                }
            }
        });
    }
}