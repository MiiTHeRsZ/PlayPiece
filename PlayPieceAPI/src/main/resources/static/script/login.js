const form = document.getElementById("form");
form.onclick = (e) => e.preventDefault();

const conect_api = async () => {
  const nickname = document.getElementById("nickname").value;
  const password = (document.getElementById("password").value).hashCode();

  const result = await fetch(`http://localhost:8080/usuario/search?email=${nickname}`).then(data => data.json())

  if (nickname == result.emailUsuario && password == result.senha) {
    console.log("logado")
    location.href = `./backoffice.html?group=${result.cargo.id}`, {};
  } else {
    console.log(password)
    alert("usuario ou senha incorreta");
  }

}

const btn = document.getElementById("form__btn");
btn.addEventListener("click", () => {
  conect_api();
});

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