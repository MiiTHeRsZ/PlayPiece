const urlParams = new URLSearchParams(window.location.search);
email = urlParams.get('email')

const conectAPI = async () => {
    const result = await fetch(`/cliente/search?email=${email}`).then(data => data.json());
    
    document.getElementById("nomeCliente").innerHTML = result.nome;
    
    document.getElementById("cpf").value = result.cpf;
    document.getElementById("nome").value = result.nome;
    document.getElementById("dt_nasc").value = result.dt_nascimento;
    document.getElementById("genero").value = result.genero;
    document.getElementById("email").value = result.email;
    
    document.getElementById("cepFat").value = result.enderecoFaturamento.cep;
    document.getElementById("logradouroFat").value = result.enderecoFaturamento.logradouro;
    document.getElementById("numeroFat").value = result.enderecoFaturamento.numero;
    document.getElementById("complementoFat").value = result.enderecoFaturamento.complemento;
    document.getElementById("bairroFat").value = result.enderecoFaturamento.bairro;
    document.getElementById("cidadeFat").value = result.enderecoFaturamento.cidade;
    document.getElementById("ufFat").value = result.enderecoFaturamento.uf;
    
    // ! fazer um foreach para pegar o padrão
    result.listaEndereco.forEach(endereco => {
        let opcao = document.createElement("option");
        opcao.value = endereco.id;
        opcao.textContent = endereco.logradouro + ", N° " + endereco.numero;
        document.getElementById("enderecoEntrega").appendChild(opcao);
    });

    document.getElementById("cep").value = result.listaEndereco[0].cep;
    document.getElementById("logradouro").value = result.listaEndereco[0].logradouro;
    document.getElementById("numero").value = result.listaEndereco[0].numero;
    document.getElementById("complemento").value = result.listaEndereco[0].complemento;
    document.getElementById("bairro").value = result.listaEndereco[0].bairro;
    document.getElementById("cidade").value = result.listaEndereco[0].cidade;
    document.getElementById("uf").value = result.listaEndereco[0].uf;
}
conectAPI();