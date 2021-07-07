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

import br.com.saveup.saveupbackend.model.dto.DespesaCadastroDTO;
import br.com.saveup.saveupbackend.model.dto.DespesaDTO;
import br.com.saveup.saveupbackend.service.DespesaService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("despesa")
public class DespesaController {
	 
    private final DespesaService despesaService;

    @GetMapping
    public ResponseEntity<List<DespesaDTO>> findAll() {
        return ResponseEntity.ok(despesaService.findAll());
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<DespesaDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(despesaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DespesaDTO> salvarDespesa(@RequestBody @Validated DespesaCadastroDTO despesa) {
        return ResponseEntity.ok(despesaService.salvarDespesa(despesa));
    }
    
    @PatchMapping(value = "{id}")
    public ResponseEntity<DespesaDTO> edit(@PathVariable Integer id, @RequestBody @Validated DespesaDTO despesa) {
    	return ResponseEntity.ok(despesaService.edit(id, despesa));
    }
    
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
    	despesaService.deleteById(id);
    	return ResponseEntity.noContent().build();
    }

}
