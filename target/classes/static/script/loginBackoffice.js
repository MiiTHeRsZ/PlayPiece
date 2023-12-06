
const form = document.getElementById("form");
form.onclick = (e) => e.preventDefault();
const loader = document.getElementById("loading")

function getCookie(nome) {
	return Cookies.get(nome)
}
function setCookie(nome, info, exdays) {
	Cookies.set(nome, info, exdays)
}

function checkCookie(nome) {
	var cargo = getCookie(nome);
	if (cargo != undefined) {
		var resp = confirm("Usuário logado. Redirecionando para o BackOffice\nAo cancelar, você estará encerrando sua sessão")
		if (resp == 1) {

			location.href = `./backoffice.html`
		} else if (resp == 0) {
			Cookies.remove("cargo")
			Cookies.remove("jobSession")
		}
	}
}

sessao = checkCookie("cargo")

const conect_api = async () => {
	const nickname = document.getElementById("nickname").value;
	const password = (document.getElementById("password").value);

	let login = {
		"email": nickname,
		"senha": password
	}
	// faz a busca dos dados fornecidos e depois converte para json
	let result = null
	let status = null
	try {
		result = await fetch(`/usuario/login`, {
			method: "PUT",
			headers: {
				'Content-Type': 'application/json;charset=utf-8'
			},
			body: JSON.stringify(login),
		}).then((data) => {
			status = data.status
			loader.classList.remove("display")
			return data.json()
		})

	} catch (error) {
	}

	let cargoCode = (cargo) => {
		let cont = 1
		let code = cargo;
		for (cont = 1; cont <= 16; cont++) {
			code = String(code).hashCode()
		}
		return code
	}

	// validar se usuario e senha digitados batem com o usuario e senha cadastrado no banco de dados
	if (status == 200) {
		setCookie("cargo", cargoCode(result[3]), 1)
		setCookie("jobSession", result[0], 1)
		location.href = `./backoffice.html`
	} else {
		setTimeout(() => {
			alert("Usuário e/ou senha inválido(s)!");
		}, 100)
	}

}

function mostrarLoad() {
	loader.classList.add("display")
	setTimeout(() => {
		loader.classList.remove("display")
	}, 10000);
}

const btn = document.getElementById("form__btn-entrar");
btn.addEventListener("click", () => {
	mostrarLoad()
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