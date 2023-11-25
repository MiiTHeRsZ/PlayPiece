package com.playpieceAPI.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.playpieceAPI.models.CargoModel;
import com.playpieceAPI.models.LoginDto;
import com.playpieceAPI.models.UsuarioModel;
import com.playpieceAPI.repositories.CargoRepository;
import com.playpieceAPI.repositories.UsuarioRepository;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CargoRepository cargoRepository;
    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar os usuários corretamente")
    void testGetUsuarioListCase1() {

        List<UsuarioModel> lista = new ArrayList<UsuarioModel>();

        when(usuarioRepository.findAll()).thenReturn(lista);

        // guardo o retorno da usuarioService em uma variável
        var result = usuarioService.getUsuarioList();

        // veririco se o valor recebido pela execução do service é o mesmo que mockei
        assertEquals(lista, result);
    }

    @Test
    @DisplayName("Deve estourar exceção")
    void testGetUsuarioListCase2() {
        when(usuarioRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(Exception.class, ()->usuarioService.getUsuarioList());
    }

    @Test
    @DisplayName("Deve retornar o usuário corretamente")
    void testGetUsuarioByIdCase1() {
        UsuarioModel usuario = new UsuarioModel();

        // findById me retorna um Optional<UsuarioModel>, por isso o Optional.of
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        var result = usuarioService.getUsuarioById(1L);

        assertEquals(usuario, result);
    }

    @Test
    @DisplayName("Deve estourar exceção")
    void testGetUsuarioByIdCase2() {
        when(usuarioRepository.findById(anyLong())).thenThrow(new RuntimeException());

        assertThrows(Exception.class, ()->usuarioService.getUsuarioById(1L));
    }

    @Test
    @DisplayName("Deve retornar o usuário corretamente")
    void getUsuarioByEmailCase1() {

        UsuarioModel usuario = new UsuarioModel(1L, "TESTE", "12312312312", new CargoModel(1, "TESTE"),
                "teste@teste.com", "123", true);

        when(usuarioRepository.findByEmailUsuario(anyString())).thenReturn(usuario);

        UsuarioModel result = usuarioService.getUsuarioByEmail("teste@teste.com");

        assertEquals(usuario, result);
    }

    @Test
    @DisplayName("Deve estourar uma exceção")
    void getUsuarioByEmailCase2() {
        //Mando ele estourar uma exceção
        when(usuarioRepository.findByEmailUsuario(anyString())).thenThrow(new RuntimeException());

        //verifico se a execução teve uma exceção de qualquer tipo por conta do Exception.class
        assertThrows(Exception.class,()-> usuarioService.getUsuarioByEmail("teste@teste.com"));
    }

    @Test
    @DisplayName("Deve retornar o usuário corretamente")
    void testGetUsuarioByNomeCase1() {

        List<UsuarioModel> lista = new ArrayList<>();

        when(usuarioRepository.findByNomeContaining(anyString())).thenReturn(lista);

        var result = usuarioService.getUsuarioByNome("TESTE");

        assertEquals(lista, result);
    }

    @Test
    @DisplayName("Deve estourar uma exceção")
    void testGetUsuarioByNomeCase2() {
        when(usuarioRepository.findByNomeContaining(anyString())).thenThrow(new RuntimeException());
        assertThrows(Exception.class,()-> usuarioService.getUsuarioByNome("TESTE"));
    }

    @Test
    void testPostUsuarioCase1() {

        when(encoder.encode(anyString())).thenReturn("TesteSenhaEnc");

        UsuarioModel usuario = new UsuarioModel();
        usuario.setSenha("TESTE");
        usuario.setCargo(new CargoModel(1, "TESTE"));

        when(cargoRepository.findById(anyInt())).thenReturn(Optional.of(new CargoModel()));

        UsuarioModel novoUsuario = new UsuarioModel();

        when(usuarioRepository.save(any())).thenReturn(novoUsuario);

        var result = usuarioService.postUsuario(usuario);

        assertEquals(novoUsuario, result);
    }

    @Test
    void testPostUsuarioCase2() {
        when(encoder.encode(anyString())).thenReturn("TesteSenhaEnc");

        UsuarioModel usuario = new UsuarioModel();
        usuario.setSenha("TESTE");
        usuario.setCargo(new CargoModel(1,"TESTE"));

        when(cargoRepository.findById(anyInt())).thenReturn(Optional.of(new CargoModel()));
        
        when(usuarioRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(Exception.class, ()->usuarioService.postUsuario(usuario));
    }

    @Test
    void testStatusUsuarioCase1() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setAtivo(true);

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        var result = usuarioService.statusUsuario(1L);

        assertEquals(usuario, result);
    }

    @Test
    void testStatusUsuarioCase2() {
        when(usuarioRepository.findById(anyLong())).thenThrow(new RuntimeException());
        assertThrows(Exception.class, ()->usuarioService.statusUsuario(1L));
    }

    @Test
    @DisplayName("atualiza usuario e res é true")
    void testUpdateUsuarioCase1() {

        UsuarioModel usuario = new UsuarioModel(1L, "TESTE", "12312312312", null, "teste@teste.com", "123123123", true);
        usuario.setCargo(new CargoModel(1, "TESTE"));

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(encoder.matches(anyString(), anyString())).thenReturn(true);
        when(cargoRepository.findById(anyInt())).thenReturn(Optional.of(usuario.getCargo()));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        var result = usuarioService.updateUsuario(1L, usuario);
        assertEquals(usuario, result);
    }

    @Test
    @DisplayName("atualiza usuario e res é false")
    void testUpdateUsuarioCase2() {
        UsuarioModel usuario = new UsuarioModel(1L, "TESTE", "12312312312", null, "teste@teste.com", "123123123", true);
        usuario.setCargo(new CargoModel(1, "TESTE"));

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(encoder.matches(anyString(), anyString())).thenReturn(false);
        when(cargoRepository.findById(anyInt())).thenReturn(Optional.of(usuario.getCargo()));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        var result = usuarioService.updateUsuario(1L, usuario);
        assertEquals(usuario, result);
    }

    @Test
    @DisplayName("estoura exceção")
    void testUpdateUsuarioCase3() {
        when(usuarioRepository.findById(anyLong())).thenThrow(new RuntimeException());

        assertThrows(Exception.class, ()->usuarioService.updateUsuario(1L, any()));
    }

    @Test
    @DisplayName("usuario não é nulo e senha é correta")
    void testUsuarioLoginCase1() {
        UsuarioModel usuario = new UsuarioModel(1L, "TESTE", "12312312312", null, "teste@teste.com", "123123123", true);

        when(usuarioRepository.findByEmailUsuario(anyString())).thenReturn(usuario);
        when(encoder.matches(any(CharSequence.class), anyString())).thenReturn(true);

        var result = usuarioService.usuarioLogin(new LoginDto("teste@teste.com", "123123123"));

        assertEquals(usuario, result);
    }

    @Test
    @DisplayName("usuario não é nulo e senha é incorreta")
    void testUsuarioLoginCase2() {
        UsuarioModel usuario = new UsuarioModel(1L, "TESTE", "12312312312", null, "teste@teste.com", "123123123", true);

        when(usuarioRepository.findByEmailUsuario(anyString())).thenReturn(usuario);
        when(encoder.matches(anyString(), anyString())).thenReturn(false);

        var result = usuarioService.usuarioLogin(new LoginDto("teste@teste.com", "123123123"));

        assertEquals(null, result);
    }

    @Test
    @DisplayName("usuario é nulo")
    void testUsuarioLoginCase3() {
        when(usuarioRepository.findByEmailUsuario(anyString())).thenReturn(null);

        var result = usuarioService.usuarioLogin(new LoginDto("teste@teste.com", "123123123"));

        assertEquals(null, result);
    }

    @Test
    @DisplayName("estoura exceção")
    void testUsuarioLoginCase4() {
        
        when(usuarioRepository.findByEmailUsuario(anyString())).thenThrow(new RuntimeException());

        assertThrows(Exception.class, ()->usuarioService.usuarioLogin(new LoginDto("teste@teste.com", "123123123")));
    }
}
