package br.com.saveup.saveupbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.saveup.saveupbackend.model.dto.GanhosCadastroDTO;
import br.com.saveup.saveupbackend.model.dto.GanhosDTO;
import br.com.saveup.saveupbackend.service.GanhosService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("ganhos")
public class GanhosController {
    
    private final GanhosService ganhosService;

    @GetMapping
    public ResponseEntity<List<GanhosDTO>> findAll() {
        return ResponseEntity.ok(ganhosService.findAll());
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<GanhosDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ganhosService.findById(id));
    }

    @PostMapping
    public ResponseEntity<GanhosDTO> salvarGanho(@RequestBody @Validated GanhosCadastroDTO ganho) {
        return ResponseEntity.ok(ganhosService.salvarGanho(ganho));
    }
    
    @PatchMapping(value = "{id}")
    public ResponseEntity<GanhosDTO> edit(@PathVariable Integer id, @RequestBody @Validated GanhosDTO ganhos) {
    	return ResponseEntity.ok(ganhosService.edit(id, ganhos));
    }
    
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
    	ganhosService.deleteById(id);
    	return ResponseEntity.noContent().build();
    }
}
