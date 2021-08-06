package com.ssafy.common.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.ssafy.common.websocket.CustomHandshakeHandler;
import com.ssafy.common.websocket.StompHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker//@EnableWebSocketMessageBroker is used to enable our WebSocket server
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final StompHandler stompHandler;
	
    @Override
    //클라이언트가 웹 소켓 서버에 연결하는 데 사용할 웹 소켓 엔드 포인트를 등록합니다.
    //connection을 맺을때 CORS 허용합니다.
    //클라이언트에서 소켓을 처음 연결할때 사용
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	registry.addEndpoint("/connectNotification").setAllowedOriginPatterns("*").setHandshakeHandler(new CustomHandshakeHandler()).withSockJS();
    }


    @Override
    // 한 클라이언트에서 다른 클라이언트로 메시지를 라우팅 하는 데 사용될 메시지 브로커를 구성하고 있습니다.
    public void configureMessageBroker(MessageBrokerRegistry registry) {
    	//"/notification" 시작되는 메시지가 message-handling methods으로 라우팅 되어야 한다는 것을 명시합니다.
    	// 유저가 데이터를 보낼때(send) 사용하는거
    	//세션 전체에게 보낼때? 잘 모르겟다
        registry.setApplicationDestinationPrefixes("/notification");
        
        //특정 유저에게 보낼때? 잘 모르겟다
//        registry.setUserDestinationPrefix("/user");
        
        
        //"/topic" 시작되는 메시지가 메시지 브로커로 라우팅 되도록 정의합니다.
        //메시지 브로커는 특정 주제를 구독 한 연결된 모든 클라이언트에게 메시지를 broadcast 합니다.
        // 클라이언트가 데이터를 받을때(subscribe) 사용하는거
        registry.enableSimpleBroker("/notification");
        //보통 topic은 전체(@sendTo("/topic")), queue는 특정사람(@sendToUser("/queue"))한테 보낼때 사용한다함
        //그런데 우리는 브로커 안거치고 SimpMessagingTemplate이걸로 직접 보내줄거임
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
    
}