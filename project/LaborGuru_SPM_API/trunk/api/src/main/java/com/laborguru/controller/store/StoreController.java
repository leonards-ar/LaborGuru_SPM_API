package com.laborguru.controller.store;

import com.laborguru.model.Store;
import com.laborguru.service.store.StoreService;
import com.laborguru.frontend.dto.StoreDto;
import com.laborguru.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by federicobarreraoro on 3/19/16.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/store")
public class StoreController extends BaseController{

    @Autowired
    private StoreService storeService;

    //-------------------Retrieve All Stores--------------------------------------------------------
    @RequestMapping(method = RequestMethod.GET)
    public List<StoreDto> getStores() throws Exception {

        List<StoreDto> storesDto = new LinkedList<>();

        for(Store store: storeService.findAll()) {
            storesDto.add(getDtoMapper().toTinyDto(store));
        }

        return storesDto;

    }

    @RequestMapping(value="{storeId}", method = RequestMethod.GET)
    public StoreDto getStore(@PathVariable Integer storeId) {

        Store tmp = new Store();
        tmp.setId(storeId);

        return getDtoMapper().toDto(storeService.getStoreById(tmp));

    }


    public StoreService getStoreService() {
        return storeService;
    }

    public void setStoreService(StoreService storeService) {
        this.storeService = storeService;
    }
}
