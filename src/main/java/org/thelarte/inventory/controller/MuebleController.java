package org.thelarte.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<Mueble>> getAllMuebles() {
        List<Mueble> muebles = service.findAll();
        return ResponseEntity.ok(muebles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mueble> getMuebleById(@PathVariable String id) {
        Optional<Mueble> mueble = service.findById(id);
        return mueble.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mueble> createMueble(@RequestBody Mueble mueble) {
        Mueble nuevoMueble = service.save(mueble);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMueble);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMueble(@PathVariable String id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/inventario")
    public ResponseEntity<Mueble> ajustarInventario(@PathVariable String id, @RequestParam int ajuste) {
        Optional<Mueble> actualizado = service.ajustarInventario(id, ajuste);
        return actualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
