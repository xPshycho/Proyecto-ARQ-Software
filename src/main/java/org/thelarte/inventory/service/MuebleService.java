package org.thelarte.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thelarte.inventory.model.Mueble;
import org.thelarte.inventory.repository.MuebleRepository;

@Service
public class MuebleService {

    private final MuebleRepository repository;

    public MuebleService(MuebleRepository repository) {
        this.repository = repository;
    }

    public List<Mueble> findAll() {
        return repository.findAll();
    }

    public Optional<Mueble> findById(String id) {
        return repository.findById(id);
    }

    public Mueble save(Mueble mueble) {
        return repository.save(mueble);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    /**
     * Ajusta la cantidad en inventario.
     *
     * @param id            Identificador del mueble.
     * @param cantidadAjuste Valor que se sumará (o restará, si es negativo).
     * @return Mueble actualizado o vacío si no se encontró.
     */
    @Transactional
    public Optional<Mueble> ajustarInventario(String id, int cantidadAjuste) {
        Optional<Mueble> optional = repository.findById(id);
        if (optional.isPresent()) {
            Mueble mueble = optional.get();
            int nuevaCantidad = mueble.getCantidad() + cantidadAjuste;
            if (nuevaCantidad < 0) {
                nuevaCantidad = 0;
            }
            mueble.setCantidad(nuevaCantidad);
            repository.save(mueble);
            return Optional.of(mueble);
        }
        return Optional.empty();
    }
}
