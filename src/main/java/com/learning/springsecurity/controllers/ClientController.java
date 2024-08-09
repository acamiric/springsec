package com.learning.springsecurity.controllers;

import com.learning.springsecurity.entity.Account;
import com.learning.springsecurity.entity.Client;
import com.learning.springsecurity.entity.Role;
import com.learning.springsecurity.repository.ClientRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository repository;
    private final PasswordEncoder passwordEncoder;

    //samo ulogovanim userima
    @GetMapping
    public ResponseEntity<List<Client>> getAll() {
        List<Client> clients = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable(name = "id") Integer id) {
        Optional<Client> client = repository.findById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    //dostupno svima
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Client client) {
        try {
            String hashPwd = passwordEncoder.encode(client.getPassword());
            client.setPassword(hashPwd);
            Account clientAccount = new Account();
            clientAccount.setBalance(1000.0);
            client.setAccount(clientAccount);
            //za sada samo useri mogu da se registruju
            client.setRole(Role.USER);
            Client savedClient = repository.save(client);

            if (savedClient.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).
                    body("Given client details are successfully registered");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("Client registration failed");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                body("An exception occurred: " + ex.getMessage());
        }
    }
}
