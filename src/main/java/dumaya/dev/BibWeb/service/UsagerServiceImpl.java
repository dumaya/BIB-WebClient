package dumaya.dev.BibWeb.service;

import dumaya.dev.BibWeb.modelAPI.Role;
import dumaya.dev.BibWeb.modelAPI.Usager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service("usagerService")
public class UsagerServiceImpl implements UsagerService, UserDetailsService {

	private final APIClientService clientService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private static final Logger LOGGER = LoggerFactory.getLogger(UsagerServiceImpl.class);

	@Autowired
	public UsagerServiceImpl(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder, APIClientService clientService) {
		this.clientService = clientService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public Usager findUsagerByEmail(String email) {
		return clientService.recupererUnUsagerParEmail(email);
	}

	/**
	 * Sauvegarde de l'utilisateur / cryptage mdp
	 * @param usager
	 */
	public void saveUsager(Usager usager) {
		LOGGER.debug("maj usager");
		usager.setPassword(bCryptPasswordEncoder.encode(usager.getPassword()));
		usager.setActive(true);
		HashSet<Role> roles = new HashSet<Role>();
		roles.add(clientService.recupererUnRoleParRole("ROLE_ADMIN"));
		usager.setRoles(roles);
		clientService.creerUsager(usager);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Usager usager = clientService.recupererUnUsagerParEmail(userName);
		List<GrantedAuthority> authorities = getUserAuthority(usager.getRoles());
		return buildUserForAuthentication(usager, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(Usager usager, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(usager.getEmail(), usager.getPassword(), usager.isActive(), true, true, true, authorities);
	}
}
