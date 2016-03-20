package com.laborguru.controller;

import com.laborguru.frontend.mapper.DtoMapper;

/**
 * Created by federicobarreraoro on 3/19/16.
 */
public abstract class BaseController {

    private final com.laborguru.frontend.mapper.DtoMapper dtoMapper = new com.laborguru.frontend.mapper.DtoMapper();

    public DtoMapper getDtoMapper() {
        return dtoMapper;
    }
}
