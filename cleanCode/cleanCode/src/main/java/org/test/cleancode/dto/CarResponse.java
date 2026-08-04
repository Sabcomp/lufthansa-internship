package org.test.cleancode.dto;

public class CarResponse {
    private Long id;
    private String ownerName;
    private String plateNumber;
    private String model;

    public CarResponse(){

    }

    public CarResponse(Long id, String ownerName, String plateNumber, String model){
        this.id = id;
        this.ownerName = ownerName;
        this.plateNumber = plateNumber;
        this.model = model;
    }

    public Long getId(){
        return id;
    }

    public String getOwnerName(){
        return ownerName;
    }

    public String getPlateNumber(){
        return plateNumber;
    }

    public String getModel(){
        return model;
    }
}
