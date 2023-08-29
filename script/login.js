/*
async function logar() {
  

  try {
    const response = await fetch(/*onde ficará o caminho da api/*",") }*/
/*

  try {
  const response = await fetch("/api/login", {
    method: "POST",
    headers: {
    "Content-Type": "application/json",
    },
    body: JSON.stringify({ nickname, password }),
  });
	
  const data = await response.json();
	
  if (data.success) {
    document.getElementById("result").textContent = "Login bem-sucedido!";
  } else {
    document.getElementById("result").textContent =
    "Usuário ou senha incorretos.";
  }
  if (nickname == "henrick@email.com" && password == "12345678") {
    location.href = "/pages/backoffice.html";
  } else {
    alert("usuario ou senha incorreta");
    console.log("erro");
  }
  } catch (error) {
  console.error("Erro ao fazer login:", error);
  }
}
*/

const form = document.getElementById("form");
form.onclick = (e) => e.preventDefault();

const conect_api = async () => {
  const nickname = document.getElementById("nickname").value;
  const password = document.getElementById("password").value;

  console.log(nickname)
  console.log(password)

  const response = await fetch(`http://localhost:8080/acesso/access?email=${nickname}`).then(data => data.json());
  console.log(response);

  if (nickname == response.email && password == response.senha) {
    console.log("logado")
    location.href = "./backoffice.html";
  }

  alert("usuario ou senha incorreta");

}

const btn = document.getElementById("form__btn");
btn.addEventListener("click", () => {
  conect_api();
});