package com.playpieceAPI.services.pedido;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.playpieceAPI.models.carrinho.CarrinhoModel;
import com.playpieceAPI.models.carrinho.ItemCarrinhoModel;
import com.playpieceAPI.models.pedido.ItemPedidoModel;
import com.playpieceAPI.models.pedido.PedidoModel;
import com.playpieceAPI.repositories.ClienteRespository;
import com.playpieceAPI.repositories.pedido.PedidoRepository;
import com.playpieceAPI.services.EnderecoService;
import com.playpieceAPI.services.carrinho.CarrinhoService;

@Service
public class PedidoService {

    final PedidoRepository pedidoRepository;
    final ItemPedidoService itemPedidoService;
    final CarrinhoService carrinhoService;
    final ClienteRespository clienteRespository;
    final EnderecoService enderecoService;

    public PedidoService(PedidoRepository pedidoRepository, ItemPedidoService itemPedidoService,
            CarrinhoService carrinhoService, ClienteRespository clienteRespository, EnderecoService enderecoService) {
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoService = itemPedidoService;
        this.carrinhoService = carrinhoService;
        this.clienteRespository = clienteRespository;
        this.enderecoService = enderecoService;

    }

    public PedidoModel importarCarrinho(Long clienteId, Long enderecoId, Double frete, String modoPagamento) {
        PedidoModel pedido = new PedidoModel();
        var cliente = clienteRespository.findById(clienteId).get();
        pedido.setCliente(cliente);
        pedido.setEnderecoEntrega(cliente.getEnderecoFaturamento());
        pedido = pedidoRepository.save(pedido);
        CarrinhoModel carrinho = carrinhoService.getCarrinhoAtivoByClienteId(clienteId);
        var listaItens = carrinho.getItens();
        var total = 0;
        try {

            for (ItemCarrinhoModel itemCarrinho : listaItens) {
                var itemPedido = itemPedidoService.criarItemPedido(itemCarrinho, pedido);
                if (pedido.getItens() == null) {
                    pedido.setItens(new ArrayList<ItemPedidoModel>());
                }
                pedido.getItens().add(itemPedido);
                total += itemCarrinho.getQuantidade() * itemCarrinho.getProduto().getPreco();
            }
            total += frete;

            carrinho = carrinhoService.limparCarrinho(carrinho);

        } catch (Exception e) {
            System.out.println(e);
        }
        if (cliente.getPedidos() == null) {
            cliente.setPedidos(new ArrayList<PedidoModel>());
        }

        var endereco = enderecoService.getEnderecoById(enderecoId);

        pedido.getCliente().getPedidos().add(pedido);
        pedido.setEnderecoEntrega(endereco);
        pedido.setModoPagamento(modoPagamento);
        pedido.setValorFrete(frete);
        pedido.setStatusPagamento("Aguardando Pagamento");
        pedido.setValorTotal(total);
        pedido.setDataPedido(new Date());
        return pedidoRepository.save(pedido);
    }

    public List<PedidoModel> getListaPedidosCliente(Long id) {
        List<PedidoModel> pedidos = new ArrayList<>();

        try {
            pedidos = pedidoRepository.findAllByClienteId(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return pedidos;
    }

    public PedidoModel getPedidoById(Long id) {
        PedidoModel pedido = new PedidoModel();

        try {
            pedido = pedidoRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e);
        }
        return pedido;
    }

}
