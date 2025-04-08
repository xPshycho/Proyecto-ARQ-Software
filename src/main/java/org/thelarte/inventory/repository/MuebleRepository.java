package org.thelarte.inventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.thelarte.inventory.model.Mueble;

public interface MuebleRepository extends MongoRepository<Mueble, String> {
    // Puedes añadir métodos de consulta personalizados si es necesario.
}
