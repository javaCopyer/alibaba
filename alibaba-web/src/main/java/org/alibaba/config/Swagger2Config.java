package org.alibaba.config;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger api文档配置类
 * @author sky
 *
 */
@EnableSwagger2
public class Swagger2Config {
	 @Bean
	    public Docket api() {

	        return new Docket(DocumentationType.SWAGGER_2)
	                .groupName("alibaba") //  组名
	                .select()  // 选择哪些路径和api会生成document
	                  .apis(RequestHandlerSelectors.basePackage("org.alibaba.controller"))//// 对packageName包中的api进行监控
	                  .paths(PathSelectors.any())      //
	                  .build()
	                .apiInfo(apiInfo());
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("阿里巴巴")//You application title. Required.
	                .description("API Document")//API description. Arbitrary text in CommonMark or HTML.
	                .version("1.0.0")//API version. You can use semantic versioning like 1.0.0, or an arbitrary string like 0.99-beta. Required.
	                .build();
	    }

}
