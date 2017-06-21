package com.example;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Task implements Comparable<Task>{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer priority;

    public int compareTo(Task someTask){
        return priority.compareTo(someTask.priority);
    }



}
