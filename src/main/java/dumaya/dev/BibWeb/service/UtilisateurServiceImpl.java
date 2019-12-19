package dumaya.dev.BibWeb.service;

import dumaya.dev.BibWeb.modelForm.Role;
import dumaya.dev.BibWeb.modelForm.Utilisateur;
import dumaya.dev.BibWeb.repository.RoleRepository;
import dumaya.dev.BibWeb.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("utilisateurService")
public class UtilisateurServiceImpl implements UtilisateurService, UserDetailsService {

	private final UtilisateurRepository utilisateurRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UtilisateurServiceImpl(@Lazy UtilisateurRepository utilisateurRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.utilisateurRepository = utilisateurRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}


	public Utilisateur findUtilisateurByEmail(String email) {
		return utilisateurRepository.findByEmail(email);
	}


	/**
	 * Sauvegarde de l'utilisateur / cryptage mdp
	 * @param utilisateur
	 */
	public void saveUtilisateur(Utilisateur utilisateur) {
		utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));
		utilisateur.setActive(true);
		HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByRole("ROLE_ADMIN"));
		utilisateur.setRoles(roles);
		utilisateurRepository.save(utilisateur);
		//TODO passer le repo du login ds l'API
	}
	
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Utilisateur utilisateur = utilisateurRepository.findByEmail(userName);
		List<GrantedAuthority> authorities = getUserAuthority(utilisateur.getRoles());
		return buildUserForAuthentication(utilisateur, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(Utilisateur utilisateur, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(utilisateur.getEmail(), utilisateur.getPassword(), utilisateur.isActive(), true, true, true, authorities);
	}
}
