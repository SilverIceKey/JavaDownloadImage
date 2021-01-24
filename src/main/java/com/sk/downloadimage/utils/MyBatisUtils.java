package com.sk.downloadimage.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {
    private SqlSession mSqlSession;


    public MyBatisUtils() {
        try {
            //mybatis的配置文件
            String resource = "conf.xml";
            //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
            InputStream is = Resources.getResourceAsStream(resource);
            //构建sqlSession的工厂
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
            //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
            //Reader reader = Resources.getResourceAsReader(resource);
            //构建sqlSession的工厂
            //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            //创建能执行映射文件中sql的sqlSession
            mSqlSession = sessionFactory.openSession();
        }catch (IOException e){

        }
    }

    public static SqlSession getInstance(){
        return MyBatisUtilsHolder.mInstance.mSqlSession;
    }

    private static class MyBatisUtilsHolder{
        public static MyBatisUtils mInstance = new MyBatisUtils();
    }
}
