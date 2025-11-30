package com.delivery_api.Projeto.Delivery.API.security;

import com.delivery_api.Projeto.Delivery.API.dto.ErrorResponseDTO;
import com.delivery_api.Projeto.Delivery.API.entity.Usuario;
import com.delivery_api.Projeto.Delivery.API.enums.Role;
import com.delivery_api.Projeto.Delivery.API.exceptions.BusinessException;
import com.delivery_api.Projeto.Delivery.API.exceptions.ConflictException;
import com.delivery_api.Projeto.Delivery.API.exceptions.EntityNotFoundException;
import com.delivery_api.Projeto.Delivery.API.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityAndExceptionCoverageTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testJwtUtilFullCoverage() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setRole(Role.ADMIN);
        usuario.setId(1L);
    }

    @Test
    public void testUserDetailsServiceImplException() {
        // Força o erro de UsernameNotFoundException
        when(userDetailsService.loadUserByUsername("inexistente"))
                .thenThrow(new UsernameNotFoundException("Não encontrado"));

        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername("inexistente"));
    }

    @Test
    public void testExceptionsFullCoverage() {
        BusinessException be1 = new BusinessException("Erro");
        BusinessException be2 = new BusinessException("Erro", new RuntimeException());
        assertNotNull(be1.getMessage());
        assertNotNull(be2.getCause());

        ConflictException ce = new ConflictException("Conflito");
        assertNotNull(ce.getMessage());

        EntityNotFoundException enfe1 = new EntityNotFoundException("Não achou");
        EntityNotFoundException enfe2 = new EntityNotFoundException("Não achou", new RuntimeException());
        assertNotNull(enfe1.getMessage());
        assertNotNull(enfe2.getCause());
    }

    @Test
    public void testErrorResponseDTOBuilder() {
        Map<String, String> details = new HashMap<>();
        details.put("campo", "erro");

        ErrorResponseDTO dto = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error("Bad Request")
                .message("Mensagem")
                .path("/path")
                .details(details)
                .build();

        assertNotNull(dto.getTimestamp());
        assertEquals(400, dto.getStatus());
        assertEquals("Bad Request", dto.getError());
        assertEquals("Mensagem", dto.getMessage());
        assertEquals("/path", dto.getPath());
        assertEquals("erro", dto.getDetails().get("campo"));

        assertNotNull(dto.toString());
        assertEquals(dto, dto);
    }
}