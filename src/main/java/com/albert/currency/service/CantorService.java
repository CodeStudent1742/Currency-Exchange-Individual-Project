package com.albert.currency.service;

import com.albert.currency.domain.Cantor;
import com.albert.currency.repository.CantorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CantorService {

    private final CantorRepository cantorRepository;

    public Cantor getCantorRates(){
        return cantorRepository.findTopByOrderByCantorRatesIdDesc();
    }
    public void saveCantor(Cantor cantor){
        cantorRepository.save(cantor);
    }
}
