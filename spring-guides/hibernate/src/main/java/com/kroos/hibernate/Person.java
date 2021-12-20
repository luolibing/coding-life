package com.kroos.hibernate;

import lombok.Data;

import javax.persistence.*;

/**
 * desc: TODO
 *
 * @author Kroos.luo
 * @since 2021-12-16 16:14
 */
@Data
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
