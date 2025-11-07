package com.delivery_api.Projeto.Delivery.API.repository;

import com.delivery_api.Projeto.Delivery.API.entity.Pedido;
import com.delivery_api.Projeto.Delivery.API.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteIdOrderByDataPedidoDesc(Long clienteId);

    Pedido findByNumeroPedido(String numeroPedido);

    List<Pedido> findByRestauranteIdOrderByDataPedidoDesc(Long restauranteId);

    List<Pedido> findByStatus(StatusPedido status); //

    List<Pedido> findTop10ByOrderByDataPedidoDesc(); //

    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim); //

}
