package com.cad.pojo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Paper")
public class Reference {
    private ObjectId _id;
    private String title;
    private String abs;
    private String author;
    private Boolean is_marked;
    private Boolean is_submit;
    private Boolean is_update;
    private String journal;
    private String keywords;
    private String src;
    private String url;
    private Boolean valid;

    @Override
    public String toString() {
        return "User{" +
                "id=" + _id +
                ", abs='" + abs + '\'' +
                ", journal='" + journal + '\'' +
                '}';
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getAbs() {
        return abs;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setIs_marked(Boolean is_marked) {
        this.is_marked = is_marked;
    }

    public Boolean getIs_marked() {
        return is_marked;
    }

    public void setIs_submit(Boolean is_submit) {
        this.is_submit = is_submit;
    }

    public Boolean getIs_submit() {
        return is_submit;
    }

    public void setIs_update(Boolean is_update) {
        this.is_update = is_update;
    }

    public Boolean getIs_update() {
        return is_update;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getJournal() {
        return journal;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getValid() {
        return valid;
    }
}
