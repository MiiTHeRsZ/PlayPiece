const urlParams = new URLSearchParams(window.location.search);
group = urlParams.get('group')
idProduto = urlParams.get('id')

//#region imagens

let boxImagens = document.getElementById("input_imagens"),
    inputImagens = document.querySelector("#imagens"),
    form = document.getElementById("form")
let listaInput = []

async function getImagens() {

    let produto = await fetch(`/produto/${idProduto}`).then(response => response.json())
    let imagens = produto.listaImagens

    imagens.forEach(async imagem => {
        console.log(imagem.caminho)
        listaInput.push(imagem.caminho)
        mostarImagensInput()
    });
    console.log(listaInput)

    inputImagens.addEventListener("change", () => {
        const files = inputImagens.files

        for (let i = 0; i < files.length; i++) {
            listaInput.push(files[i])
        }
        console.log(listaInput)
        mostarImagensInput()
    })

    boxImagens.addEventListener("drop", (e) => {
        e.preventDefault()

        const files = e.dataTransfer.files
        for (let i = 0; i < files.length; i++) {
            if (!files[i].type.match("image")) continue

            if (listaInput.every(imagem => imagem.name !== files[i].name))
                listaInput.push(URL.createObjectURL(files[i]))
        }

        mostarImagensInput()
    })
}

getImagens()

function mostarImagensInput() {
    limparImagensInput()
    let a = document.querySelector("#imagens").files
    let imagens = ""
    let text = document.querySelector("#input_imagens p")
    text.style.display = "none"
    if (listaInput.length == 0) {
        let text = document.querySelector("#input_imagens p")
        text.style.display = "inline"
    }
    listaInput.forEach((imagem, index) => {
        let link = imagem.split("/")
        let newLink = "../" + link[4] + "/" + link[5] + "/" + link[6] + "/" + link[7]
        imagens += `<div class="imagem-input">
        <img src="${newLink}" alt="imagem">
        <span class="fav" onclick="favoritarInput(${index})">&#10025;</span>
        <span class="del" onclick="removerInput(${index})">&times;</span>
        </div>`
    })

    boxImagens.innerHTML += imagens
    inputImagens.value = ""
}
function limparImagensInput() {
    let imagens = document.querySelectorAll(".imagem-input")
    imagens.forEach((imagem) => {
        imagem.remove()
    })
}

function favoritarInput(index) {
    let unfavcount = 0
    let el = document.querySelectorAll(".fav")
    el.forEach((item, i) => {
        if (i == index) {
            item.textContent == "✩" ? item.innerHTML = "&#10029;" : item.innerHTML = "&#10025;"
        } else {
            item.textContent == "✭" ? item.innerHTML = "&#10025;" : item.innerHTML = "&#10025;"
        }

        item.textContent == "✩" ? unfavcount++ : ""

    })
    unfavcount == el.length ? el[0].innerHTML = "&#10029;" : ""
}

function removerInput(index) {
    listaInput.splice(index, 1)
    console.log(listaInput)
    mostarImagensInput()
}

//#endregion

if (group == 2) {
    document.getElementById("nome").setAttribute("readonly", "true")
    document.getElementById("avaliacao").setAttribute("readonly", "true")
    document.getElementById("descricao").setAttribute("readonly", "true")
    document.getElementById("preco").setAttribute("readonly", "true")
}

async function getProduct() {

    const result = await fetch(`/produto/${idProduto}`).then(response => response.json())

    produto = {
        "id": result.id,
        "nome": result.nome,
        "avaliacao": result.avaliacao,
        "descricao": result.descricao,
        "preco": result.preco,
        "quantidade": result.quantidade,
        "ativo": result.ativo
    }
    document.getElementById("nome").value = result.nome
    document.getElementById("avaliacao").value = result.avaliacao
    document.getElementById("descricao").value = result.descricao
    document.getElementById("preco").value = result.preco
    document.getElementById("quantidade").value = result.quantidade
}

getProduct()

const botaoSalvar = document.getElementById("btn-salvar");
botaoSalvar.addEventListener("click", async (e) => {
    e.preventDefault()

    produto = {
        "id": produto.id,
        "nome": document.getElementById("nome").value,
        "avaliacao": document.getElementById("avaliacao").value,
        "descricao": document.getElementById("descricao").value,
        "preco": document.getElementById("preco").value,
        "quantidade": document.getElementById("quantidade").value,
        "ativo": produto.ativo
    }

    const result = await fetch(`/produto/${idProduto}`, {
        method: "PUT",
        headers: {
            'Content-Type':
                'application/json;charset=utf-8'
        },
        body: JSON.stringify(produto),

    })
    if (result.status == 200) {

        alert("Produto atualizado com sucesso!")
        window.close()
    } else {
        document.querySelector("body").style = "background-color:#ffcbcb;"
        alert("Falha ao atualizar produto\nTente novamente")
    }

})
