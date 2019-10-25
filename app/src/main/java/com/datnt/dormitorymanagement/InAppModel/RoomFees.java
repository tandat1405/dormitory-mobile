package com.datnt.dormitorymanagement.InAppModel;

public class RoomFees {
    private int roomId, waterNumber, electricNumber;
    private String roomName;

    public RoomFees(int roomId, int waterNumber, int electricNumber, String roomName) {
        this.roomId = roomId;
        this.waterNumber = waterNumber;
        this.electricNumber = electricNumber;
        this.roomName = roomName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getWaterNumber() {
        return waterNumber;
    }

    public void setWaterNumber(int waterNumber) {
        this.waterNumber = waterNumber;
    }

    public int getElectricNumber() {
        return electricNumber;
    }

    public void setElectricNumber(int electricNumber) {
        this.electricNumber = electricNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
