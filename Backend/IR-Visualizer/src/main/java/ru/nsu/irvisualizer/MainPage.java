package ru.nsu.irvisualizer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPage {
    @RequestMapping("/main")
    public String main() {
        return "Hello World!";
    }
}
