package tripagramex.infrastructure.mock;

import org.thymeleaf.context.Context;
import tripagramex.domain.email.service.TemplateManager;

public class MockTemplateManager implements TemplateManager {

    @Override
    public String makeTemplate(String url, Context context) {
        return "testTemplate";
    }
}
