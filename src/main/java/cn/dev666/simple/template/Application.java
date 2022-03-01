package cn.dev666.simple.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *  声明为 Web 控制类（同 @Controller）
 *  声明方法响应对象为 Http 响应体（同 @ResponseBody）
 */
@RestController
/*
 *  声明为配置类（同 @Configuration）
 *  启用自动配置（同 @EnableAutoConfiguration）
 *  启用组件扫描（同 @ComponentScan）
 */
@SpringBootApplication
public class Application {

    @GetMapping("/")              // 接受 Http GET 请求，URL为 /, 返回 hello, dev666
    public String index(){
        return "hello, dev666";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
