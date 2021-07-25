package me.s0wnd.catalogservice.service;

import me.s0wnd.catalogservice.jpa.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
