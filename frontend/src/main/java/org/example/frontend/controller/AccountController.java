package org.example.frontend.controller;

import jakarta.servlet.http.HttpSession;
import org.example.frontend.model.request.AccountRequest;
import org.example.frontend.model.response.AccountResponse;
import org.example.frontend.model.response.ApiResponse;
import org.example.frontend.model.response.AuthResponse;
import org.example.frontend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/list")
    public String getAllAccounts(Model model, @RequestParam(required = false, defaultValue = "1") Integer currentPage, @RequestParam(required = false, defaultValue = "5") Integer size, HttpSession session) {
        try {
            AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
            if (userLogin == null) {
                return "redirect:/login";
            }
            ApiResponse<List<AccountResponse>> apiResponse = accountService.getAllAccounts(currentPage, size, userLogin.getAccessToken());

            List<AccountResponse> accountList = new ArrayList<>();
            Map<String, Object> metadata = new HashMap<>();

            if (apiResponse != null) {
                accountList = apiResponse.getPayload();
                metadata = apiResponse.getMetadata();
            }

            model.addAttribute("accountList", accountList);
            model.addAttribute("totalPages", metadata.get("totalPages"));
            model.addAttribute("totalElements", metadata.get("totalElements"));
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("size", size);

            return "account-list";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/save")
    public String addAccount(@ModelAttribute AccountRequest account, BindingResult bindingResult, Model model, HttpSession session) {
        try {
            AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
            if (userLogin == null) {
                return "redirect:/login";
            }

            if (bindingResult.hasErrors()) {
                return "account-update"; // Return to the form if there are validation errors
            }

            ApiResponse apiResponse = accountService.addAccount(account, userLogin.getAccessToken());
            if (apiResponse != null && apiResponse.getStatus().equals("SUCCESS")) {
                return "redirect:/account/list";
            }
            return "error"; // Return error view if adding account fails
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/add")
    public String addAccount(Model model) {
        model.addAttribute("account", new AccountResponse());
        model.addAttribute("isUpdate", false);
        return "account-update"; // Shared view for both add and update
    }

    @GetMapping("/update/{accountId}")
    public String updateAccount(@PathVariable Long accountId, Model model, HttpSession session) {
        try {
            AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
            if (userLogin == null) {
                return "redirect:/login";
            }

            ApiResponse<AccountResponse> apiResponse = accountService.getAccountById(accountId, userLogin.getAccessToken());
            if (apiResponse != null) {
                model.addAttribute("account", apiResponse.getPayload());
                model.addAttribute("isUpdate", true);
                return "account-update"; // Shared view for both add and update
            }

            return "error"; // Return error view if fetching account data fails
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/update/{accountId}")
    public String updateAccount(@PathVariable Long accountId, @ModelAttribute AccountRequest account, BindingResult bindingResult, Model model, HttpSession session) {
        try {
            AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
            if (userLogin == null) {
                return "redirect:/login";
            }

            if (bindingResult.hasErrors()) {
                return "account-update"; // Return to the form if there are validation errors
            }

            account.setAccountId(accountId);

            ApiResponse apiResponse = accountService.updateAccount(account, userLogin.getAccessToken());
            if (apiResponse != null && apiResponse.getStatus().equals("SUCCESS")) {
                return "redirect:/account/list";
            }

            return "error"; // Return error view if updating account fails
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/detail/{accountId}")
    public String detailAccount(@PathVariable Long accountId, Model model, HttpSession session) {
        try {
            AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
            if (userLogin == null) {
                return "redirect:/login";
            }

            ApiResponse<AccountResponse> apiResponse = accountService.getAccountById(accountId, userLogin.getAccessToken());
            if (apiResponse != null) {
                model.addAttribute("account", apiResponse.getPayload());
                return "account-detail";
            }

            return "error"; // Return error view if fetching account data fails
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam(required = false) String token, Model model) {
        if (token != null && !token.isEmpty()) {
            model.addAttribute("token", token);
            return "new-password"; // Return view for entering new password
        }
        return "reset-password"; // Return to reset-password view if no token is present
    }

    @PostMapping("/request-reset-password")
    public String requestResetPassword(@RequestParam String email, Model model) {
        try {
            ApiResponse apiResponse = accountService.requestResetPassword(email);
            if (apiResponse != null && apiResponse.getStatus().equals("SUCCESS")) {
                model.addAttribute("success", "Reset password link has been sent to your email.");
                return "reset-password"; // Return to reset-password view with success message
            }
            model.addAttribute("error", "Failed to send reset password link.");
            return "reset-password"; // Return to reset-password view with error message
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "An error occurred while sending reset password link.");
            return "reset-password"; // Return to reset-password view with error message
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword, Model model) {
        try {
            ApiResponse apiResponse = accountService.resetPassword(token, newPassword);
            if (apiResponse != null && apiResponse.getStatus().equals("SUCCESS")) {
                model.addAttribute("success", "Password reset successfully.");
                return "new-password"; // Return to reset-password view with success message
            }
            model.addAttribute("error", "Failed to reset password.");
            return "new-password"; // Return to reset-password view with error message
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "An error occurred while resetting password.");
            return "new-password"; // Return to reset-password view with error message
        }
    }
}
