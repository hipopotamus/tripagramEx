package tripagramex.global.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import tripagramex.global.exception.ExceptionCode;
import tripagramex.global.exception.dto.ErrorResponse;

import java.io.IOException;

public class AccountAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ExceptionCode forbiddenExCode = ExceptionCode.FORBIDDEN;
        ErrorResponse accessException =
                new ErrorResponse("Forbidden", forbiddenExCode.getMessage(), forbiddenExCode.getCode());

        String authenticationExJson = new Gson().toJson(accessException);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(authenticationExJson);
    }
}
