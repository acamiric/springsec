package com.learning.springsecurity.config;

import com.learning.springsecurity.entity.Client;
import com.learning.springsecurity.repository.ClientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyClientDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    //ako koristimo ovo onda nam ne treba user i roles tabela
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username).orElseThrow(() -> new
            UsernameNotFoundException("Client details not found for the client: " + username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(client.getRole().name()));
        return new User(client.getEmail(), client.getPassword(), authorities);
    }
}
