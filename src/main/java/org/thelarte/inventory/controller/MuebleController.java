package org.thelarte.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thelarte.inventory.model.Mueble;
import org.thelarte.inventory.service.MuebleService;

@RestController
@RequestMapping("/api/muebles")
public class MuebleController {

    private final MuebleService service;

    public MuebleController(MuebleService service) {
        this.service = service;
    }

    // Obtener todos los muebles
    @GetMapping
    public ResponseEntity<List<Mueble>> getAllMuebles() {
        List<Mueble> muebles = service.findAll();
        return ResponseEntity.ok(muebles);
    }

    // Obtener un mueble por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Mueble> getMuebleById(@PathVariable String id) {
        Optional<Mueble> mueble = service.findById(id);
        return mueble.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo mueble
    @PostMapping
    public ResponseEntity<Mueble> createMueble(@RequestBody Mueble mueble) {
        Mueble nuevoMueble = service.save(mueble);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMueble);
    }

    // Actualizar un mueble existente
    @PutMapping("/{id}")
    public ResponseEntity<Mueble> updateMueble(@PathVariable String id, @RequestBody Mueble details) {
        Optional<Mueble> existing = service.findById(id);
        if (existing.isPresent()) {
            Mueble mueble = existing.get();
            mueble.setNombre(details.getNombre());
            mueble.setDescripcion(details.getDescripcion());
            mueble.setPrecio(details.getPrecio());
            mueble.setCantidad(details.getCantidad());
            Mueble actualizado = service.save(mueble);
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un mueble por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMueble(@PathVariable String id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Ajustar el inventario (sumar o restar cantidad)
    @PutMapping("/{id}/inventario")
    public ResponseEntity<Mueble> ajustarInventario(@PathVariable String id, @RequestParam int ajuste) {
        Optional<Mueble> actualizado = service.ajustarInventario(id, ajuste);
        return actualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cargar un mueble para editarlo
    @GetMapping("/editar/{id}")
    public ResponseEntity<Mueble> obtenerMueble(@PathVariable String id) {
        Optional<Mueble> mueble = service.findById(id);
        return mueble.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar un mueble (para edición)
    @PutMapping("/editar/{id}")
    public ResponseEntity<String> actualizarMueble(@PathVariable String id, @RequestBody Mueble muebleActualizado) {
        Optional<Mueble> mueble = service.findById(id);
        if (mueble.isPresent()) {
            Mueble muebleExistente = mueble.get();

            // Actualizamos los campos del mueble
            muebleExistente.setNombre(muebleActualizado.getNombre());
            muebleExistente.setDescripcion(muebleActualizado.getDescripcion());
            muebleExistente.setPrecio(muebleActualizado.getPrecio());
            muebleExistente.setCantidad(muebleActualizado.getCantidad());
            muebleExistente.setFotos(muebleActualizado.getFotos());

            service.save(muebleExistente);  // Guardamos el mueble actualizado
            return new ResponseEntity<>("Mueble actualizado correctamente", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Mueble no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
