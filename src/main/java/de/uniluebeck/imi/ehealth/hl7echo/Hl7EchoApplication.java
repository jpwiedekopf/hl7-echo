package de.uniluebeck.imi.ehealth.hl7echo;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.HL7Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties(Hl7Properties.class)
public class Hl7EchoApplication {

    private final Logger logger = LoggerFactory.getLogger("Hl7Echo");

    public static void main(String[] args) {
        SpringApplication.run(Hl7EchoApplication.class, args);
    }

    @Bean
    HapiContext hapiContext() {
        return new DefaultHapiContext();
    }

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext ctx,
                                        @Autowired Hl7Properties properties,
                                        @Autowired HapiContext hapiContext) {
        return args -> {
            logger.info("Starting HL7 Echo Server on port %s".formatted(properties.getPort()));
            HL7Service server = hapiContext.newServer(properties.getPort(), false);
            EchoReceiverApplication application = new EchoReceiverApplication();
            server.registerApplication("*", "*", application);
            server.registerConnectionListener(new EchoConnectionListener());
            server.setExceptionHandler(new EchoExceptionHandler(hapiContext));
            server.start();
        };
    }
}
