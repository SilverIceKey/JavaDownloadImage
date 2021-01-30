package com.sk.downloadimage;

import com.sk.downloadimage.base.ControllerManager;
import com.sk.downloadimage.features.main.MainController;
import com.sk.downloadimage.utils.ConfigUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ConfigUtils.init();
        ControllerManager.getInstance().addController(new MainController());
    }
}
