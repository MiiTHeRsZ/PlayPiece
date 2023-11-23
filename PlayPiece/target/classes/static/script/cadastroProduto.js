//registro de imagens
let boxImagens = document.getElementById("input_imagens"),
    inputImagens = document.querySelector("#imagens"),
    form = document.getElementById("form")
let listaInput = []

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
            listaInput.push(files[i])
    }

    mostarImagensInput()
})

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
        imagens += `<div class="imagem-input">
        <img src="${URL.createObjectURL(imagem)}" alt="imagem">
        <span class="fav" onclick="favoritarInput(${index})">&#10025;</span>
        <span class="del" onclick="removerInput(${index})">&times;</span>
        </div>`
    })

    boxImagens.innerHTML += imagens
    inputImagens.value = ""
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
function limparImagensInput() {
    let imagens = document.querySelectorAll(".imagem-input")
    imagens.forEach((imagem) => {
        imagem.remove()
    })
}

//cadastrar produto

const btnSalvarProduto = document.querySelector("#form")
btnSalvarProduto.addEventListener("submit", async (e) => {
    e.preventDefault()

    var hasFav = false

    listaInput.forEach(async (imagem, index) => {
        let el = document.querySelectorAll(".fav")
        el.forEach((item, i) => {
            el[index].textContent == "✭" ? hasFav = true : ""
        })
    });

    if (hasFav) {
        let produto = {
            "nome": document.querySelector("#nome").value,
            "avaliacao": document.querySelector("#avaliacao").value,
            "descricao": document.querySelector("#descricao").value,
            "preco": document.querySelector("#preco").value,
            "quantidade": document.querySelector("#quantidade").value
        }

        let result = await fetch("/produto", {
            method: "POST",
            headers: {
                'Content-Type':
                    'application/json;charset=utf-8'
            },
            body: JSON.stringify(produto),

        })
        if (result.status == 201) {

            alert("Produto criado com sucesso! Aguarde até a janela se fechar")
        } else {
            document.querySelector("body").style = "background-color:#ffcbcb;"
            alert("Falha ao cadastrar Produto\nTente novamente")
        }


        listaInput.forEach(async (imagem, index) => {
            let fav = 0;
            let el = document.querySelectorAll(".fav")
            el.forEach((item, i) => {
                el[index].textContent == "✭" ? fav = 1 : fav = 0
            })
            let formData = new FormData()
            formData.append("imageFile", imagem)
            const produto = await fetch(`/produto`).then(data => data.json())
            let separa = imagem.type.split("/")
            let tipo = separa[1]
            const resultImagem = await fetch(`/imagem/${produto[0].produtoId}?nome=${index.toString()}.${tipo}&fav=${fav}`, {
                method: "POST",
                body: formData
            })
            if (resultImagem.status == "201") {

                console.log(resultImagem.url)
            } else {
                alert("falha ao criar imagens. Tente alterar o produto em breve")
            }

        });
    } else {
        alert("Você deve selecionar uma imagem para ser a padrão!")
    }
})



