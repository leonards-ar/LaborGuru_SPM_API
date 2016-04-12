package com.laborguru.controller;

import com.laborguru.frontend.mapper.DomainMapper;
import com.laborguru.frontend.mapper.DtoMapper;

/**
 * Created by federicobarreraoro on 3/19/16.
 */

public abstract class BaseController {

    private final DtoMapper dtoMapper = new DtoMapper();

    private final DomainMapper domainMapper = new DomainMapper();

    public DtoMapper getDtoMapper() {
        return dtoMapper;
    }

    public DomainMapper getDomainMapper() {
        return domainMapper;
    }
}
