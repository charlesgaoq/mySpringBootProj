package com.gao.mybaties.mybatiestest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.gao.mybaties.mybatiestest.mapper.TeacherMapper;
import com.gao.mybaties.mybatiestest.mapper.UserModelMapper;
import com.gao.mybaties.mybatiestest.model.Teacher;
import com.gao.mybaties.mybatiestest.model.UserModel;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Hello World!" );
        
        String resource = "mybatisConfig.xml";
        // 通过流将核心配置文件读取进来
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过核心配置文件创建会话工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        
        // 通过会话工厂创建会话
        SqlSession session = factory.openSession();
        
//        UserModelMapper userMapper = session.getMapper(UserModelMapper.class);
//        UserModel user = new UserModel();
//        user.setUseAge(18);
//        user.setUseName("武汉");
//        user.setUseIdNo("110");
//        int ii = userMapper.insert(user);
//        System.out.println(ii);
//        UserModel user = userMapper.selectByPrimaryKey(3);
//        System.out.println(user.getTeacher());
//        System.out.println(user.getUseName());
        TeacherMapper teacherMapper = session.getMapper(TeacherMapper.class);
        List<Teacher> tt = new ArrayList<Teacher>();
        for(int i = 10; i< 14;i++) {
	        Teacher t = new Teacher();
	        t.setName("gaoqiang_" + i);
	        t.setId(i + 2);
//	        teacherMapper.insert(t);
	        tt.add(t);
        }
        teacherMapper.batchDelete(tt);
        session.commit();
        session.close();
        //UserModel user = userMapper.selectByPrimaryKey(2);
        //System.out.println(user.getUseEmail());
        
    }
}
