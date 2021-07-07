package br.com.saveup.saveupbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.com.saveup.saveupbackend.model.domain.Financa;
import br.com.saveup.saveupbackend.model.domain.Ganhos;
import br.com.saveup.saveupbackend.model.domain.TipoGanho;
import br.com.saveup.saveupbackend.model.dto.GanhosCadastroDTO;
import br.com.saveup.saveupbackend.model.dto.GanhosDTO;
import br.com.saveup.saveupbackend.repository.FinancaRepository;
import br.com.saveup.saveupbackend.repository.GanhosRepository;
import br.com.saveup.saveupbackend.repository.TipoGanhoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GanhosService {

    private final ModelMapper mapper;
    private final GanhosRepository ganhosRepository;
    private final TipoGanhoRepository tipoGanhoRepository;
    private final FinancaRepository financaRepository;
    
    public List<GanhosDTO> findAll() {
        List<Ganhos> ganhos = ganhosRepository.findAll();

        return ganhos.stream().map(ganho -> mapper.map(ganho, GanhosDTO.class))
            .collect(Collectors.toList());
    }

    public GanhosDTO findById(Integer id) {
        Ganhos ganhos = ganhosRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        return mapper.map(ganhos, GanhosDTO.class);
    }

    public GanhosDTO salvarGanho(GanhosCadastroDTO ganhoDTO) {
        Ganhos novoGanho = new Ganhos();

        Financa financa = financaRepository.findById(ganhoDTO.getId_financa()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        TipoGanho tipoGanho = tipoGanhoRepository.findById(ganhoDTO.getId_tipo()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        
        novoGanho.setFinanca(financa);
        novoGanho.setTipoGanho(tipoGanho);
        novoGanho.setNome(ganhoDTO.getNome());
        novoGanho.setValor(ganhoDTO.getValor());

        return mapper.map(ganhosRepository.save(novoGanho), GanhosDTO.class);
    }

	public void deleteById(Integer id) {
		ganhosRepository.deleteById(id);
	}

	public GanhosDTO edit(Integer id, GanhosDTO ganhos) {
		Ganhos g = ganhosRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		g.setNome(ganhos.getNome());
		g.setValor(ganhos.getValor());
		
		TipoGanho tipoGanho = tipoGanhoRepository.findById(ganhos.getTipo().getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		g.setTipoGanho(tipoGanho);
		
		return mapper.map(ganhosRepository.save(g), GanhosDTO.class);
	}
}
