package com.delivery_api.Projeto.Delivery.API;

import com.delivery_api.Projeto.Delivery.API.dto.*;
import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.entity.Pedido;
import com.delivery_api.Projeto.Delivery.API.entity.Produto;
import com.delivery_api.Projeto.Delivery.API.entity.Usuario;
import com.delivery_api.Projeto.Delivery.API.enums.Role;
import com.delivery_api.Projeto.Delivery.API.exceptions.BusinessException;
import com.delivery_api.Projeto.Delivery.API.exceptions.ConflictException;
import com.delivery_api.Projeto.Delivery.API.exceptions.EntityNotFoundException;
import com.delivery_api.Projeto.Delivery.API.repository.*;
import com.delivery_api.Projeto.Delivery.API.security.JwtUtil;
import com.delivery_api.Projeto.Delivery.API.service.AuthService;
import com.delivery_api.Projeto.Delivery.API.service.ProdutoService;
import com.delivery_api.Projeto.Delivery.API.service.RelatorioService;
import com.delivery_api.Projeto.Delivery.API.validation.CategoriaValidator;
import com.delivery_api.Projeto.Delivery.API.validation.CepValidator;
import com.delivery_api.Projeto.Delivery.API.validation.TelefoneValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class GeneralCoverageTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private RelatorioService relatorioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private RestauranteRepository restauranteRepository;

    @MockBean
    private PedidoRepository pedidoRepository;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSecurityAndAuth() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@email.com");
        usuario.setRole(Role.ADMIN);

        String token = jwtUtil.generateToken(usuario);
        assertNotNull(token);
        assertEquals("teste@email.com", jwtUtil.extractUsername(token));
        assertTrue(jwtUtil.isTokenValid(token, usuario));

        RegisterRequest registerRequest = new RegisterRequest("Nome", "email@teste.com", "123456", Role.CLIENTE, null);
        when(usuarioRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("senhaHash");
        when(usuarioRepository.save(any())).thenReturn(usuario);

        LoginResponse res = authService.register(registerRequest);
        assertNotNull(res);

        LoginRequest loginRequest = new LoginRequest("teste@email.com", "123456");
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(usuario));

        LoginResponse loginRes = authService.login(loginRequest);
        assertNotNull(loginRes);
    }

    @Test
    public void testProdutoService() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO();
        dto.setNome("Prod");
        dto.setDescricao("Desc");
        dto.setPreco(BigDecimal.TEN);
        dto.setCategoria("Cat");
        dto.setDisponivel(true);
        dto.setRestauranteId(1L);

        Produto produto = new Produto(1L, "Prod", "Desc", BigDecimal.TEN, "Cat", true, 1L);

        when(restauranteRepository.existsById(1L)).thenReturn(true);
        when(produtoRepository.save(any())).thenReturn(produto);
        when(produtoRepository.findAll()).thenReturn(Collections.singletonList(produto));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.findByRestauranteId(1L)).thenReturn(Collections.singletonList(produto));

        assertNotNull(produtoService.cadastrar(dto));
        assertFalse(produtoService.listarTodos().isEmpty());
        assertNotNull(produtoService.buscarPorId(1L));
        assertNotNull(produtoService.buscarPorRestaurante(1L));

        produtoService.inativar(1L);

        produtoService.atualizar(1L, dto);

        when(produtoRepository.existsById(1L)).thenReturn(true);
        produtoService.excluir(1L);
    }

    @Test
    public void testRelatorioService() {
        Pedido pedido = new Pedido();
        pedido.setValorTotal(BigDecimal.valueOf(100));
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setClienteId(1L);

        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        when(pedidoRepository.findByDataPedidoBetween(any(), any())).thenReturn(Collections.singletonList(pedido));
        when(pedidoRepository.findAll()).thenReturn(Collections.singletonList(pedido));

        assertFalse(relatorioService.vendasPorPeriodo(LocalDateTime.now(), LocalDateTime.now()).isEmpty());
        assertEquals(BigDecimal.valueOf(100), relatorioService.calcularFaturamentoTotal());
    }

    @Test
    public void testExceptionsAndValidations() {
        BusinessException be = new BusinessException("Erro");
        assertNotNull(be.getMessage());

        ConflictException ce = new ConflictException("Conflito");
        assertNotNull(ce.getMessage());

        EntityNotFoundException enf = new EntityNotFoundException("Não achou");
        assertNotNull(enf.getMessage());

        TelefoneValidator telVal = new TelefoneValidator();
        assertTrue(telVal.isValid("11999998888", null));
        assertFalse(telVal.isValid("123", null));

        CategoriaValidator catVal = new CategoriaValidator();
        assertTrue(catVal.isValid("Italiana", null));
        assertFalse(catVal.isValid("Invalida", null));

        CepValidator cepVal = new CepValidator();
        assertTrue(cepVal.isValid("12345678", null));
        assertFalse(cepVal.isValid("123", null));
    }

    @Test
    public void testErrorResponse() {
        ErrorResponseDTO erro = ErrorResponseDTO.builder()
                .status(400)
                .error("Bad Request")
                .message("Teste")
                .path("/api")
                .timestamp(LocalDateTime.now())
                .build();

        assertNotNull(erro);
        assertEquals(400, erro.getStatus());
    }
}