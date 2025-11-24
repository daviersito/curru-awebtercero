package com.example.corruna.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.corruna.demo.entities.Boleta;
import com.example.corruna.demo.repositories.BoletaRepository;

@Service
public class BoletaServiceImpl implements BoletaService {

    private final BoletaRepository boletaRepo;

    public BoletaServiceImpl(BoletaRepository boletaRepo) {
        this.boletaRepo = boletaRepo;
    }

    @Override
    public Boleta crearBoleta(Boleta boleta) {
        return boletaRepo.save(boleta);
    }

    @Override
    public Boleta obtenerBoleta(Long id) {
        return boletaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada"));
    }

    @Override
    public List<Boleta> obtenerBoletasPorUsuario(Long usuarioId) {
        return boletaRepo.findByUsuarioId(usuarioId);
    }

    @Override
public List<Boleta> obtenerTodasLasBoletas() {
    return boletaRepo.findAll();
}
}