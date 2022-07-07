package lk.ijse.pos.views.Cart;

import javafx.scene.control.Button;

import java.math.BigDecimal;

public class CartTM {
    private String itemCode;
    private int qty;
    private BigDecimal discount;
    private Button btn;

    public CartTM() {
    }

    public CartTM(String itemCode, int qty, BigDecimal discount, Button btn) {
        this.itemCode = itemCode;
        this.qty = qty;
        this.discount = discount;
        this.btn = btn;
    }


    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", discount=" + discount +
                ", btn=" + btn +
                '}';
    }
}
