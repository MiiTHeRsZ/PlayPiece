


function menu() {

    let nome_perfil = document.getElementById("nome-perfil");
    let login_perfil = document.getElementById("login-perfil");
    let sair = document.getElementById("sair");

    if (idCliente == undefined) {
        nome_perfil.innerHTML = "Seja Bem-Vindo(a)!";
        login_perfil.innerHTML = "Login";
        login_perfil.href = "./loginCliente.html";
        sair.style.display = 'none';
    }

    let carrinho = sessionStorage.getItem('carrinho');

    if (carrinho != "" && carrinho != null && carrinho != undefined) {
        let cont = 0;

        if (carrinho.length > 3) {
            carrinho.split(",").forEach(item => {
                cont++;
            });
        } else {
            cont++;
        }

        document.getElementById("notificacaoCarrinho").innerHTML = cont;
        document.getElementById("notificacaoCarrinho").style.display = "inline";
    }
}
menu();

var jaCpf = null // variavel que retorna se o CPF já existe em um cadastro ou nao

// verifica se o CPF inserido já não pertence em algum cadastro
document.getElementById("cpf").onchange = async (e) => {
    const checkCpf = await (await fetch(`/cliente/search?cpf=${document.getElementById("cpf").value}`)).status == 200 ? true : false
    if (!checkCpf) { // cpf não existente
        document.getElementById("err-cpf").style.display = "none"
        verificaInformacao(e.target.id);
        jaCpf = false
    } else { // cpf já existente
        document.getElementById("err-cpf").innerHTML = "CPF já cadastrado"
        document.getElementById("cpf").classList.add('fail')
        document.getElementById("err-cpf").style.display = "block"
        jaCpf = true
    }
}

// valida tamanho do nome/ sobrenome a cada mudança 
document.getElementById("nome").onchange = (e) => {
    if (!verificaNome(e.target.id)) {
        alert("Preencha o campo 'Nome' com apenas um nome e um sobrenome\ncontendo ao menos 3 letras cada!.");
        verificaInformacao();
    }
}

// valida o email fornecido a cada mudança 
document.getElementById("email").onchange = (e) => {
    if (e.target.value !== "") {
        verificaInformacao(e.target.id);
    }
}

// verifica se o campo está preenchido (foi selecionado) 
document.getElementById("genero").onchange = (e) => {
    if (e.target.value !== "") {
        verificaInformacao(e.target.id);
    }
}

// valida o cep do endereço faturamento
document.getElementById("cep-faturamento").onchange = (e) => {
    if (e.target.value !== "") {
        buscarDadosCep("cep-faturamento");
        verificaEnderecos()
        verificaInformacao();
    }
}

// valida o cep do endereço entrega caso a opção (Tornar este meu endereço de entrega, no endereço faturamento) não esteja marcada
document.getElementById("cep-entrega").onchange = (e) => {
    if (e.target.value !== "") {
        buscarDadosCep("cep-entrega");
        verificaEnderecos()
        verificaInformacao();
    }
}

// verifica e valida se a senha fornecida esta dentro dos padrões
document.getElementById("senha").onchange = (e) => {
    if (e.target.value !== "") {
        verificaInformacao(e.target.id);
    }
}

// verifica e valida se a senha esta dentro dos padrões e se confere com a senha anteriormente fornecida
document.getElementById("confirmaSenha").onchange = (e) => {
    if (e.target.value !== "") {
        verificaInformacao(e.target.id);
    }
}

// validação caso a flag (Tornar este meu endereço de entrega, no endereço faturamento) esteja marcada
document.getElementById("isEntrega").addEventListener("change", () => {
    if (document.getElementById("isEntrega").checked) {
        document.getElementById("cep-entrega").classList.remove("fail")
        document.getElementById("cep-entrega").value = document.getElementById("cep-faturamento").value
        document.getElementById("cep-entrega").setAttribute("readonly", "true")
        document.getElementById("logradouro-entrega").value = document.getElementById("logradouro-faturamento").value
        document.getElementById("logradouro-entrega").setAttribute("readonly", "true")
        document.getElementById("numero-entrega").value = document.getElementById("numero-faturamento").value
        document.getElementById("numero-entrega").setAttribute("readonly", "true")
        document.getElementById("complemento-entrega").value = document.getElementById("complemento-faturamento").value
        document.getElementById("complemento-entrega").setAttribute("readonly", "true")
        document.getElementById("bairro-entrega").value = document.getElementById("bairro-faturamento").value
        document.getElementById("bairro-entrega").setAttribute("readonly", "true")
        document.getElementById("cidade-entrega").value = document.getElementById("cidade-faturamento").value
        document.getElementById("cidade-entrega").setAttribute("readonly", "true")
        document.getElementById("uf-entrega").value = document.getElementById("uf-faturamento").value
        document.getElementById("uf-entrega").setAttribute("readonly", "true")
    } else {
        document.getElementById("cep-entrega").classList.add("fail")
        document.getElementById("cep-entrega").value = ""
        document.getElementById("cep-entrega").removeAttribute("readonly")
        document.getElementById("logradouro-entrega").value = ""
        document.getElementById("numero-entrega").value = ""
        document.getElementById("numero-entrega").removeAttribute("readonly")
        document.getElementById("complemento-entrega").value = ""
        document.getElementById("complemento-entrega").removeAttribute("readonly")
        document.getElementById("bairro-entrega").value = ""
        document.getElementById("cidade-entrega").value = ""
        document.getElementById("uf-entrega").value = ""
    }
})

// vetor que pega o dia atual, formatando numero do dia e do mes, caso o valor seja menor que 10
var hojeSemForm = new Date();
const hoje = {
    "dia": hojeSemForm.getDate() < 10 ? "0" + hojeSemForm.getDate() : hojeSemForm.getDate(),
    "mes": Number(hojeSemForm.getMonth() < 10 ? "0" + hojeSemForm.getMonth() : hojeSemForm.getMonth()) + 1,
    "ano": hojeSemForm.getFullYear(),
    "string": ""
}
// retorna a data como string no padrao yyyy-mm-dd
hoje.string = hoje.ano + "-" + hoje.mes + "-" + hoje.dia
// no campo tipo date, que tem o id (dt_nasc), seta o seu valor maximo de escolha para o dia atual
document.getElementById("dt_nasc").setAttribute("max", hoje.string)

// função que verifica se os campos não estão nulos e se estão formatados corretamente
async function verificaInformacao(input) {
    let senha = document.getElementById("senha").value
    let confirmaSenha = document.getElementById("confirmaSenha").value
    let nomeret = verificaNome();
    let cpf = document.getElementById("cpf").value
    let cpfret = validaCPF(cpf)
    let emailret = await verificaEmail();
    let genero = document.getElementById("genero").value;
    let endereco = verificaEnderecos();
    document.getElementById("senha").value

    let ret = verificarSenhas(senha, confirmaSenha)

    // console.log("v " + endereco);

    // verifica se as senhas informadas são validas ou não
    if (ret) {
        document.getElementById("confirmaSenha").classList.remove("fail")
    } else {
        document.getElementById("confirmaSenha").classList.add('fail')
    }

    if (cpfret && !jaCpf) { // verifica se o cpf fornecido é valido e se já existe
        document.getElementById("cpf").classList.remove("fail")
        document.getElementById("err-cpf").style.display = "none"
    } else {
        document.getElementById("cpf").classList.add('fail')
        if (!jaCpf) {
            document.getElementById("err-cpf").innerHTML = "CPF inválido"
        }
        document.getElementById("err-cpf").style.display = "inline"
    }

    // verifica se o email já existe ou não
    if (emailret) {
        document.getElementById("email").classList.remove("fail")
    } else {
        document.getElementById("email").classList.add('fail')
    }

    // verifica se o endereço é valido ou não
    if (endereco) {
        document.getElementById("cep-entrega").classList.remove("fail")
        document.getElementById("cep-faturamento").classList.remove("fail")
    } else {
        document.getElementById("cep-entrega").classList.add('fail')
        document.getElementById("cep-faturamento").classList.add('fail')
    }

    // verifica se as informações foram preechidas corratamente ou não para liberar o botao de salvar
    // check: senha ok; nome ok; cpf ok; email ok; genero ok
    if (ret && nomeret && cpfret && emailret && genero != 0) {
        document.getElementById("btn-salvar").removeAttribute("disabled")
        document.getElementById("btn-salvar").style.cursor = 'pointer'
    } else {
        document.getElementById("btn-salvar").setAttribute("disabled", "true")
        document.getElementById("btn-salvar").style.cursor = 'not-allowed'
    }

}

// verifica se o email forneido já possui cadastro
async function verificaEmail() {
    let email = document.getElementById("email").value;
    let comp = null

    comp = await fetch(`/cliente/search?email=${email}`).then((data) => {
        const alert = document.querySelector(".mail");
        if (data.status == 404) {
            alert.style.display = "none"
            return false
        } else { // já possui email cadastrado
            const alert = document.querySelector(".mail");
            alert.style.display = "inline"
            return data.json();
        }
    })

    if (email == comp.email)
        return false

    return true
}

// verifica se o endereço fornecido é valido atraves da função buscarDadosCep()
function verificaEnderecos() {
    buscarDadosCep(document.getElementById("cep-faturamento").value)
    buscarDadosCep(document.getElementById("cep-entrega").value)

    let endEntrega = document.getElementById("cep-entrega").value
    let endFatutamento = document.getElementById("cep-faturamento").value

    let ruaEntrega = document.getElementById("logradouro-entrega").value
    let ruaFatutamento = document.getElementById("logradouro-faturamento").value

    if (endFatutamento == "" || endEntrega == "" || ruaEntrega == "" || ruaFatutamento == "") {
        if (endFatutamento == "" && endEntrega == "") {
            // console.log("1 true");
            return true
        }
        // console.log("1 false");
        return false;
    }

    // console.log("2 true");
    return true;
}

// logica que oculta ou não a senha digitada pelo cliente
let showPassIcon = document.querySelector("#showPassword")
showPassIcon.addEventListener("click", () => {
    if (showPassIcon.getAttribute("class") == "fa-solid fa-eye-slash") {
        showPassIcon.removeAttribute("class")
        showPassIcon.setAttribute("class", "fa-solid fa-eye")
        let passInput = document.querySelectorAll(".passInput")
        for (let i = 0; i < passInput.length; i++) {
            passInput[i].setAttribute("type", "text")
        }
    } else {
        showPassIcon.removeAttribute("class")
        showPassIcon.setAttribute("class", "fa-solid fa-eye-slash")
        let passInput = document.querySelectorAll(".passInput")
        for (let i = 0; i < passInput.length; i++) {
            passInput[i].setAttribute("type", "password")
        }
    }
})

// lógica por tras do botão 'Cadastrar', permitir que o cliente salve seus dados corretamente
const botaoSalvar = document.getElementById("btn-salvar");
botaoSalvar.addEventListener("click", async (e) => {
    e.preventDefault()

    var listaEndereco = null;
    // caso a opção (Tornar este meu endereço de entrega) não esteja marcada, coletar as informações do  
    // endereço entrega do cliente e armazenar no vetor listaEndereco
    if (!document.getElementById("isEntrega").checked) {
        listaEndereco = [{
            "cep": document.getElementById("cep-entrega").value,
            "logradouro": document.getElementById("logradouro-entrega").value,
            "numero": document.getElementById("numero-entrega").value,
            "complemento": document.getElementById("complemento-entrega").value,
            "bairro": document.getElementById("bairro-entrega").value,
            "cidade": document.getElementById("cidade-entrega").value,
            "uf": document.getElementById("uf-entrega").value,
            "padrao": true,
            "ativo": true
        }]
    }

    document.getElementById("cep-entrega").classList.remove("fail")
    // coletar todas as informações do cliente e armazenar no vetor cliente
    let cliente = {
        "cpf": document.getElementById("cpf").value,
        "nome": document.getElementById("nome").value,
        "dt_nascimento": document.getElementById("dt_nasc").value,
        "genero": document.getElementById("genero").value,
        "email": document.getElementById("email").value,
        "enderecoFaturamento": {
            "cep": document.getElementById("cep-faturamento").value,
            "logradouro": document.getElementById("logradouro-faturamento").value,
            "numero": Number(document.getElementById("numero-faturamento").value),
            "complemento": document.getElementById("complemento-faturamento").value,
            "bairro": document.getElementById("bairro-faturamento").value,
            "cidade": document.getElementById("cidade-faturamento").value,
            "uf": document.getElementById("uf-faturamento").value,
            "padrao": listaEndereco != null ? false : true,
            "ativo": true
        },
        "listaEndereco": listaEndereco,
        "senha": document.getElementById("senha").value,
        "ativo": true
    }
    console.table(cliente)

    // método POST que envia os dados para o backend
    const result = await fetch("/cliente", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(cliente),
    })

    if (result.status == 201) {

        alert("Cliente criado com sucesso!")
        location.href = "./loginCliente.html"
    } else {
        document.querySelector("body").style = "background-color:#ffcbcb;"
        alert("Falha ao cadastrar cliente\nTente novamente")
    }
})

// função que verifica se as senhas fornecidas são validas
function verificarSenhas(senha, confirmaSenha) {
    let alert = document.querySelector(".alert-senha")
    if (senha !== confirmaSenha || confirmaSenha == "" || confirmaSenha.length < 8 || confirmaSenha.length > 25) {
        if (confirmaSenha == "" && senha == "") {
            return true
        }
        else if (senha !== confirmaSenha) {
            alert.style.display = "inline"
            alert.innerHTML = "Senhas não batem"
            return false
        } else if (confirmaSenha.length < 8 || confirmaSenha.length > 25) {
            alert.style.display = "inline"
            alert.innerHTML = "Senha deve conter de 8 até 25 caracteres"
            return false
        }
    } else {
        alert.innerHTML = ""
        alert.style.display = "none"
        return true
    }
}

// função responsavel por realizar o calculo dos numeros fornecidos e validar se é um CPF válido ou não
function validaCPF(cpf) {
    if (cpf == "") {
        return true
    }
    var Soma = 0
    var Resto

    var strCPF = String(cpf).replace(/[^\d]/g, '')

    if (strCPF.length !== 11)
        return false

    if ([
        '00000000000',
        '11111111111',
        '22222222222',
        '33333333333',
        '44444444444',
        '55555555555',
        '66666666666',
        '77777777777',
        '88888888888',
        '99999999999',
    ].indexOf(strCPF) !== -1)
        return false

    for (i = 1; i <= 9; i++)
        Soma = Soma + parseInt(strCPF.substring(i - 1, i)) * (11 - i);

    Resto = (Soma * 10) % 11

    if ((Resto == 10) || (Resto == 11))
        Resto = 0

    if (Resto != parseInt(strCPF.substring(9, 10)))
        return false

    Soma = 0

    for (i = 1; i <= 10; i++)
        Soma = Soma + parseInt(strCPF.substring(i - 1, i)) * (12 - i)

    Resto = (Soma * 10) % 11

    if ((Resto == 10) || (Resto == 11))
        Resto = 0

    if (Resto != parseInt(strCPF.substring(10, 11)))
        return false

    return true
}

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
                }
                reject(erro);
            }
        }
        xhr.send();
    });
}

function getValidCep(input) {
    return new Promise((resolve, reject) => {
        const numeroCep = document.getElementById(input).value;

        if (numeroCep.length === 8) {
            resolve(numeroCep);
        } else {
            reject(new Error("CEP inválido"));
        }
    });
}

// o campo 'Nome' com apenas um nome e um sobrenome contendo ao menos 3 letras cada!.
function verificaNome() {
    let nome = document.getElementById("nome").value.split(" ");
    let nomeValido;
    nome.forEach(palavra => {
        if (palavra.length < 3 || nome.length < 2 || nomeValido == false) {
            nomeValido = false;
        } else {
            nomeValido = true;
        }
    });
    if (nome.length == 3) {
        nomeValido = false;
    }
    return nomeValido;
}

// função que valida se o cep fornecido é verdadeiro ou não
async function buscarDadosCep(input) {
    try {
        const alert = document.querySelector(`.alert-${input}`);
        if (input === "cep-faturamento") {
            const valorCep = await getValidCep(input);
            const dadosArray = await getJson(`https://viacep.com.br/ws/${valorCep}/json/`);
            if (dadosArray.erro === true) {
                alert.innerHTML = "CEP inválido!";
                alert.style.display = "inline";
                document.getElementById("cep-faturamento").classList.add('fail')

                document.getElementById('logradouro-faturamento').value = "";
                document.getElementById('bairro-faturamento').value = "";
                document.getElementById('cidade-faturamento').value = "";
                document.getElementById('uf-faturamento').value = "";
            } else {
                alert.innerHTML = "";
                alert.style.display = "none";
                document.getElementById("cep-faturamento").classList.remove("fail")

                document.getElementById('logradouro-faturamento').value = dadosArray.logradouro;
                document.getElementById('bairro-faturamento').value = dadosArray.bairro;
                document.getElementById('cidade-faturamento').value = dadosArray.localidade;
                document.getElementById('uf-faturamento').value = dadosArray.uf;
            }
        } else if (input === "cep-entrega") {
            valorCep = await getValidCep(input);
            dadosArray = await getJson(`https://viacep.com.br/ws/${valorCep}/json/`);
            if (dadosArray.erro === true) {
                alert.innerHTML = "CEP inválido!";
                alert.style.display = "inline";
                document.getElementById("cep-entrega").classList.add('fail')
                document.getElementById('logradouro-entrega').value = "";
                document.getElementById('bairro-entrega').value = "";
                document.getElementById('cidade-entrega').value = "";
                document.getElementById('uf-entrega').value = "";
            } else {
                alert.innerHTML = "";
                alert.style.display = "none";
                document.getElementById("cep-entrega").classList.remove("fail")

                document.getElementById('logradouro-entrega').value = dadosArray.logradouro;
                document.getElementById('bairro-entrega').value = dadosArray.bairro;
                document.getElementById('cidade-entrega').value = dadosArray.localidade;
                document.getElementById('uf-entrega').value = dadosArray.uf;
            }
        }


    } catch (erro) {
        console.error("Erro na busca do CEP: " + erro.message + "\nVerifique se o CEP fornecido é realmente válido");
    }
}
