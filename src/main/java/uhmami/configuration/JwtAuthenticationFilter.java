package uhmami.configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
//import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import static uhmami.configuration.TokenConfig.CONTENT_TYPE;
import static uhmami.configuration.TokenConfig.HEADER_AUTHORIZATION;
import static uhmami.configuration.TokenConfig.PREFIX_TOKEN;
import static uhmami.configuration.TokenConfig.SECRET_KEY;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uhmami.modelo.entities.Usuario;

//Generar token
public class JwtAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		Usuario usuario = null;
		String username = null;
		String password = null;
		
		try {
			usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			username = usuario.getUsername();
			password = usuario.getPassword();
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
		String username = user.getUsername();
		Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
		
		Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username", username)
                .build();
		
		String jws = Jwts.builder()
				.subject(username)
				.claims(claims)
				.expiration(new Date(System.currentTimeMillis() + 3600000)) //el token durará una hora: fecha actual + 3600000 milisegundos que equivalen a una hora
				.issuedAt(new Date())
				.signWith(SECRET_KEY)
				.compact();
		response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + jws);
		
		Map<String, String> body = new HashMap<>();
		body.put("jws", jws);
		body.put("username", username);
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setContentType(CONTENT_TYPE);
		response.setStatus(200);
		
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, String> body = new HashMap<>();
		body.put("message", "Error en la autenticacion. Username o password incorrectos");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType(CONTENT_TYPE);
	}
	
	
	
	
}
