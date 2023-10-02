package ndt.java.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
import ndt.java.spring.repository.UserInfoRepository;
import ndt.java.spring.service.UserInforDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	static String[] list_path = new String[] {"/","/hello","/swagger-ui/**",
            "/swagger-resources/*",
            "/v3/api-docs/**"} ;
	
	final UserInfoRepository repository;
	
	@Bean
	// authorization, chính sách bảo mật trang web
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        return http.csrf(csrf -> csrf.disable())
	                .authorizeHttpRequests( auth -> auth
	                		.requestMatchers(list_path).permitAll()
	                		.requestMatchers("/user/new").permitAll()
	                		.requestMatchers("/customer/**").authenticated()
	                		
	                		// .anyRequest().authenticated()
	                	
	                		)
	                .formLogin(form->form.permitAll())
	                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll())
	                .build();
	}
	
	@Bean
	// authentication, xác thực để truy cập trang web
	public UserDetailsService userDetailsService() {
//		UserDetails admin =  User.withUsername("hach")
//                .password(encoder.encode("hacheery"))
//                .roles("ADMIN")
//                .build();
//		UserDetails user = User.withUsername("user")
//				.password(encoder.encode("pwd1"))
//				.roles("USER")
//				.build();
//		return new InMemoryUserDetailsManager(admin,user);
		
		return new UserInforDetailService(repository);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	//Spring Security tìm kiếm AuthenticationProvider thích hợp để xử lý quá trình xác thực
	@Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
}
