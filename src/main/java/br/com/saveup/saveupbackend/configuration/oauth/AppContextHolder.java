package br.com.saveup.saveupbackend.configuration.oauth;

import br.com.saveup.saveupbackend.model.domain.Usuario;
import br.com.saveup.saveupbackend.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@AllArgsConstructor
@Component
public class AppContextHolder {

    private final UsuarioService usuarioService;

    public Usuario getUser(){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioService.repository().findByEmail(email)
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        return usuario;
    }
}
