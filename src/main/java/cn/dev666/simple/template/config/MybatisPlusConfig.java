package cn.dev666.simple.template.config;

import cn.dev666.simple.template.dao.UserDao;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;

@Configuration
@MapperScan(basePackageClasses = UserDao.class)
@EnableTransactionManagement
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public MybatisObjectHandler MybatisObjectHandler(){
        return new MybatisObjectHandler();
    }

    public class MybatisObjectHandler implements MetaObjectHandler {

        @Override
        public void insertFill(MetaObject metaObject) {
            setFieldValByName("createTime", LocalDateTime.now(),metaObject);
            setFieldValByName("modifyTime",LocalDateTime.now(),metaObject);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            setFieldValByName("modifyTime",LocalDateTime.now(),metaObject);
        }
    }
}
