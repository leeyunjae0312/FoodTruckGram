package org.androidtown.foodtruckgram.Info;

import java.util.ArrayList;

/**
 * Created by 이예지 on 2018-09-11.
 */

public class FoodTruckInfo {

    private String storeName, ownerId, ownerName;
    private double longitude,latitude;
    private boolean isOpen=false;
    private ArrayList<MenuInfo> menuList;

    public void opening(double longitude, double latitude){
        //서버에 개점알림
        this.longitude = longitude;
        this.latitude = latitude;
        this.isOpen = true;
    }

    public void closing(){
        //서버에 폐점알림
        this.isOpen = false;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void addMenu(MenuInfo newMenu){
        menuList.add(newMenu);
    }

    public void removeMenu(MenuInfo menu){
        //품절 메뉴 설정
        for(int i=0;i<menuList.size();i++){
            if(menuList.get(i).equals(menu))
                menuList.remove(i);
        }
    }

    public void setSoldOut(MenuInfo soldOutMenu){
        //품절 메뉴 설정
        for(int i=0;i<menuList.size();i++){
            if(menuList.get(i).equals(soldOutMenu))
                menuList.get(i).setSoldOut(true);
        }
    }

    public ArrayList<MenuInfo> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<MenuInfo> menuList) {
        this.menuList = menuList;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
