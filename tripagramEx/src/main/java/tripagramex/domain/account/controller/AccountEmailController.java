package tripagramex.domain.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tripagramex.domain.account.service.AccountEmailService;
import tripagramex.domain.account.validation.AccountValidator;

@Controller
@RequestMapping("/accountEmail")
@RequiredArgsConstructor
public class AccountEmailController {

    @Value("${domain.back}")
    private String backDomain;

    @Value("${domain.front}")
    private String frontDomain;

    private final AccountEmailService accountEmailService;
    private final AccountValidator accountValidator;

    @ResponseBody
    @PostMapping("/tempPasswordGuid/{email}")
    public void sendTempPasswordGuid(@PathVariable String email) {
        accountValidator.verifyExistsByEmail(email);
        accountValidator.verifySendTempPasswordEmailAt(email);
        accountEmailService.sendTempPasswordGuid(email, backDomain);
    }

    @GetMapping("/tempPassword/{accountId}")
    public String applyTempPassword(@PathVariable Long accountId, @RequestParam String tempPassword) {

        try {
            accountValidator.verifyExistsById(accountId);
            accountValidator.verifyApplyTempPasswordAt(accountId);
            accountEmailService.applyTempPassword(accountId, tempPassword);
        } catch (Exception e) {
            return "/mail/passwordEx";
        }

        return "redirect:http://" + frontDomain;
    }

}
