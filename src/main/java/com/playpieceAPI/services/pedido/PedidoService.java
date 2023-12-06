package com.playpieceAPI.services.pedido;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playpieceAPI.models.carrinho.CarrinhoModel;
import com.playpieceAPI.models.carrinho.ItemCarrinhoModel;
import com.playpieceAPI.models.pedido.AtualizarStatusDTO;
import com.playpieceAPI.models.pedido.ItemPedidoModel;
import com.playpieceAPI.models.pedido.PedidoModel;
import com.playpieceAPI.models.pedido.enums.StatusPedidoEnum;
import com.playpieceAPI.repositories.ClienteRespository;
import com.playpieceAPI.repositories.pedido.PedidoRepository;
import com.playpieceAPI.services.EnderecoService;
import com.playpieceAPI.services.carrinho.CarrinhoService;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ItemPedidoService itemPedidoService;
    @Autowired
    private CarrinhoService carrinhoService;
    @Autowired
    private ClienteRespository clienteRespository;
    @Autowired
    private EnderecoService enderecoService;

    public PedidoModel importarCarrinho(Long clienteId, Long enderecoId, Double frete, String modoPagamento) {

        try {
            PedidoModel pedido = new PedidoModel();
            var cliente = clienteRespository.findById(clienteId).get();
            pedido.setCliente(cliente);
            var endereco = enderecoService.getEnderecoById(enderecoId);
            pedido.setEnderecoEntrega(endereco);
            pedido = pedidoRepository.save(pedido);
            CarrinhoModel carrinho = carrinhoService.getCarrinhoAtivoByClienteId(clienteId);
            var listaItens = carrinho.getItens();
            Double total = 0.00;
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
                throw e;
            }

            if (cliente.getPedidos() == null) {
                cliente.setPedidos(new ArrayList<PedidoModel>());
            }

            pedido.getCliente().getPedidos().add(pedido);
            pedido.setModoPagamento(modoPagamento);
            pedido.setValorFrete(frete);
            pedido.setStatusPagamento(StatusPedidoEnum.AGUARDANDO_PAGAMENTO.getValor());
            pedido.setValorTotal(total);
            pedido.setDataPedido(new Date());
            return pedidoRepository.save(pedido);

        } catch (Exception e) {
            throw e;
        }
    }

    public List<PedidoModel> getListaPedidosCliente(Long id) {

        try {
            List<PedidoModel> pedidos = new ArrayList<>();
            pedidos = pedidoRepository.findAllByClienteIdOrderByPedidoIdDesc(id);
            return pedidos;
        } catch (Exception e) {
            throw e;
        }
    }

    public PedidoModel getPedidoById(Long id) {

        try {
            PedidoModel pedido = new PedidoModel();
            pedido = pedidoRepository.findById(id).get();
            return pedido;
        } catch (Exception e) {
            throw e;
        }
    }

    public PedidoModel atualizarStatus(AtualizarStatusDTO novoPedido) {
        try {

            PedidoModel pedido = pedidoRepository.findById(novoPedido.getId()).get();

            String sigla = null;

            for (StatusPedidoEnum item : StatusPedidoEnum.values()) {
                if (novoPedido.getSigla().equalsIgnoreCase(item.getValor())) {
                    sigla = item.getValor();
                }
            }

            if (sigla == null)
                throw new RuntimeException("Status não existe");

            pedido.setStatusPagamento(sigla);

            return pedidoRepository.save(pedido);

        } catch (Exception e) {
            throw e;
        }
    }

    public List<PedidoModel> getPedidos() {

        try {
            List<PedidoModel> pedidos = new ArrayList<>();
            pedidos = pedidoRepository.findAllOrderByPedidoIdDesc();
            return pedidos;
        } catch (Exception e) {
            throw e;
        }
    }
}
