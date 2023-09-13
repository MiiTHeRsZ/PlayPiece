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

    console.log(listaInput)
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
        <span onclick="removerInput(${index})">&times;</span>
        </div>`
    })

    boxImagens.innerHTML += imagens
    inputImagens.value = ""
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

    let produto = {
        "nome": document.querySelector("#nome").value,
        "avaliacao": document.querySelector("#avaliacao").value,
        "descricao": document.querySelector("#descricao").value,
        "preco": document.querySelector("#preco").value,
        "quantidade": document.querySelector("#quantidade").value
    }

    const result = await fetch("http://localhost:8080/produto", {
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

    result = await fetch("http://localhost:8080/produto").then(data => data.json())

    listaInput.forEach(async (imagem, index) => {
        let formData = new FormData()
        formData.append("imageFile", imagem)
        console.log(imagem)
        const resultImagem = await fetch(`http://localhost:8080/imagem`, {
            method: "POST",
            body: formData
        })
    });

    window.close()

})



