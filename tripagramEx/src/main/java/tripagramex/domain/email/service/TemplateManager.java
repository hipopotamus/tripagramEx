package tripagramex.domain.email.service;

import org.thymeleaf.context.Context;

public interface TemplateManager {

    String makeTemplate(String url, Context context);
}
