package com.fsa.cursus.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CategoryRequest implements Serializable {
    private int categoryId;
    private String categoryName;
    private String categoryStatus;
}
