package com.mihneacristian.project_tracker.Entities;

import javax.persistence.*;


@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;
    
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status statusOfItem;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type typeOfItem;

    @ManyToOne
    @JoinColumn(name = "team_member_id")
    private TeamMembers teamMemberOfItem;

    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
    private Deadline deadline;

    // Getters and Setters
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatusOfItem() {
        return statusOfItem;
    }

    public void setStatusOfItem(Status statusOfItem) {
        this.statusOfItem = statusOfItem;
    }

    public Type getTypeOfItem() {
        return typeOfItem;
    }

    public void setTypeOfItem(Type typeOfItem) {
        this.typeOfItem = typeOfItem;
    }

    public TeamMembers getTeamMemberOfItem() {
        return teamMemberOfItem;
    }

    public void setTeamMemberOfItem(TeamMembers teamMemberOfItem) {
        this.teamMemberOfItem = teamMemberOfItem;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }
}
