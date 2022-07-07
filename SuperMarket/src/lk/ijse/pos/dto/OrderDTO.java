package lk.ijse.pos.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private String id;
    private String date;
    private String customerId;

    List<OrderDetailsDTO> orderDetails;


    //List<OrderDetailsDTO> orderDetails;

    public OrderDTO() {
    }

    public OrderDTO(String id, String date, String customerId, List<OrderDetailsDTO> orderDetails) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.orderDetails = orderDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }


}
