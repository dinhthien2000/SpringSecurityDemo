package ndt.java.spring.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ndt.java.spring.config.UserInfoUserDetails;
import ndt.java.spring.entities.UserInfo;
import ndt.java.spring.repository.UserInfoRepository;

@Service
@RequiredArgsConstructor
// kế thừa interface UserDetailsService để loadUserByUsername và trả về interface UserDetailsService  ( interface UserDetailsService được triên khai bởi UserInforDetailService)
// nên sẽ sử dụng các phương thức và khai báo hay định nghĩa của UserInforDetailService
public class UserInforDetailService implements UserDetailsService {

	/*
	 * 
	 * @Repository
	 * Generates a constructor with required arguments.Required arguments are final fields and fields with constraints such as @NonNull. 
	 * */
	final UserInfoRepository repository;
	
//	public UserInforDetailService(UserInfoRepository repository) {
//		// TODO Auto-generated constructor stub
//		this.repository = repository;
//	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo = repository.findByName(username);
		return userInfo.map(UserInfoUserDetails::new)
				.orElseThrow(()->new UsernameNotFoundException("user not found: " + username))
				;
	}

}
