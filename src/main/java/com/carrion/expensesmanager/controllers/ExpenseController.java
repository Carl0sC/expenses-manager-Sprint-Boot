package com.carrion.expensesmanager.controllers;

import com.carrion.expensesmanager.models.Expense;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExpenseController {
    private List<Expense> expenses = new ArrayList<>();

    @GetMapping("/expenses")
    public String showExpenses(Model model) {
        model.addAttribute("expenses", expenses);

        // Calcular el total de gastos
        double total = calculateTotalExpenses();
        model.addAttribute("totalExpenses", total);

        return "expenses";
    }

    @PostMapping("/addExpense")
    public String addExpense(@RequestParam("description") String description,
                             @RequestParam("amount") double amount,
                             @RequestParam("date") String dateString,
                             @RequestParam("category") String category) {

        LocalDate date = LocalDate.parse(dateString);
        Expense newExpense = new Expense(getNextExpenseId(), description, amount, date, category);
        expenses.add(newExpense);

        return "redirect:/expenses";
    }

    @PostMapping("/deleteExpense")
    public String deleteExpense(@RequestParam("id") Long id) {
        expenses.removeIf(expense -> expense.getId().equals(id));
        return "redirect:/expenses";
    }

    private Long getNextExpenseId() {
        return (long) (expenses.size() + 1);
    }

    private double calculateTotalExpenses() {
        double total = 0.0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }
}
