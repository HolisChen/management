package com.github.hollis.domain.vo.base;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public abstract class Tree<T> {
    List<T> children;
}
