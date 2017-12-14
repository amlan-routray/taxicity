package com.appsters.igit.taxicity;


public class Feedbackrow {
    private String fbText,fbImage,fbLike,fbComment;

    public Feedbackrow(String fbText, String fbImage, String fbLike, String fbComment) {
        this.fbText = fbText;
        this.fbImage = fbImage;
        this.fbLike = fbLike;
        this.fbComment = fbComment;
    }

    public Feedbackrow() {
    }

    public String getFbText() {
        return fbText;
    }

    public void setFbText(String fbText) {
        this.fbText = fbText;
    }

    public String getFbImage() {
        return fbImage;
    }

    public void setFbImage(String fbImage) {
        this.fbImage = fbImage;
    }

    public String getFbLike() {
        return fbLike;
    }

    public void setFbLike(String fbLike) {
        this.fbLike = fbLike;
    }

    public String getFbComment() {
        return fbComment;
    }

    public void setFbComment(String fbComment) {
        this.fbComment = fbComment;
    }
}
