package br.com.saveup.saveupbackend.controller;

import br.com.saveup.saveupbackend.model.dto.TipoDespesaThinDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import br.com.saveup.saveupbackend.configuration.oauth.AppContextHolder;
import br.com.saveup.saveupbackend.model.domain.TipoDespesa;
import br.com.saveup.saveupbackend.model.dto.TipoDespesaDTO;
import br.com.saveup.saveupbackend.service.TipoDespesaService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("tipo-despesa")
public class TipoDespesaController {

    private final TipoDespesaService tipoDespesaService;
    private final ModelMapper mapper;
    private final AppContextHolder appContextHolder;

    @GetMapping(value = "findAllByFilter")
    public ResponseEntity<Page<TipoDespesa>> findAllByFilter(Pageable pageable, TipoDespesa tipoDespesa) {
        String email = appContextHolder.getUser().getEmail();
        tipoDespesa.setEmail(email);
        Page<TipoDespesa> allTipoDespesas = tipoDespesaService.findAll(pageable, tipoDespesa);
        return ResponseEntity.ok(allTipoDespesas);
    }

    @GetMapping(value = "findAll")
    public ResponseEntity<List<TipoDespesaThinDTO>> findAll() {
        String email = appContextHolder.getUser().getEmail();
        return ResponseEntity.ok(tipoDespesaService.findAll(email));
    }

    @GetMapping(value = "isAlreadyExistsByNome")
    public boolean isAlreadyExistsByNome(TipoDespesaDTO tipoDespesa) {
        String email = appContextHolder.getUser().getEmail();
        tipoDespesa.setEmail(email);

        return tipoDespesaService.isAlreadyExistsByNome(mapper.map(tipoDespesa, TipoDespesa.class));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<TipoDespesa> findTipoDespesaById(@PathVariable Integer id) {
        return ResponseEntity.ok(tipoDespesaService.findTipoDespesaById(id));
    }

    @PostMapping
    public ResponseEntity<TipoDespesa> salvarTipoDespesa(@RequestBody @Validated TipoDespesaDTO tipoDespesa) {
        String email = appContextHolder.getUser().getEmail();
        tipoDespesa.setEmail(email);
        return ResponseEntity.ok(tipoDespesaService.salvar(mapper.map(tipoDespesa, TipoDespesa.class)));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        tipoDespesaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity<TipoDespesa> atualizarById(@PathVariable Integer id, @RequestBody TipoDespesa tipoDespesa) {
        return ResponseEntity.ok(tipoDespesaService.updateById(id, tipoDespesa));
    }

}
