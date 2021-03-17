package com.teenthofabud.codingchallenge.sharenow.position.quadtree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node<T> {

    private T data;
    private Point point;

}
