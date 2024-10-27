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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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
            model.addAttribute("error", "An error occurred while fetching account data.");
            return "error";
        }
    }


    // post new account
    @PostMapping("/save")
    public String addAccount(@ModelAttribute AccountRequest account, Model model, HttpSession session) {
        try {
            AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
            if (userLogin == null) {
                return "redirect:/login";
            }
            ApiResponse apiResponse = accountService.addAccount(account, userLogin.getAccessToken());
            if (apiResponse != null && apiResponse.getStatus().equals("SUCCESS")) {
                return "redirect:/account/list";
            }
            model.addAttribute("error", "An error occurred while adding account.");
            return "error";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "An error occurred while adding account.");
            return "error";
        }
    }

    // Add new account
    @GetMapping("/add")
    public String addAccount(Model model) {
        model.addAttribute("account", new AccountResponse());
        model.addAttribute("isUpdate", false); // Indicate that this is for adding a new account
        return "account-update"; // The shared view for both add and update
    }

    // Update account
    @GetMapping("/update/{accountId}")
    public String updateAccount(@PathVariable Long accountId, Model model, HttpSession session) {
        try {
            AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
            if (userLogin == null) {
                return "redirect:/login"; // Redirect to login if user is not authenticated
            }

            ApiResponse<AccountResponse> apiResponse = accountService.getAccountById(accountId, userLogin.getAccessToken());
            if (apiResponse != null) {
                model.addAttribute("account", apiResponse.getPayload());
                model.addAttribute("isUpdate", true); // Indicate that this is for updating an account
                return "account-update"; // The shared view for both add and update
            }

            model.addAttribute("error", "An error occurred while fetching account data.");
            return "error";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "An error occurred while fetching account data.");
            return "error";
        }
    }

    @PostMapping("/update/{accountId}")
    public String updateAccount(@PathVariable Long accountId, @ModelAttribute AccountRequest account, Model model, HttpSession session) {
        try {
            AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
            if (userLogin == null) {
                return "redirect:/login"; // Redirect to login if user is not authenticated
            }

            // Set the account ID in the account request object (if not already set in the form)
            account.setAccountId(accountId);

            ApiResponse apiResponse = accountService.updateAccount(account, userLogin.getAccessToken());
            if (apiResponse != null && apiResponse.getStatus().equals("SUCCESS")) {
                return "redirect:/account/list";
            }

            model.addAttribute("error", "An error occurred while updating the account.");
            return "error";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "An error occurred while updating the account.");
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
            try {
                ApiResponse<AccountResponse> apiResponse = accountService.getAccountById(accountId, userLogin.getAccessToken());
                if (apiResponse != null) {
                    model.addAttribute("account", apiResponse.getPayload());
                    return "account-detail";
                }
                model.addAttribute("error", "An error occurred while fetching account data.");
                return "error";
            } catch (HttpClientErrorException.Forbidden ex) {
                model.addAttribute("error", "You do not have permission to access this resource.");
                return "error";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "An error occurred while fetching account data.");
            return "error";
        }
    }
}
