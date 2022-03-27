package com.lkm.elogsheet.models;

import java.util.List;

public class BaseModel {

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocRev() {
        return docRev;
    }

    public void setDocRev(String docRev) {
        this.docRev = docRev;
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

    public String docId;
    public String docRev;
    public List<String> channels;
    public Audit audit;

}
