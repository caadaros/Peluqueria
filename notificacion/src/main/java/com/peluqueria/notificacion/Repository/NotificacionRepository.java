package com.peluqueria.notificacion.Repository;

import com.peluqueria.notificacion.Model.Notificacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByRutCliente(String rutCliente);
}
