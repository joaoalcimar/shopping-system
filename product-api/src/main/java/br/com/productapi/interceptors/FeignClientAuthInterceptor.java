package br.com.productapi.interceptors;

import br.com.productapi.exceptions.ValidationException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class FeignClientAuthInterceptor implements RequestInterceptor {

    private final static String AUTHORIZATION = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest currentRequest = getCurrentRequest();

        template
                .header(AUTHORIZATION, currentRequest.getHeader(AUTHORIZATION));
    }

    private HttpServletRequest getCurrentRequest(){
        try{
            return ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes())
                    .getRequest();
        }catch (Exception e){
            e.printStackTrace();
            throw new ValidationException("The current request could not be processed.");
        }
    }
}
