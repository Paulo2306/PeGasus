package com.ifsp.PeGasus.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    @GetMapping("/dashboard")
    public String exibeDashboard(){
        return "dashboard-gerente/dashboard";
    }

}
