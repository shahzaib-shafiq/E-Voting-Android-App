package com.example.smd_final_project;

public class Candidate {
    String imageUrl,audioUrl,symbolUrl,name,cnic,partyName,area,type,Id;
    int noOfVotes;
    public Candidate() {
    }



    public Candidate(String imageUrl, String audioUrl, String symbolUrl,String name, String cnic, String partyName, String area, String type,int noOfVotes) {
        this.imageUrl = imageUrl;
        this.audioUrl = audioUrl;
        this.symbolUrl = symbolUrl;
        this.name = name;
        this.cnic = cnic;
        this.noOfVotes = noOfVotes;
        this.partyName = partyName;
        this.area = area;
        this.type = type;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getNoOfVotes() {
        return noOfVotes;
    }

    public void setNoOfVotes(int noOfVotes) {
        this.noOfVotes = noOfVotes;
    }

    public String getSymbolUrl() {
        return symbolUrl;
    }

    public void setSymbolUrl(String symbolUrl) {
        this.symbolUrl = symbolUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
