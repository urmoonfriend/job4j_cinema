package kz.job4j.cinema.service.impl;

import kz.job4j.cinema.model.entity.Hall;
import kz.job4j.cinema.repository.HallRepository;
import kz.job4j.cinema.repository.impl.Sql2oHallRepository;
import kz.job4j.cinema.service.HallService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ThreadSafe
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;

    public HallServiceImpl(Sql2oHallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public Hall save(Hall hall) {
        return hallRepository.save(hall);
    }

    @Override
    public Optional<Hall> findById(int id) {
        return hallRepository.findById(id);
    }

    @Override
    public Optional<Hall> findByName(String name) {
        return hallRepository.findByName(name);
    }

    @Override
    public void deleteById(int id) {
        hallRepository.deleteById(id);
    }
}
