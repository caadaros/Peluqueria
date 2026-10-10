package com.peluqueria.pago.Repository;

import com.peluqueria.pago.Model.Pago;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByIdBoleta(Long idBoleta);
    List<Pago> findByRutCliente(String rutCliente);
}
