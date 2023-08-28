async function logar() {
  var nickname = document.getElementById("nickname").value;
  var password = document.getElementById("password").value;

  const response = await fetch("http://localhost:8080/usuario");
  const data = await response.json();

  console.log(data);

  /*try {
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
  } catch (error) {
    console.error("Erro ao fazer login:", error);
  }
}
*/

const response = await fetch("http://localhost:8080/usuario");
const data = await response.json();

console.log(data);

if (nickname == "henrick@email.com" && password == "12345678") {
  location.href = "/pages/backoffice.html";
} else {
  alert("usuario ou senha incorreta");
  console.log("erro");
}
