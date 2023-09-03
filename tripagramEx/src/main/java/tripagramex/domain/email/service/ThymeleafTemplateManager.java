package tripagramex.domain.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class ThymeleafTemplateManager implements TemplateManager{

    private final TemplateEngine templateEngine;

    @Override
    public String makeTemplate(String url, Context context) {
        return templateEngine.process(url, context);
    }
}
