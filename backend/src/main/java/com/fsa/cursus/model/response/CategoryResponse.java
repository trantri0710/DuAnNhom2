package com.fsa.cursus.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CategoryResponse implements Serializable {
    private int categoryId;
    private String categoryName;
    private Boolean categoryStatus;
}
