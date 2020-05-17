/*package com.lagou.edu.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*//**
 * 定义全局过滤器，会对所有路由生效
 *//*
@Slf4j
@Component  // 让容器扫描到，等同于注册了
public class BlackListFilter implements GlobalFilter, Ordered {
    private static final String MAX_AGE = "18000L";
    private static final Integer OPEN_TIME = 5*1000;
    private static final Integer COUNT = 10;
    // 模拟黑名单（实际可以去数据库或者redis中查询）
    private static List<String> blackList = new ArrayList<>();
    private Map<String,List<Long>> clientIpRecord = new HashMap<String,List<Long>>();
    static {
        blackList.add("0:0:0:0:0:0:0:1");  // 模拟本机地址
    }

    *//**
     * 过滤器核心方法
     * @param exchange 封装了request和response对象的上下文
     * @param chain 网关过滤器链（包含全局过滤器和单路由过滤器）
     * @return
     *//*
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 思路：获取客户端ip，判断是否在黑名单中，在的话就拒绝访问，不在的话就放行
        // 从上下文中取出request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        HttpHeaders requestHeaders = request.getHeaders();
        HttpHeaders headers = response.getHeaders();
        if(StringUtils.isNotBlank(requestHeaders.getOrigin())){
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
        }
        headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders
                .getAccessControlRequestHeaders());
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
        // 从request对象中获取客户端ip
        String clientIp = request.getRemoteAddress().getHostString();
        URI uri = request.getURI();
        String path = uri.getPath();
        if(path.contains("register")){
            List<Long> times = clientIpRecord.get(clientIp);
            if(times != null){
                Long currentTime = System.currentTimeMillis();
                times.add(currentTime);
                int count = 0;
                for(int i = times.size()-1;i>=0;i--){
                    if(currentTime-OPEN_TIME>=times.get(i)){
                        count++;
                    }else{
                        break;
                    }
                }
                if(count>=COUNT){
                    response.setStatusCode(HttpStatus.SEE_OTHER); // 状态码
                    String data = "Request be denied!";
                    DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
                    return response.writeWith(Mono.just(wrap));
                }
            }else{
                List<Long> timeList = new ArrayList<Long>();
                timeList.add(System.currentTimeMillis());
                clientIpRecord.put(clientIp,timeList);
            }
        }



        // 拿着clientIp去黑名单中查询，存在的话就决绝访问
        if(blackList.contains(clientIp)) {
            // 决绝访问，返回
            response.setStatusCode(HttpStatus.UNAUTHORIZED); // 状态码
            //log.debug("=====>IP:" + clientIp + " 在黑名单中，将被拒绝访问！");
            String data = "Request be denied!";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            //return response.writeWith(Mono.just(wrap));
        }

        // 合法请求，放行，执行后续的过滤器
        return chain.filter(exchange);
    }


    *//**
     * 返回值表示当前过滤器的顺序(优先级)，数值越小，优先级越高
     * @return
     *//*
    @Override
    public int getOrder() {
        return 0;
    }
}
*/