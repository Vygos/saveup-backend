package br.com.saveup.saveupbackend.controller;


import br.com.saveup.saveupbackend.configuration.oauth.AppContextHolder;
import br.com.saveup.saveupbackend.model.domain.TipoGanho;
import br.com.saveup.saveupbackend.model.dto.TipoGanhoDTO;
import br.com.saveup.saveupbackend.model.dto.TipoGanhoThinDTO;
import br.com.saveup.saveupbackend.service.TipoGanhoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("tipo-ganho")
public class TipoGanhoController {

    private final TipoGanhoService tipoGanhoService;
    private final ModelMapper mapper;
    private final AppContextHolder appContextHolder;

    @GetMapping(value = "findAllByFilter")
    public ResponseEntity<Page<TipoGanho>> findAllByFilter(Pageable pageable, TipoGanho tipoGanho) {
        String email = appContextHolder.getUser().getEmail();
        tipoGanho.setEmail(email);
        Page<TipoGanho> allTipoGanhos = tipoGanhoService.findAllByFilter(pageable, tipoGanho);
        return ResponseEntity.ok(allTipoGanhos);
    }

    @GetMapping(value = "findAll")
    public ResponseEntity<List<TipoGanhoThinDTO>> findAll() {
        String email = appContextHolder.getUser().getEmail();
        return ResponseEntity.ok(tipoGanhoService.findAll(email));
    }

    @GetMapping(value = "isAlreadyExistsByNome")
    public boolean isAlreadyExistsByNome(TipoGanhoDTO tipoGanho) {
        String email = appContextHolder.getUser().getEmail();
        tipoGanho.setEmail(email);

        return tipoGanhoService.isAlreadyExistsByNome(mapper.map(tipoGanho, TipoGanho.class));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<TipoGanho> findTipoGanhoById(@PathVariable Integer id) {
        return ResponseEntity.ok(tipoGanhoService.findTipoGanhoById(id));
    }

    @PostMapping
    public ResponseEntity<TipoGanho> salvarTipoGanho(@RequestBody @Validated TipoGanhoDTO tipoGanho) {
        String email = appContextHolder.getUser().getEmail();
        tipoGanho.setEmail(email);
        return ResponseEntity.ok(tipoGanhoService.salvar(mapper.map(tipoGanho, TipoGanho.class)));
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity<TipoGanho> updateById(@PathVariable Integer id, @RequestBody TipoGanho tipoGanho) {
        return ResponseEntity.ok(tipoGanhoService.updateById(id, tipoGanho));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteById(@PathVariable() Integer id) {
        this.tipoGanhoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
