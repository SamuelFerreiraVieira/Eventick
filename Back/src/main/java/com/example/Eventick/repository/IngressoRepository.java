package com.example.Eventick.repository;

import com.example.Eventick.model.Ingresso;
import com.example.Eventick.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
    Optional<Ingresso> findByCodigoValidacao(String codigoValidacao);
    java.util.List<Ingresso> findByUsuario(Long usuarioid);
    long countByEventoId(Long eventoId);
    long countByEventoIdAndUtilizadoTrue(Long eventoId);
}
