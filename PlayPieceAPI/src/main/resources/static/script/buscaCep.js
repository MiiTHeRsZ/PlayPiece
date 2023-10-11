// Função responsável por fazer a requisição JSON
function getJson(url) {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.onload = function () {
            if (xhr.status >= 200 && xhr.status < 400) {
                resolve(JSON.parse(xhr.responseText));
            } else {
                const erro = {
                    codigo: xhr.status,
                    mensagem: 'Erro na requisição'
                };
                reject(erro);
            }
        };
        xhr.send();
    });
}

// Função que retorna o tamanho CEP válido que é inserido
function getValidCep() {
    return new Promise((resolve, reject) => {
        const numeroCep = document.querySelector("#cep");
        const alert = document.querySelector(".alert");
        const btn = document.querySelector("#send");

        btn.addEventListener("click", function (e) {
            e.preventDefault();

            const value = numeroCep.value;

            if (value.length === 8) {
                numeroCep.classList.remove("input-error");
                // alert.style.display = "none";
                resolve(value);
            } else {
                numeroCep.classList.add("input-error");
                // alert.style.display = "inline";
                reject(new Error("CEP inválido"));
            }
        });
    });
}

// Função para buscar dados do CEP vindos em JSON
async function buscarDadosCep() {
    try {
        const cepOK = true
        const valorCep = await getValidCep();
        const dadosArray = await getJson(`https://viacep.com.br/ws/${valorCep}/json/`);
        if (dadosArray.erro === 'true') {
            console.log(dadosArray);
            alert("Erro na busca do CEP: " + erro.message + "\nVerifique se o CEP fornecido é realmente válido");
        } else {
            console.log(dadosArray);
            document.getElementById('logradouro').placeholder = dadosArray.logradouro;
            document.getElementById('bairro').placeholder = dadosArray.bairro;
            document.getElementById('cidade').placeholder = dadosArray.localidade;
            document.getElementById('unidadeFederativa').placeholder = dadosArray.uf;
        }
    } catch (erro) {
        alert("Erro na busca do CEP: " + erro.message + "\nVerifique se o CEP fornecido é realmente válido");
    }
}
// acionando o evento de busca dos dados atraves de um botao (melhor fazer isso com um botao "enviar" que fazer a requisicao automatica)
const btnEnviar = document.querySelector("#send");
btnEnviar.addEventListener("click", buscarDadosCep);




// {
//     "cep": "04775-170",
//     "logradouro": "Rua Waldemar Gomes Lingoanoti",
//     "complemento": "",
//     "bairro": "Jardim Marabá(Zona Sul)",
//     "localidade": "São Paulo",
//     "uf": "SP",
//     "ibge": "3550308",
//     "gia": "1004",
//     "ddd": "11",
//     "siafi": "7107"
// }