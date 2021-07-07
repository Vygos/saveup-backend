package br.com.saveup.saveupbackend.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> {
    private Integer totalPages;
//    private Integer totalElements;
//    private Boolean last;
//    private Integer pageNumber;
    private List<T> content;

}
