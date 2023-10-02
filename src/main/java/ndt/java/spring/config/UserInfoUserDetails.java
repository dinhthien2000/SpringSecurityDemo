package ndt.java.spring.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ndt.java.spring.entities.UserInfo;


@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoUserDetails implements UserDetails {

	/**
	 * UserInfo, chúng ta cần phải convert từ UserInfo sang UserDetails. 
	 * Để có thể làm được điều đó, chúng ta cần tạo 1 class mới là UserInfoUserDetails và implements UserDetails và khai báo các properties 
	 * chúng ta muốn convert sang UserDetails bao gồm: username, password và authorities
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String password;
	private List<GrantedAuthority> authorities;

	
	public UserInfoUserDetails(UserInfo userInfo){
		name = userInfo.getName();
		password =userInfo.getPassword();
		authorities = Arrays.stream(userInfo.getRoles().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
