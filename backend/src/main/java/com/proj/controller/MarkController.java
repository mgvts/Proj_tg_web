package com.proj.controller;

import com.proj.domain.Mark;
import com.proj.domain.User;
import com.proj.exception.ValidationException;
import com.proj.form.MarkCredentials;
import com.proj.service.MarkService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class MarkController {
    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping("marks")
    public List<Mark> findMarks() {
        return markService.findAll();
    }

    @PostMapping("addMark")
    public Mark addMark(@RequestBody MarkCredentials markCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        return markService.addMark(markCredentials);
    }
}
