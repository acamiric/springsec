package com.learning.springsecurity.controllers;


import com.learning.springsecurity.entity.Client;
import com.learning.springsecurity.entity.Role;
import com.learning.springsecurity.repository.ClientRepository;
import com.learning.springsecurity.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@AllArgsConstructor
public class GameController {

    private final GameService gameService;
    private final ClientRepository clientRepository;

    @GetMapping("/{email}/{guess}")
    public String rollTheDice(@PathVariable(name = "email") String email, @PathVariable(name = "guess") Integer guess) {
        Client client = clientRepository.findByEmail(email).orElseThrow(() -> new
            UsernameNotFoundException("Client details not found for the client: " + email));

        if (client.getRole() == Role.ADMIN) {
            return "ADMINS CAN NOT PLAY!!!";
        }
        double balance = client.getAccount().getBalance();

        if (balance<50) return "You do not have the money to play!";

        if (guess > 6 || guess < 1) {
            return "Numbers go from 1 to 6";
        }
        boolean result = gameService.rollTheDice(guess);
        if (result) {
            balance += 200;
            client.getAccount().setBalance(balance);
            clientRepository.save(client);
            return "Bravo you won 200$";
        } else {
            balance -= 50;
            client.getAccount().setBalance(balance);
            clientRepository.save(client);
            return "Better luck next time! You lost 50$";
        }

    }
}
