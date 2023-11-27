const urlParams = new URLSearchParams(window.location.search);
group = urlParams.get('group')
idProduto = urlParams.get('id')
let imagens = [];

//#region imagens

let boxImagens = document.getElementById("input_imagens"),
    inputImagens = document.querySelector("#imagens"),
    form = document.getElementById("form")
let listaInput = []

async function getImagens() {

    let produto = await fetch(`/produto/${idProduto}`).then(response => response.json())

    produto.listaImagens.forEach(imagem => {
        imagens.push(imagem)
    })

    imagens.forEach(async (imagem, index) => {

        let link = imagem.caminho.split("/")
        let caminhoNovo = link[5] + "/" + link[6] + "/" + link[7] + "/" + link[8]
        let novoNome = link[8].split(".")

        const imagemR = await fetch(`/./${caminhoNovo}`).then(data => data.blob()).then(blob => new File([blob], novoNome[0], { type: blob.type }))

        listaInput.push(imagemR)
        if (index == imagens.length - 1) {
            mostarImagensInput()
        }
    });

    inputImagens.addEventListener("change", () => {
        const files = inputImagens.files

        for (let i = 0; i < files.length; i++) {
            listaInput.push(files[i])
            imagens.push(
                { padrao: false }
            )
        }
        mostarImagensInput()
    })

    boxImagens.addEventListener("drop", (e) => {
        e.preventDefault()

        const files = e.dataTransfer.files
        for (let i = 0; i < files.length; i++) {
            if (!files[i].type.match("image")) continue

            if (listaInput.every(imagem => imagem.name != files[i].name)) {

                listaInput.push(files[i])
                imagens.push(
                    { padrao: false }
                )
            }
        }

        mostarImagensInput()
    })
}

getImagens()
function mostarImagensInput() {
    limparImagensInput()

    let a = document.querySelector("#imagens").files
    let imgs = ""
    let text = document.querySelector("#input_imagens p")
    text.style.display = "none"
    if (listaInput.length == 0) {
        let text = document.querySelector("#input_imagens p")
        text.style.display = "inline"
    }
    listaInput.forEach((imagem, index) => {

        let divImagens = document.createElement("div")
        divImagens.classList.add("imagem-input")
        divImagens.innerHTML = `<img src="${URL.createObjectURL(imagem)}" alt="imagem">
        <span class="fav" onclick="favoritarInput(${index})">${imagens[index].padrao ? "&#10029;" : "&#10025;"}</span>
        <span class="del" onclick="removerInput(${index})">&times;</span>`

        boxImagens.appendChild(divImagens)
    })
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
        "id": result.produtoId,
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
        "id": produto.produtoId,
        "nome": document.getElementById("nome").value,
        "avaliacao": document.getElementById("avaliacao").value,
        "descricao": document.getElementById("descricao").value,
        "preco": document.getElementById("preco").value,
        "quantidade": document.getElementById("quantidade").value,
        "ativo": produto.ativo
    }

    let result = null
    let status = null
    result = await fetch(`/produto/${idProduto}`, {
        method: "PUT",
        headers: {
            'Content-Type':
                'application/json;charset=utf-8'
        },
        body: JSON.stringify(produto),

    }).then((data) => {
        status = data.status
        return data.json()
    })
    if (status == 200) {

        alert("Produto atualizado com sucesso! Aguarde até a janela se fechar")

        var fav = 0;
        let formData = new FormData()
        listaInput.forEach(imagem => {
            let el = document.querySelectorAll(".fav")
            el.forEach((item, i) => {
                if (item.textContent == "✭") {
                    fav = i
                }
            })
            formData.append("imageFiles", imagem)
        });

        const resultImagem = await fetch(`/imagem/${result.produtoId}?tipo=jpeg&fav=${fav}`, {
            method: "POST",
            body: formData
        })
        if (resultImagem.status == "201") {

            window.close()
        } else {
            alert("falha ao criar imagens. Tente alterar o produto em breve")
            window.close()
        }

    } else {
        document.querySelector("body").style = "background-color:#ffcbcb;"
        alert("Falha ao atualizar Produto\nTente novamente")
    }
})
