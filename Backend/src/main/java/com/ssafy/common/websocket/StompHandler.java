package com.ssafy.common.websocket;

import java.util.Objects;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.ssafy.common.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("message:" + message);
        System.out.println("헤더 : " + message.getHeaders());
        System.out.println("토큰" + accessor.getNativeHeader("Authorization")+"\n");
        
    

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            System.out.println("jwt "+accessor.getFirstNativeHeader("Authorization").substring(7));
            System.out.println("jwt boolean "+tokenProvider.validateToken(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).substring(7)));
            
        	if(!tokenProvider.validateToken(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).substring(7))) {
        		throw new IllegalStateException("토큰이 유효하지 않습니다");        
        	}
        		
        }
        return message;
    }
}