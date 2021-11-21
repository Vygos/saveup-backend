package br.com.saveup.saveupbackend.service;


import br.com.saveup.saveupbackend.handlers.NegocioException;
import br.com.saveup.saveupbackend.model.domain.TipoGanho;
import br.com.saveup.saveupbackend.model.dto.TipoGanhoThinDTO;
import br.com.saveup.saveupbackend.repository.TipoGanhoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;



@AllArgsConstructor
@Service
public class TipoGanhoService {

    private final TipoGanhoRepository tipoGanhoRepository;
    private final ModelMapper modelMapper;

    public Page<TipoGanho> findAllByFilter(Pageable pageable, TipoGanho tipoGanho) {
        Example<TipoGanho> tipoGanhoExample = Example.of(tipoGanho,
            ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        return tipoGanhoRepository.findAll(tipoGanhoExample, pageable);
    }

    public List<TipoGanhoThinDTO> findAll(String email) {
        List<TipoGanho> allDefault = tipoGanhoRepository.findAllByEmailIsNull();
        List<TipoGanho> allByEmail = tipoGanhoRepository.findAllByEmail(email);

        allDefault.addAll(allByEmail);

        return allDefault.stream()
                .map(tipoGanho -> modelMapper.map(tipoGanho, TipoGanhoThinDTO.class))
                .collect(Collectors.toList());
    }

    public boolean isAlreadyExistsByNome(TipoGanho tipoGanho) {
        List<String> tipoGanhos = tipoGanhoRepository.existsByNomeAndEmail(tipoGanho.getNome(), tipoGanho.getEmail());
        return !tipoGanhos.isEmpty();
    }

    public List<TipoGanho> findAllTipoGanho() {
        return tipoGanhoRepository.findAll();
    }

    public TipoGanho findTipoGanhoById(Integer id) {
        return tipoGanhoRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public TipoGanho salvar(TipoGanho tipoGanho) {
        if (isAlreadyExistsByNome(tipoGanho)) {
            throw new NegocioException("Nome já cadastrado");
        }
        return tipoGanhoRepository.save(tipoGanho);
    }

    public TipoGanho updateById(Integer id, TipoGanho tipoGanhoAtualizado) {
        TipoGanho tipoGanho = tipoGanhoRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        tipoGanho.setNome(tipoGanhoAtualizado.getNome());
        return tipoGanhoRepository.save(tipoGanho);
    }

    public void deleteById(Integer id) {
        tipoGanhoRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        this.tipoGanhoRepository.deleteById(id);
    }
}
