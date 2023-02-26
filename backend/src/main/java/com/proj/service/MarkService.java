package com.proj.service;

import com.proj.domain.Mark;
import com.proj.domain.User;
import com.proj.form.MarkCredentials;
import com.proj.repository.MarkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkService {
    private final MarkRepository markRepository;

    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public List<Mark> findAll() {
        return markRepository.findAllByOrderByCreationTimeDesc();
    }

    public Mark addMark(MarkCredentials markCredentials) {
        Mark mark = new Mark();
        mark.setUser(markCredentials.getUser());
        mark.setValue(Integer.parseInt(markCredentials.getValue()));
        mark.setComment(markCredentials.getComment());
        markRepository.save(mark);
        return mark;
    }
}
