//package br.com.validator.config;
//
//import br.com.validator.models.ErrorResponse;
//import com.fasterxml.classmate.TypeResolver;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.web.bind.annotation.RequestMethod;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.builders.ResponseMessageBuilder;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ResponseMessage;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@EnableSwagger2
//@Configuration
//public class SwaggerConfig {
//    @Autowired
//    private Environment env;
//    @Bean
//    public Docket api(@Autowired TypeResolver typeResolver) {
//        return new Docket(DocumentationType.SWAGGER_2).select()
//                .apis(RequestHandlerSelectors.basePackage("br.com.validator")).paths(PathSelectors.any()).build()
//                .apiInfo(apiInfo())
//
//                .additionalModels(typeResolver.resolve (ErrorResponse.class) )
//                .globalResponseMessage(RequestMethod.GET, responseMessageError())
//                .globalResponseMessage(RequestMethod.POST, responseMessageError())
//                .globalResponseMessage(RequestMethod.PUT, responseMessageError())
//                .globalResponseMessage(RequestMethod.DELETE, responseMessageError())
//                .globalResponseMessage(RequestMethod.OPTIONS, responseMessageError()) ;
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title(env.getProperty("info.app.name", "API - Customers"))
//                .description("API customers"+ "\nEnvironment: " + String.join(", ", env.getActiveProfiles()))
//                .version(env.getProperty("info.app.verscion", env.getProperty("info.app.bootstrap.version", "0.0.1-SNAPSHOT")))
//                .build();
//    }
//
//    private List<ResponseMessage> responseMessageError() {
//        List<ResponseMessage> list = new ArrayList<>();
//        list.add(getErrorResponse(400, "Bad Request - Erro genérico para requisições que não puderam ser processadas devido ao formato não aderente ao media-type"));
//        list.add(getErrorResponse(401, "Unauthorized - Credenciais não reconhecidas pelo servidor"));
//        list.add(getErrorResponse(403, "Forbidden - Credencial não possui privilégios suficentes para acessar o recurso"));
//        list.add(getErrorResponse(404, "Not Found - A URI informada na requisição não existe"));
//        list.add(getErrorResponse(405, "Method Not Allowed - Método HTTP não permitido para o recurso"));
//        list.add(getErrorResponse(412, "Precondition Fail - Algo na requisição não está aderente ao esperado"));
//        list.add(getErrorResponse(413, "Entity Too Large - Requisição excedeu o limite de capacidade de processamento do servidor"));
//        list.add(getErrorResponse(415, "Unsupported Media Type - O payload está em um formato que o servidor não reconhece"));
//        list.add(getErrorResponse(422, "Unprocessable Entity - Ocorreu algum erro de negócio com a mensagem. Sintaticamente correto, semanticamente não"));
//        list.add(getErrorResponse(429, "Too Many Requests - O servidor está limitando seu acesso porque você atingiu o limite máximo de requisições"));
//        list.add(getErrorResponse(500, "Internal Server Error - A requisição está certa, porém algum erro aconteceu no servidor"));
//        list.add(getErrorResponse(502, "Bad Gateway - O API Gateway não conseguiu identificar exatamente o erro reportado pelo backend"));
//        list.add(getErrorResponse(503, "Service Unavailable - O servidor não consegue processar agora por sobrecarga ou manutenção"));
//        return list;
//    }
//
//    private ResponseMessage getErrorResponse(int code, String message) {
//        return new ResponseMessageBuilder().responseModel(new ModelRef("ErrorResponse")).code(code).message(message).build();
//    }
//}