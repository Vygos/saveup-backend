package br.com.saveup.saveupbackend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.saveup.saveupbackend.model.domain.Usuario;
import br.com.saveup.saveupbackend.model.dto.ObjectDTO;
import br.com.saveup.saveupbackend.model.dto.UsuarioCadastroDTO;
import br.com.saveup.saveupbackend.model.dto.UsuarioDTO;
import br.com.saveup.saveupbackend.service.UsuarioService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ModelMapper mapper;

    @GetMapping(value = "{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping(value = "findByEmail")
    public ResponseEntity<UsuarioDTO> findByEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @GetMapping(value = "existsByEmail")
    public Boolean existsByEmail(@RequestParam("email") String email) {
        return usuarioService.existsByEmail(email);
    }

    @PostMapping(value = "save")
    public ResponseEntity<UsuarioDTO> salvar(@RequestBody @Validated UsuarioCadastroDTO usuarioCadastroDTO) {
        return ResponseEntity.ok(usuarioService.salvar(mapper.map(usuarioCadastroDTO, Usuario.class)));
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@RequestBody @Validated UsuarioDTO usuarioDTO, @PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.atualizar(id, mapper.map(usuarioDTO, Usuario.class)));
    }

    @PostMapping(value = "{id}/upload")
    public ResponseEntity<ObjectDTO> upload(@PathVariable Integer id, @RequestParam("arquivo") MultipartFile arquivo) {
        return ResponseEntity.ok(usuarioService.upload(arquivo, id));
    }
}
