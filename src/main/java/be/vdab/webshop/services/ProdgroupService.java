package be.vdab.webshop.services;

import be.vdab.webshop.domain.dto.GroupOnlyIdName;

import be.vdab.webshop.domain.entities.Prodgroup;
import be.vdab.webshop.repositories.ProdgroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdgroupService {

    private final ProdgroupRepository prodgroupRepository;

    public ProdgroupService(ProdgroupRepository prodgroupRepository) {
        this.prodgroupRepository = prodgroupRepository;
    }

    public List<GroupOnlyIdName> getProdgroupsIdName() {
        return prodgroupRepository.getProdgroupsIdName();
    }

    public Optional<Prodgroup> getProdgroupById(long id) {
        return prodgroupRepository.findById(id);
    }
}
