package ro.fortech.internship.vinylshop.order.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.fortech.internship.vinylshop.order.model.OrderStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class DisplayOrderDto {

    private Double cost;

    private LocalDate dateCreate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
