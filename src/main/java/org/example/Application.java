package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/berna/Downloads/chromedriver_win32/chromedriver.exe");
        SpringApplication.run(Application.class, args);
    }
}