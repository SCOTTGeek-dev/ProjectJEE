package com.mihneacristian.project_tracker.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Deadline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deadlineId;

    @Column(nullable = false)
    private Date dueDate;

    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "itemId")
    private Item item;

    // Getters and Setters
    public Integer getDeadlineId() {
        return deadlineId;
    }

    public void setDeadlineId(Integer deadlineId) {
        this.deadlineId = deadlineId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
