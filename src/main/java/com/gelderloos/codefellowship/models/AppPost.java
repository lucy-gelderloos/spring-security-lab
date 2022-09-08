package com.gelderloos.codefellowship.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AppPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postAuthorUserName;
    private String postContent;

    private Date postDate;

    @ManyToOne
    private AppUser appUser;

    protected AppPost(){
    }

    public AppPost(String postAuthorUserName, String postContent) {
        this.postAuthorUserName = postAuthorUserName;
        this.postContent = postContent;
        this.postDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostAuthorUserName() {
        return postAuthorUserName;
    }

    public void setPostAuthorUserName(String postAuthorUserId) {
        this.postAuthorUserName = postAuthorUserId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
