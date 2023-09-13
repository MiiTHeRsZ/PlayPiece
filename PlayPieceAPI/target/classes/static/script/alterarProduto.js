const urlParams = new URLSearchParams(window.location.search);
group = urlParams.get('group')
idProduto = urlParams.get('id')

if (group == 2) {
    document.getElementById("nome").setAttribute("readonly","true")
    document.getElementById("avaliacao").setAttribute("readonly","true")
    document.getElementById("descricao").setAttribute("readonly","true")
    document.getElementById("preco").setAttribute("readonly","true")
    document.getElementById("imagens").setAttribute("readonly","true")
}

async function getProduct() {

    const result = await fetch(`http://localhost:8080/produto/${idProduto}`).then(response => response.json())

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
    document.getElementById("imagens").value = result.imagens[0].caminho
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
    }

    const result = await fetch(`http://localhost:8080/produto/${idProduto}`, {
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
