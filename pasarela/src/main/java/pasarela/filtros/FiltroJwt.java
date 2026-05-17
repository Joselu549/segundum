package pasarela.filtros;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pasarela.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class FiltroJwt extends ZuulFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtils jwtUtils;

    public FiltroJwt(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String uri = ctx.getRequest().getRequestURI();
        return !uri.startsWith("/auth/") && !uri.startsWith("/oauth2/") && !uri.startsWith("/login/oauth2/");
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            return null;
        }

        String token = authorization.substring(BEARER_PREFIX.length()).trim();
        try {
            Claims claims = jwtUtils.validateToken(token);
            ctx.addZuulRequestHeader("X-User-Id", claims.getSubject());
            ctx.addZuulRequestHeader("X-User-Roles", claims.get("roles", String.class));
        } catch (Exception e) {
            rechazar(ctx, "Token inválido o expirado");
        }
        return null;
    }

    private void rechazar(RequestContext ctx, String mensaje) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        ctx.setResponseBody(mensaje);
        ctx.getResponse().setContentType("text/plain;charset=UTF-8");
    }
}
