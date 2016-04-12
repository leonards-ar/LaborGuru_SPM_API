package com.laborguru.controller.store;

import com.laborguru.controller.BaseController;
import com.laborguru.frontend.dto.OperationTimeDto;
import com.laborguru.model.OperationTime;
import com.laborguru.model.Store;
import com.laborguru.service.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by federicobarreraoro on 4/10/16.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/store/{storeId}/operationTimes")
public class OperationTimeController extends BaseController {

    @Autowired
    private StoreService storeService;

    @RequestMapping(method = RequestMethod.GET)
    public HashMap<String, Object> getOperationTimes(@PathVariable Integer storeId){
        List<OperationTimeDto> operationTimesDtos = new LinkedList<>();
        HashMap<String,Object> operations = new HashMap<>();

        Store auxStore = new Store();
        auxStore.setId(storeId);
        Store store = storeService.getStoreById(auxStore);

        operations.put("firstDayOfWeek",store.getFirstDayOfWeek().name());
        operations.put("store", getDtoMapper().toDto(store));
        operations.put("operationTime", getDtoMapper().toDto(store.getOperationTimes()));

        return operations;
    }


    public void setStoreService(StoreService storeService) {
        this.storeService = storeService;
    }
}
