package cl.checkpoint.reservas.service;

import cl.checkpoint.reservas.model.Reserva;
import cl.checkpoint.reservas.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository repo;

    public ReservaService(ReservaRepository repo) {
        this.repo = repo;
    }

    public List<Reserva> listar() {
        return repo.findAll();
    }

    public Reserva obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
    }

    public Reserva crear(Reserva item) {
        if (item.getClienteId() == null || item.getEspacio() == null || item.getFecha() == null
                || item.getHorario() == null)
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST, "Faltan datos de reserva");
        if (repo.findAll().stream().anyMatch(r -> item.getEspacio().equalsIgnoreCase(r.getEspacio())
                && item.getFecha().equals(r.getFecha()) && item.getHorario().equals(r.getHorario())
                && !"CANCELADA".equals(r.getEstado()) && (item.getId() == null || !item.getId().equals(r.getId()))))
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT, "Espacio ocupado en ese horario");
        item.setEstado("ACTIVA");
        return repo.save(item);
    }

    public Reserva actualizar(Long id, Reserva item) {
        Reserva actual = obtener(id);
        if (item.getClienteId() != null)
            actual.setClienteId(item.getClienteId());
        if (item.getEspacio() != null)
            actual.setEspacio(item.getEspacio());
        if (item.getFecha() != null)
            actual.setFecha(item.getFecha());
        if (item.getHorario() != null)
            actual.setHorario(item.getHorario());
        if (item.getEstado() != null)
            actual.setEstado(item.getEstado());
        return repo.save(actual);
    }

    public void eliminar(Long id) {
        obtener(id);
        repo.deleteById(id);
    }

    public java.util.List<Reserva> disponibles(String fecha, String horario) {
        return repo.findAll().stream().filter(
                r -> fecha.equals(r.getFecha()) && horario.equals(r.getHorario()) && !"CANCELADA".equals(r.getEstado()))
                .toList();
    }

    public Reserva cancelar(Long id) {
        Reserva r = obtener(id);
        r.setEstado("CANCELADA");
        return repo.save(r);
    }

}
