package com.peluqueria.agenda.Security;

import java.util.function.Consumer;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

// Requisito: reenviar el JWT de la petición entrante en las llamadas WebClient a otros microservicios.
public final class TokenForwarder {

    private TokenForwarder() {
    }

    public static Consumer<HttpHeaders> forward() {
        RequestAttributes atributos = RequestContextHolder.getRequestAttributes();
        String auth = atributos instanceof ServletRequestAttributes servlet
                ? servlet.getRequest().getHeader(HttpHeaders.AUTHORIZATION) : null;
        return headers -> {
            if (auth != null) {
                headers.set(HttpHeaders.AUTHORIZATION, auth);
            }
        };
    }
}
