package com.yeta.pps;

import com.yeta.pps.interceptor.CommonInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.yeta.pps.mapper")       //扫描Mybatis mapper包路径
@ServletComponentScan       //扫描配置的过滤器和拦截器，因为这两个是基于Servlet的
@Configuration      //注册Bean
@EnableSwagger2     //开启Swagger支持
public class PpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PpsApplication.class, args);
    }

    /**
     * 配置拦截器
     * 由于添加拦截器需要继承WebMvcConfigurerAdapter类
     * 需要用@Bean将MyInterceptor注入容器，才能在拦截器中注入，否为注入为空
     * 或者在自定义拦截器类上加上@Component注解，通过注入方式配置
     */
    @Configuration
    class WebMvcConfigurer extends WebMvcConfigurerAdapter {

		/*@Bean
		public MyInterceptor myInterceptor() {
			return new MyInterceptor();
		}*/

        @Autowired
        private CommonInterceptor commonInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            //registry.addInterceptor(commonInterceptor).addPathPatterns("/**");
            //super.addInterceptors(registry);
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/upload/**").addResourceLocations("classpath:upload/");
            registry.addResourceHandler("/download/**").addResourceLocations("classpath:download/");
            registry.addResourceHandler("/upload/**").addResourceLocations("file:upload/");
            registry.addResourceHandler("/download/**").addResourceLocations("file:download/");
            super.addResourceHandlers(registry);
        }
    }

    /**
     * 配置Swagger
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("这是标题")
                        .description("这是描述")
                        .termsOfServiceUrl("这是url")
                        .contact("这是联系人")
                        .version("这是版本")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yeta.pps.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
