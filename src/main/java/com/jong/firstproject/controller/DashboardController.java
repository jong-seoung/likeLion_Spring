package com.jong.firstproject.controller;

import com.jong.firstproject.model.CustomerRank;
import com.jong.firstproject.model.DailySales;
import com.jong.firstproject.repository.DashboardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sales")
public class DashboardController {
    private final DashboardRepository dashboardRepository;

    public DashboardController(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @GetMapping("")
    public String dailySales(Model model) {
        List<DailySales> sales = dashboardRepository.findDailySales();
        model.addAttribute("sales", sales);

        return "daily-sales";
    }

    /**   랭킹    **/
    @GetMapping("/customer-ranking")
    public String customerRanking(Model model) {
        List<CustomerRank> ranks = dashboardRepository.findCustomerRankings();
        model.addAttribute("ranks", ranks);

        return "customer-ranking";
    }
}
