package br.com.saveup.saveupbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.com.saveup.saveupbackend.model.domain.Despesa;
import br.com.saveup.saveupbackend.model.domain.Financa;
import br.com.saveup.saveupbackend.model.domain.TipoDespesa;
import br.com.saveup.saveupbackend.model.dto.DespesaCadastroDTO;
import br.com.saveup.saveupbackend.model.dto.DespesaDTO;
import br.com.saveup.saveupbackend.repository.DespesaRepository;
import br.com.saveup.saveupbackend.repository.FinancaRepository;
import br.com.saveup.saveupbackend.repository.TipoDespesaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DespesaService {

    private final ModelMapper mapper;
    private final DespesaRepository despesaRepository;
    private final TipoDespesaRepository tipoDespesaRepository;
    private final FinancaRepository financaRepository;
    
	public List<DespesaDTO> findAll() {
		List<Despesa> despesas = despesaRepository.findAll();

		return despesas.stream().map(despesa -> mapper.map(despesa, DespesaDTO.class)).collect(Collectors.toList());
	}

    public DespesaDTO findById(Integer id) {
        Despesa despesa = despesaRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        return mapper.map(despesa, DespesaDTO.class);
    }

    public DespesaDTO salvarDespesa(DespesaCadastroDTO despesaDTO) {
        Despesa novaDespesa = new Despesa();

        Financa financa = financaRepository.findById(despesaDTO.getId_financa()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        TipoDespesa tipoDespesa = tipoDespesaRepository.findById(despesaDTO.getId_tipo()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        
        novaDespesa.setFinanca(financa);
        novaDespesa.setTipoDespesa(tipoDespesa);
        novaDespesa.setNome(despesaDTO.getNome());
        novaDespesa.setValor(despesaDTO.getValor());

        return mapper.map(despesaRepository.save(novaDespesa), DespesaDTO.class);
    }

    public List<TipoDespesa> findAllTipoDespesa() {
        return tipoDespesaRepository.findAll();
    }

    public TipoDespesa findTipoDespesaById(Integer id) {
        return tipoDespesaRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public TipoDespesa salvarTipoDespesa(TipoDespesa tipoDespesa) {
        return tipoDespesaRepository.save(tipoDespesa);
    }

	public void deleteById(Integer id) {
		despesaRepository.deleteById(id);
	}

	public DespesaDTO edit(Integer id, DespesaDTO despesa) {
		Despesa d = despesaRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		d.setNome(despesa.getNome());
		d.setValor(despesa.getValor());
		
		TipoDespesa tipoDespesa = tipoDespesaRepository.findById(despesa.getTipo().getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		d.setTipoDespesa(tipoDespesa);
		
		return mapper.map(despesaRepository.save(d), DespesaDTO.class);
	}
}
