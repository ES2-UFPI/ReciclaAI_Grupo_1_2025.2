package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import br.ufpi.recicle_ai.domain.model.Usuario;
import br.ufpi.recicle_ai.domain.repository.UsuarioRepository;
import br.ufpi.recicle_ai.dto.LoginRequestDTO;
import br.ufpi.recicle_ai.dto.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ProdutorService produtorService;
    private final ReceptorService receptorService;
    private final ColetorService coletorService;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado!");
        }
        Usuario usuario = usuarioOpt.get();
        if (!usuario.getSenha().equals(loginRequest.getSenha())) {
            throw new RuntimeException("Senha inválida!");
        }
        String nomeUsuario = "";
        if(usuario.getTipoPessoa() == TipoPessoaEnum.COLETOR){
            nomeUsuario = coletorService.findById(usuario.getPessoaId()).getNome();
        } else if(usuario.getTipoPessoa() == TipoPessoaEnum.PRODUTOR){
            nomeUsuario = produtorService.findById(usuario.getPessoaId()).getNome();
        } else if(usuario.getTipoPessoa() == TipoPessoaEnum.RECEPTOR){
            nomeUsuario = receptorService.findById(usuario.getPessoaId()).getNome();
        }
        return new LoginResponseDTO(usuario.getPessoaId(), usuario.getTipoPessoa(), nomeUsuario);
    }
}