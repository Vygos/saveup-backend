package br.com.saveup.saveupbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.saveup.saveupbackend.model.dto.ChartDTO;
import br.com.saveup.saveupbackend.model.dto.FinancaDTO;
import br.com.saveup.saveupbackend.service.FinancaService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "financa")
@AllArgsConstructor
public class FinancaController {

  private final FinancaService service;

  @GetMapping(value = "{id}")
  public ResponseEntity<List<FinancaDTO>> findAllByUserId(@PathVariable Integer id, @RequestParam("ano") String ano) {
    return ResponseEntity.ok(service.findByUserIdAndYear(id, ano));
  }

  @GetMapping(value = "listarAnos/{id}")
  public ResponseEntity<List<String>> listYears(@PathVariable Integer id) {
    return ResponseEntity.ok(service.listYears(id));
  }
  
  @GetMapping(value = "dataCharts/{id}")
  public ResponseEntity<List<ChartDTO>> getDataCharts(@PathVariable Integer id, @RequestParam("ano") String ano) {
	 return ResponseEntity.ok(service.getDataCharts(id, ano));
  }
  
  @PostMapping
  public ResponseEntity<FinancaDTO> create(@Validated @RequestBody FinancaDTO financa) {
    return ResponseEntity.ok(service.create(financa));
  }

  @PatchMapping
  public ResponseEntity<FinancaDTO> atualizar(@Validated @RequestBody FinancaDTO financa) {
    return ResponseEntity.ok(service.atualizar(financa));
  }
  
}
