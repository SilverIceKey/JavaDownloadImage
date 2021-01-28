package com.sk.downloadimage;

import com.sk.downloadimage.base.ControllerManager;
import com.sk.downloadimage.features.main.MainController;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.InputStream;
import java.util.Scanner;

@SpringBootApplication
@EntityScan(basePackages = {"com.sk.downloadimage"})
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
//        initMybatis();
        ControllerManager.getInstance().addController(new MainController());
    }

    private void initMybatis(){

        /**
         * 映射sql的标识字符串，
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
    }
}
