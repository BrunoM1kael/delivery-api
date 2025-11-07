package com.delivery_api.Projeto.Delivery.API.repository;

import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Optional<Restaurante> findByNome(String nome);

    List<Restaurante> findByAtivoTrue();

    List<Restaurante> findByCategoria(String categoria);

    List<Restaurante> findByTaxaEntregaLessThanEqual(BigDecimal taxa); //

    List<Restaurante> findTop5ByOrderByNomeAsc();

}
