package com.laborguru.controller.store;

import com.laborguru.model.Store;
import com.laborguru.service.store.StoreService;
import com.laborguru.frontend.dto.StoreDto;
import com.laborguru.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by federicobarreraoro on 3/19/16.
 */
@RestController
public class StoreController extends BaseController{

    @Autowired
    private StoreService storeService;

    //-------------------Retrieve All Stores--------------------------------------------------------
    @RequestMapping(value="/api/store", method = RequestMethod.GET)
    public ResponseEntity<List<StoreDto>> getStores() throws Exception {

        List<StoreDto> storesDto = new LinkedList<>();

        for(Store store: storeService.findAll()){
            storesDto.add(getDtoMapper().toDto(store));
        }
        return new ResponseEntity(storesDto, HttpStatus.OK);

    }

    public StoreService getStoreService() {
        return storeService;
    }

    public void setStoreService(StoreService storeService) {
        this.storeService = storeService;
    }
}
