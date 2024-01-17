package com.poly.controller;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import com.poly.dao.AccountAddressDao;
import com.poly.dao.AccountsDao;
import com.poly.entity.AccountAddress;
import com.poly.entity.Accounts;
import com.poly.services.AccountService;

@Controller
public class HandleController {

    @Autowired
    AccountsDao accountsDao;

    @Autowired
    AccountAddressDao accountAddressDao;

    @Autowired
    AccountService accountService;

    @GetMapping("/register")
    public String register(Model model) {
        Accounts account = new Accounts();
        model.addAttribute("item", account);
        return "handle/register.html";
    }

    @PostMapping("/register")
    public String registerSuccess(Model model, RedirectAttributes m,
            Accounts account, @RequestParam("repeatPassword") String repeatPassword) throws IOException {

        if (repeatPassword.equals(account.getPassword())) {
            Accounts acc = accountsDao.findByUsername(account.getUsername());

            if (acc != null) {
                model.addAttribute("mess", "Username đã tồn tại");
                model.addAttribute("item", account);
            } else {
                account.setActivated(true);
                accountsDao.save(account);

                m.addFlashAttribute("mess", "Đăng ký thành công");
                return "redirect:/register";
            }
        } else {
            model.addAttribute("mess", "Password và Repeat your password không giống nhau");
            model.addAttribute("item", account);
        }
        return "handle/register";
    }

    @GetMapping("/forgotpass")
    public String forgotPassword() {
        return "handle/forgotPassword";
    }

    @PostMapping("/forgotpass")
    public String forgotpassSuccess(Model model, RedirectAttributes m, @RequestParam("username") String username,
            @RequestParam("email") String email) throws IOException {

        Accounts acc = accountsDao.findByUsername(username);

        if (acc != null) {
            if (email.equals(acc.getEmail())) {
                int random = ThreadLocalRandom.current().nextInt(0, 9999);
                acc.setPassword(String.valueOf(random));
                accountsDao.save(acc);
                m.addFlashAttribute("mess", "Password được cập nhật là: " + random);
            } else {
                m.addFlashAttribute("mess", "Email không trùng khớp");
                return "redirect:/forgotpass";
            }
        } else {
            m.addFlashAttribute("mess", "User name không tồn tại");
            return "redirect:/forgotpass";
        }

        return "redirect:/forgotpass";
    }

    @GetMapping("/changepass")
    public String changePassword() {
        return "handle/changePassword";
    }

    @PostMapping("/changepass")
    public String changePasswordSuccess(Model model, RedirectAttributes m, @RequestParam("password") String password,
            @RequestParam("newpassword") String newpassword, @RequestParam("confirmpassword") String confirmpassword,
            HttpServletRequest request) {
        String user = request.getRemoteUser();
        if (user != null) {
            if (newpassword.equals(confirmpassword)) {
                Accounts acc = accountsDao.findByUsername(user);
                if (acc != null) {
                    if (password.equals(acc.getPassword())) {
                        acc.setPassword(newpassword);
                        accountsDao.save(acc);
                        model.addAttribute("mess", "Đổi mật khẩu thành công!");
                    } else {
                        m.addFlashAttribute("mess", "Password không đúng!");
                        return "redirect:/changepass";
                    }
                }
            } else {
                m.addFlashAttribute("mess", "New Password và Confirm password phải giống nhau!");
                return "redirect:/changepass";
            }

        } else {
            m.addFlashAttribute("mess", "Vui lòng đăng nhập!");
            return "redirect:/changepass";
        }
        return "handle/changePassword.html";
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model, HttpServletRequest request) {
        Accounts acc = new Accounts();
        String user = request.getRemoteUser();
        if (StringUtils.isEmpty(user)) {
            return "redirect:/login?mess=1";
        } else {
            acc = accountsDao.findByUsername(user);
            AccountAddress accAdd = accountAddressDao.findAccountAddressByUsername(user);
            if (accAdd != null)
                acc.setAccountAddress(accAdd);
            else
                acc.setAccountAddress(new AccountAddress());

        }
        model.addAttribute("item", acc);

        return "handle/editProfile.html";
    }

    @PostMapping("/editProfile")
    public String saveProfile(Accounts account, Model model, HttpServletRequest request) {
        String user = request.getRemoteUser();

        Accounts acc = accountsDao.findByUsername(user);

        Accounts saveAcc = accountService.saveProfile(acc, account);

        model.addAttribute("item", saveAcc);
        model.addAttribute("mess", "Cập nhật tài khoản thành công");

        return "handle/editProfile.html";
    }

}
