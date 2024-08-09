package com.learning.springsecurity.service;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    Random random = new Random();

    public Boolean rollTheDice(int guess) {
        int randomNumber = random.nextInt(6) + 1;
        System.out.println("The dice rolled: " + randomNumber + " you guessed: " + guess);
        return guess == randomNumber;
    }
}
