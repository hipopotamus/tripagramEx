package tripagramex.global.intrastructure;

import org.thymeleaf.context.Context;

public interface TemplateManager {

    String makeTemplate(String url, Context context);
}
