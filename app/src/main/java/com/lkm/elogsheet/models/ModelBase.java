package com.lkm.elogsheet.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelBase {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    @SerializedName("_id")
    public String id;

    @SerializedName("_rev")
    public String rev;

    public List<String> channels;

    public Audit audit;

}
