//registro de imagens
let boxImagens = document.getElementById("input_imagens")
var inputImagens = document.getElementById("imagens")
let form = document.getElementById("form")
let listaInput = []

inputImagens.addEventListener("change", () => {
    let imagens = inputImagens.files
    for (let i = 0; i < imagens.length; i++) {
        listaInput.push(inputImagens.files[i])
    }

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
    let imagens = ""
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
        <span class="fav" onclick="favoritarInput(${index})">&#10025;</span>
        <span class="del" onclick="removerInput(${index})">&times;</span>`
        boxImagens.appendChild(divImagens)
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
    var produtoId;
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
        let result = null
        let status = null
        result = await fetch("/produto", {
            method: "POST",
            headers: {
                'Content-Type':
                    'application/json;charset=utf-8'
            },
            body: JSON.stringify(produto),

        }).then((data) => {
            status = data.status
            return data.json()
        })
        if (status == 201) {

            alert("Produto criado com sucesso! Aguarde até a janela se fechar")

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

                window.open("./backoffice.html", "_self");
            } else {
                alert("falha ao criar imagens. Tente alterar o produto em breve")
                window.open("./backoffice.html", "_self");
            }

        } else {
            document.querySelector("body").style = "background-color:#ffcbcb;"
            alert("Falha ao cadastrar Produto\nTente novamente")
        }
    } else {
        alert("Você deve selecionar uma imagem para ser a padrão!")
    }
})



