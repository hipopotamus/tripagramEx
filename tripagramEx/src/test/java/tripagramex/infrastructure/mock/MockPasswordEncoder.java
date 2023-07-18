package tripagramex.infrastructure.mock;

import tripagramex.global.intrastructure.PasswordEncoder;

public class MockPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return "[Encode]" + password;
    }
}
