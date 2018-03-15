package ma.najeh.ibnouzouhr.config;

import ma.najeh.ibnouzouhr.dto.UserDetailsDto;
import ma.najeh.ibnouzouhr.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.DASHBOARD;

@Component
public class CustomLoginAndLogoutHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler, AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private Logger logger=Logger.getLogger(CustomLoginAndLogoutHandler.class);

    @Autowired
    public CustomLoginAndLogoutHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        logger.info("onLogoutSuccess ...");
        if (authentication!=null){
            UserDetailsDto userDetails = (UserDetailsDto) authentication.getPrincipal();
            if (userDetails!=null){
                userRepository.updateLogoutDate(new Date(),userDetails.getId());
            }
        }

        httpServletResponse.sendRedirect("/");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        logger.info("onAuthenticationSuccess ...");
        UserDetailsDto userDetails = (UserDetailsDto) authentication.getPrincipal();
        userRepository.updateLoginDate(new Date(),userDetails.getId());
        httpServletResponse.sendRedirect(DASHBOARD);
    }
}