const form = document.getElementById("form");
form.onclick = (e) => e.preventDefault();

function getCookie(nome) {
    return Cookies.get(nome)
}
function setCookie(nome, info, exdays) {
    Cookies.set(nome, info, exdays)
}

function checkCookie(nome) {
    var sessao = getCookie(nome);
    if (sessao != undefined) {
        var resp = confirm("Usuário logado. Redirecionando para o perfil\nAo cancelar, você estará encerrando sua sessão")
        if (resp == 1) {
            location.href = `./perfilCliente.html`
        } else if (resp == 0) {
            Cookies.remove("sessaoId")
        }
    }
}

sessao = checkCookie("sessaoId")

const conect_api = async () => {
    const nickname = document.getElementById("nickname").value;
    // converte senha fornecida pelo usuario e ja transforma essa senha em hashcode
    const password = document.getElementById("password").value;

    let login = {
        "email": nickname,
        "senha": password
    }
    // faz a busca dos dados fornecidos e depois converte para json
    let result = null
    let status = null
    try {
        result = await fetch(`/cliente/login`, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(login),
        }).then(function (result) {
            status = result.status;
            return result.json();
        })

    } catch (error) {
    }
    // validar se usuario e senha digitados batem com o usuario e senha cadastrado no banco de dados
    if (status == 200) {
        setCookie("sessaoId", result.id, 1)
        location.href = `../index.html`;
    } else {
        alert("Usuário e/ou senha inválido(s)!");
    }

}

const btn = document.getElementById("form__btn-entrar");
btn.addEventListener("click", () => {
    conect_api();
});

let showPassIcon = document.querySelector("#showPassword")
showPassIcon.addEventListener("click", () => {
    if (showPassIcon.getAttribute("class") == "fa-solid fa-eye-slash") {
        showPassIcon.removeAttribute("class")
        showPassIcon.setAttribute("class", "fa-solid fa-eye")
        document.getElementById("password").setAttribute("type", "text")
    }
    else {
        showPassIcon.removeAttribute("class")
        showPassIcon.setAttribute("class", "fa-solid fa-eye-slash")
        document.getElementById("password").setAttribute("type", "password")
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