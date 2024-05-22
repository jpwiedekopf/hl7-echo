package de.uniluebeck.imi.ehealth.hl7echo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.hl7")
public class Hl7Properties {

    private int port = 7777;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
