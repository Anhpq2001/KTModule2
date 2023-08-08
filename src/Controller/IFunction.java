package Controller;

import java.util.List;

public interface IFunction<E> {
    void insertItem(); // add new item

    void removeItem(); // remove old item

    void updateItem(); // update information old item;

    void displayItem(List<E> itemList); // show information old item;

    String findItemID(); // search information by code
}
