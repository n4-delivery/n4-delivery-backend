package com.sparta.n4delivery.aop;

import com.sparta.n4delivery.order.dto.response.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j(topic = "OrderAop:")
@Aspect
@Component
@RequiredArgsConstructor
public class OrderAop {
    @Pointcut("execution(* com.sparta.n4delivery.order.service.OrderService.createOrder(..))")
    private void createOrder() {}

    @Pointcut("execution(* com.sparta.n4delivery.order.service.OrderService.updateOrder(..))")
    private void updateOrder() {}

    @Around("createOrder() || updateOrder()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        OrderResponseDto output = (OrderResponseDto)joinPoint.proceed();
        StringBuilder sb = new StringBuilder();
        sb.append("[ 요청 시각: ");
        sb.append(output.getUpdatedAt());
        sb.append(" ] [ 가게 ID: ");
        sb.append(output.getStoreId());
        sb.append(" ] [주문 ID: ");
        sb.append(output.getId());
        sb.append(" ]");
        log.info(sb.toString());
        return output;
    }
}
