package br.com.saveup.saveupbackend.service;

import br.com.saveup.saveupbackend.SaveupProperties;
import br.com.saveup.saveupbackend.handlers.NegocioException;
import br.com.saveup.saveupbackend.model.domain.Usuario;
import br.com.saveup.saveupbackend.model.dto.ObjectDTO;
import br.com.saveup.saveupbackend.model.dto.UsuarioDTO;
import br.com.saveup.saveupbackend.repository.UsuarioRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper mapper;
    private final SaveupProperties saveupProperties;
    private final AmazonS3 amazonS3;


    public UsuarioRepository repository() {
        return usuarioRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UsuarioDTO salvar(Usuario usuario) {
        final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

        String pswdEncoded = bCrypt.encode(usuario.getSecret());
        usuario.setSecret(pswdEncoded);
        usuario.setDtCadastro(LocalDateTime.now());
        Usuario usuarioManaged = repository().save(usuario);

        return mapper.map(usuarioManaged, UsuarioDTO.class);
    }

    public UsuarioDTO findById(Integer id) {
        Usuario usuario = repository().findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
        usuarioDTO.setFoto(recuperarFoto(usuario.getFoto()));

        return usuarioDTO;
    }

    private String recuperarFoto(String foto) {
        if (foto == null) {
            return null;
        }

        S3Object object = amazonS3.getObject(saveupProperties.getBucketName(), foto);
        S3ObjectInputStream objectContent = object.getObjectContent();

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            objectContent.transferTo(bytes);

            return Base64.getEncoder().encodeToString(bytes.toByteArray());
        } catch (IOException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    public UsuarioDTO findByEmail(String email) {
        Usuario usuario = repository().findByEmail(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
        usuarioDTO.setFotoBase64(recuperarFoto(usuario.getFoto()));

        return usuarioDTO;
    }

    public Boolean existsByEmail(String email) {
        return repository().existsByEmail(email);
    }

    public UsuarioDTO atualizar(Integer id, Usuario usuario) {
        Usuario usuarioDB = repository().findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        usuario.setEmail(usuarioDB.getEmail());
        usuario.setSecret(usuarioDB.getSecret());
        usuario.setFoto(usuarioDB.getFoto());
        usuario.setDtCadastro(usuarioDB.getDtCadastro());

        return mapper.map(repository().save(usuario), UsuarioDTO.class);
    }

    @Transactional
    public ObjectDTO upload(MultipartFile file, Integer id) {
        Usuario usuario = repository().findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            String hashName = DigestUtils.md5Hex(file.getOriginalFilename());
            usuario.setFoto(hashName);
            this.amazonS3.putObject(
                    saveupProperties.getBucketName(),
                    hashName,
                    file.getInputStream(),
                    objectMetadata);

            return new ObjectDTO(hashName);
        } catch (IOException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
