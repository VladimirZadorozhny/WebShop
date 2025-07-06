package be.vdab.webshop.controllers;

import be.vdab.webshop.domain.dto.GroupOnlyIdName;

import be.vdab.webshop.services.ProdgroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("prodgroups")
public class ProdgroupController {

    private final ProdgroupService prodgroupService;

    public ProdgroupController(ProdgroupService prodgroupService) {
        this.prodgroupService = prodgroupService;
    }


    @GetMapping()
    public List<GroupOnlyIdName> findAll() {
        return prodgroupService.getProdgroupsIdName();
    }


}
