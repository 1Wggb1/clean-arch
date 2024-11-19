package br.com.cleanarch.core.entity;

import java.util.List;

public record PageableCarAdsBusiness(long page, long size, long totalElements, List<CarAdsBusiness> results) {
}
