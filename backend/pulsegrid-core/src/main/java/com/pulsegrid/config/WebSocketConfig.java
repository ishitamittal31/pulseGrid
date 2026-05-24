package com.pulsegrid.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

    @Configuration
    @EnableWebSocketMessageBroker
    public class WebSocketConfig
            implements WebSocketMessageBrokerConfigurer {

        @Override
        public void configureMessageBroker( // how msgs move inside spring
                MessageBrokerRegistry registry
        ) {

            registry.enableSimpleBroker("/topic"); // an in-memory msg broker meaning - i want messages for this topic

            registry.setApplicationDestinationPrefixes("/app");
        }

        @Override
        public void registerStompEndpoints(
                StompEndpointRegistry registry
        ) {

            registry.addEndpoint("/ws")
                    .setAllowedOriginPatterns("*") //cors for websockets ?
                    .withSockJS();
        }
    }

