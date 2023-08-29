document.getElementById("mostrarTabela").addEventListener("click", () => {
  var tabela = document.getElementById("tabelaProd");

  teste();
  if (tabela.style.display === "none") {
    tabela.style.display = "table"; // Corrigido aqui
  } else {
    tabela.style.display = "none"; // Corrigido aqui
  }
});

//   document.getElementById('mostrarTabela').addEventListener('click', function(event){
//     event.preventDefault();

async function teste() {
  const response = await fetch("http://localhost:8080/pessoa").then((data) =>
    data.json()
  );

  console.log(response);
}

var pessoa = {
  nome: response[i].nome,
  Email: response[i].Email,
};

function criaTabela(conteudo) {
  let tabela = document.createElement("table");
  let tr = document.createElement("tr");
  let th = document.createElement("th");
  
}
