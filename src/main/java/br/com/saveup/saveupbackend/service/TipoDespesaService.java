package br.com.saveup.saveupbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import br.com.saveup.saveupbackend.model.dto.TipoDespesaThinDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.com.saveup.saveupbackend.handlers.NegocioException;
import br.com.saveup.saveupbackend.model.domain.TipoDespesa;
import br.com.saveup.saveupbackend.repository.TipoDespesaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TipoDespesaService {

    private final TipoDespesaRepository tipoDespesaRepository;
    private final ModelMapper modelMapper;

    public Page<TipoDespesa> findAll(Pageable pageable, TipoDespesa tipoDespesa) {
        Example<TipoDespesa> tipoDespesaExample = Example.of(tipoDespesa,
            ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return tipoDespesaRepository.findAll(tipoDespesaExample, pageable);
    }

    public List<TipoDespesaThinDTO> findAll(String email) {
        List<TipoDespesa> allDefault = tipoDespesaRepository.findAllByEmailIsNull();
        List<TipoDespesa> allByEmail = tipoDespesaRepository.findAllByEmail(email);

        allDefault.addAll(allByEmail);

        return allDefault.stream()
                .map(tipoGanho -> modelMapper.map(tipoGanho, TipoDespesaThinDTO.class))
                .collect(Collectors.toList());
    }

    public boolean isAlreadyExistsByNome(TipoDespesa tipoDespesa) {
        List<String> tipoDespesas = tipoDespesaRepository.existsByNomeAndEmail(tipoDespesa.getNome(),
                tipoDespesa.getEmail());
        return !tipoDespesas.isEmpty();
    }

    public List<TipoDespesa> findAllTipoDespesa() {
        return tipoDespesaRepository.findAll();
    }

    public TipoDespesa findTipoDespesaById(Integer id) {
        return tipoDespesaRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public TipoDespesa salvar(TipoDespesa tipoDespesa) {
        if (isAlreadyExistsByNome(tipoDespesa)) {
            throw new NegocioException("Nome já cadastrado");
        }
        return tipoDespesaRepository.save(tipoDespesa);
    }

    public void deleteById(Integer id) {
        tipoDespesaRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        tipoDespesaRepository.deleteById(id);
    }

    public TipoDespesa updateById(Integer id, TipoDespesa tipoDespesa) {
        TipoDespesa anterior = tipoDespesaRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        anterior.setNome(tipoDespesa.getNome());
        return tipoDespesaRepository.save(anterior);
    }
}
