package com.itq.autoService.service;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs //Habilita a la clase con la funcionalidad para crear un WS SOAP
@Configuration //Habilita a la clase con la funcionalidad para procesar Beans

public class AutosWebServiceConfig extends WsConfigurerAdapter{
	
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}
	
	
    @Bean(name = "servicioTallert") // Nombre de la operación "Crear vehículo"
    public Wsdl11Definition crearVehiculoWsdl() {
        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
        wsdl11Definition.setWsdl(new ClassPathResource("wsdl/servicioTallert.wsdl")); // Archivo WSDL para "Crear vehículo"
        return wsdl11Definition;
    }

    @Bean(name = "servicioTallert") // Nombre de la operación "Crear conductor"
    public Wsdl11Definition crearConductorWsdl() {
        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
        wsdl11Definition.setWsdl(new ClassPathResource("wsdl/servicioTallert.wsdl")); // Archivo WSDL para "Crear conductor"
        return wsdl11Definition;
    }
	
	@Bean (name = "taller")
	public XsdSchema tallerSchema() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/taller.xsd"));
	}
	
	
	@Bean(name = "commons")
	public XsdSchema commonsSchema() {
	    return new SimpleXsdSchema(new ClassPathResource("xsd/commons.xsd"));
	}



}
