const urlParams = new URLSearchParams(window.location.search);
email = urlParams.get('email')
console.log(email)

async function getUser(email) {
    let emailProfissional = document.getElementById("email_profissional")
    emailProfissional.value = email
}

getUser(email)

senha1 = document.getElementById("senha")
senha2 = document.getElementById("confirmar_senha")
function validarSenhas() {
    if (senha1.value !== senha2.value) {
        senha2.style.border = "2px solid red"
    } else {
        senha2.style.border = "2px solid black"
    }
}
senha1.onchange = validarSenhas
senha2.onchange = validarSenhas

let botao = document.getElementById("btn-salvar")
botao.addEventListener("click", async (e) => {
    e.preventDefault()


    if (senha1.value !== senha2.value) {
        alert("Senhas não batem")
    } else {
        const result = await fetch(`http://localhost:8080/acesso/${email}`, {
            method: "PATCH", headers: {
                'Content-Type':
                    'application/json;charset=utf-8'
            },
            body: senha1.value.hashCode(),
        })
        if (result.status == 200) {

            alert("Senha atualizada com sucesso!")
            window.close()
        } else {
            document.querySelector("body").style = "background-color:#ffcbcb;"
            alert("Falha ao atualizar senha\nTente novamente")
        }
    }
})

String.prototype.hashCode = function () {
    var hash = 0,
        i, chr;
    if (this.length === 0) return hash;
    for (i = 0; i < this.length; i++) {
        chr = this.charCodeAt(i);
        hash = ((hash << 5) - hash) + chr;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
}
